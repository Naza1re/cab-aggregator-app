package com.example.rideservice.service.impl;

import com.example.rideservice.dto.request.CustomerChargeRequest;
import com.example.rideservice.dto.request.RideForDriver;
import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.*;
import com.example.rideservice.exception.PaginationParamException;
import com.example.rideservice.exception.RideNotFoundException;
import com.example.rideservice.exception.RideNotHaveDriverException;
import com.example.rideservice.exception.SortTypeException;
import com.example.rideservice.kafka.producer.RideProducer;
import com.example.rideservice.mapper.RideMapper;
import com.example.rideservice.model.Ride;
import com.example.rideservice.model.enums.Status;
import com.example.rideservice.repository.RideRepository;
import com.example.rideservice.service.*;
import com.example.rideservice.util.Constants;
import com.example.rideservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.rideservice.util.Constants.DEFAULT_PRICE;


@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PassengerService passengerService;
    private final DriverService driverService;
    private final RideProducer rideProducer;
    private final PaymentService paymentService;
    private final PromoCodeService promoCodeService;


    @Override
    public RideResponse startRide(Long rideId) {
        Ride ride = getOrThrow(rideId);

        checkRideHasDriver(ride);
        ride.setStartDate(LocalDateTime.now());
        ride.setStatus(Status.ACTIVE);
        Ride savedRide = rideRepository.save(ride);
        return rideMapper.fromEntityToResponse(savedRide);
    }

    @Override
    public RideResponse getRideById(Long id) {
        Ride ride = getOrThrow(id);
        return rideMapper.fromEntityToResponse(ride);
    }

    private void checkRideHasDriver(Ride ride) {
        if (ride.getDriverId() == null) {
            throw new RideNotHaveDriverException(String.format(ExceptionMessages.RIDE_DONT_HAVE_DRIVER_TO_START, ride.getId()));
        }
    }

    @Override
    public RideResponse endRide(Long rideId) {
        Ride ride = getOrThrow(rideId);

        checkRideHasDriver(ride);
        ride.setEndDate(LocalDateTime.now());
        ride.setStatus(Status.FINISHED);
        driverService.changeStatus(ride.getDriverId());
        rideRepository.save(ride);
        return rideMapper.fromEntityToResponse(ride);
    }

    @Override
    public RideListResponse getListOfRidesByPassengerId(Long passengerId) {
        List<RideResponse> rideList = rideRepository.getAllByPassengerId(passengerId)
                .stream()
                .map(rideMapper::fromEntityToResponse)
                .toList();

        return new RideListResponse(rideList);
    }

    @Override
    public RideListResponse getListOfRidesByDriverId(Long driverId) {
        List<RideResponse> rideList = rideRepository.getAllByDriverId(driverId)
                .stream()
                .map(rideMapper::fromEntityToResponse)
                .toList();

        return new RideListResponse(rideList);
    }

    private PageRequest getPageRequest(int page, int size, String orderBy) {
        if (page < 1 || size < 1) {
            throw new PaginationParamException(String.format(ExceptionMessages.PAGINATION_FORMAT_EXCEPTION));
        }

        PageRequest pageRequest;
        if (orderBy == null) {
            pageRequest = PageRequest.of(page - 1, size);
        } else {
            validateSortingParameter(orderBy);
            pageRequest = PageRequest.of(page - 1, size, Sort.by(orderBy));
        }

        return pageRequest;
    }

    private void validateSortingParameter(String orderBy) {
        Arrays.stream(RideResponse.class.getDeclaredFields())
                .map(Field::getName)
                .filter(field -> field.equals(orderBy))
                .findFirst()
                .orElseThrow(() -> new SortTypeException(ExceptionMessages.INVALID_TYPE_OF_SORT));
    }

    @Override
    public RidePageResponse getRidePage(int page, int size, String orderBy) {
        PageRequest pageRequest = getPageRequest(page, size, orderBy);
        Page<Ride> ridePage = rideRepository.findAll(pageRequest);

        List<Ride> retrievedRides = ridePage.getContent();
        long total = ridePage.getTotalElements();

        List<RideResponse> rides = retrievedRides.stream()
                .map(rideMapper::fromEntityToResponse)
                .toList();
        return RidePageResponse.builder()
                .rideList(rides)
                .totalPages(page)
                .totalElements(total)
                .build();
    }

    @Override
    public RideResponse findRide(RideRequest rideRequest) {
        Ride ride = rideMapper.fromRequestToEntity(rideRequest);

        PassengerResponse passenger = passengerService.getPassenger(rideRequest.getPassengerId());


        ride.setPrice(reducePriceByExistingPromoCode(rideRequest.getPromo()));

        createChargeFromCustomer(passenger.getId(), ride.getPrice());
        ride.setStatus(Status.CREATED);
        Ride savedRide = rideRepository.save(ride);

        RideForDriver rideForDriver = createRideForDriver(ride);
        rideProducer.sendMessage(rideForDriver);

        return rideMapper.fromEntityToResponse(savedRide);
    }

    private BigDecimal reducePriceByExistingPromoCode(String value) {
        if (value == null) {
            return BigDecimal.valueOf(DEFAULT_PRICE);
        } else {
            PromoCodeResponse promoCodeResponse = promoCodeService.getPromCode(value);
            return BigDecimal.valueOf(DEFAULT_PRICE - (DEFAULT_PRICE * (promoCodeResponse.getPercent() / 100)));
        }
    }

    private void createChargeFromCustomer(Long id, BigDecimal price) {
        CustomerChargeRequest request = CustomerChargeRequest.builder()
                .amount(price)
                .passengerId(id)
                .currency("USD")
                .build();
        paymentService.chargeFromCustomer(request);

    }

    private RideForDriver createRideForDriver(Ride ride) {
        return RideForDriver.builder()
                .rideId(ride.getId())
                .build();
    }

    private Ride getOrThrow(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(String.format(ExceptionMessages.RIDE_NOT_FOUND_EXCEPTION, id)));
    }

    @Override
    public void setDriver(DriverForRide driver) {
        Ride ride = getOrThrow(driver.getRideId());
        ride.setStatus(Status.ACCEPTED);
        driverService.changeStatus(driver.getDriverId());
        ride.setDriverId(driver.getDriverId());

        rideRepository.save(ride);
    }

    @Override
    public void handleRideForAvailableDriver() {
        Optional<Ride> ride = rideRepository.findFirstByDriverIdIsNull();
        ride.ifPresent(this::findDriverForRide);
    }

    private void findDriverForRide(Ride ride) {
        RideForDriver rideForDriver = RideForDriver.builder()
                .rideId(ride.getId())
                .build();
        rideProducer.sendMessage(rideForDriver);
    }

}
