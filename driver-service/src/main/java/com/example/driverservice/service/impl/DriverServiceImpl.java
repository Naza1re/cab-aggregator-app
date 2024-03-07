package com.example.driverservice.service.impl;

import com.example.driverservice.dto.request.DriverForRide;
import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.request.RatingRequest;
import com.example.driverservice.dto.response.*;
import com.example.driverservice.exception.*;
import com.example.driverservice.kafka.producer.AvailableDriverProducer;
import com.example.driverservice.kafka.producer.DriverProducer;
import com.example.driverservice.mapper.DriverMapper;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.service.DriverService;
import com.example.driverservice.service.RatingService;
import com.example.driverservice.util.Constants;
import com.example.driverservice.util.ConstantsMessages;
import com.example.driverservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.example.driverservice.util.ConstantsMessages.*;
import static com.example.driverservice.util.ExceptionMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final AvailableDriverProducer availableDriverProducer;
    private final DriverProducer driverProducer;
    private final RatingService ratingService;

    @Override
    public DriverResponse getDriverById(Long id) {
        Driver driver = getOrThrow(id);
        log.info(String.format(GET_DRIVER_BY_ID_LOG_MESSAGE, id));
        return driverMapper.fromEntityToResponse(driver);
    }

    @Override
    public DriverListResponse getListOfDrivers() {
        List<DriverResponse> driverResponseList = driverRepository.findAll().stream()
                .map(driverMapper::fromEntityToResponse)
                .toList();
        log.info(String.format(GET_DRIVER_LIST_LOG_MESSAGE));
        return new DriverListResponse(driverResponseList);
    }

    @Override
    public DriverResponse updateDriver(Long id, DriverRequest driverRequest) {

        Driver driver = getOrThrow(id);

        preUpdateDriverCheck(driver, driverRequest);

        driver = driverMapper.fromRequestToEntity(driverRequest);
        driver.setId(id);
        log.info(String.format(UPDATE_DRIVER_WITH_ID, id));
        return driverMapper.fromEntityToResponse(driverRepository.save(driver));
    }

    @Transactional
    @Override
    public DriverResponse createDriver(DriverRequest driverRequest) {

        preCreateDriverCheck(driverRequest);

        Driver driver = driverMapper.fromRequestToEntity(driverRequest);
        driver.setAvailable(false);

        Driver savedDriver = driverRepository.save(driver);

        ratingService.createDriverRecord(RatingRequest.builder()
                .id(savedDriver.getId())
                .build());
        log.info(String.format(CREATE_DRIVER_WITH_ID, savedDriver.getId()));
        return driverMapper.fromEntityToResponse(savedDriver);
    }

    @Override
    public DriverResponse deleteDriver(Long id) {
        Driver driver = getOrThrow(id);
        driverRepository.delete(driver);

        ratingService.deleteDriverRating(id);
        log.info(String.format(DELETE_DRIVER_WITH_ID, id));
        return driverMapper.fromEntityToResponse(driver);
    }

    private void preUpdateDriverCheck(Driver driver, DriverRequest driverRequest) {
        if (!driver.getEmail().equals(driverRequest.getEmail())) {
            checkEmailExist(driverRequest.getEmail());
        }
        if (!driver.getPhone().equals(driverRequest.getPhone())) {
            checkPhoneExist(driverRequest.getPhone());
        }
        if (!driver.getNumber().equals(driverRequest.getNumber())) {
            checkCarNumberExist(driverRequest.getNumber());
        }
    }

    private void checkEmailExist(String email) {
        if (driverRepository.existsByEmail(email)) {
            log.info(String.format(DRIVER_WITH_MAIL_ALREADY_EXIST, email));
            throw new EmailAlreadyExistException(String.format(ExceptionMessages.DRIVER_WITH_EMAIL_ALREADY_EXIST, email));
        }
    }

    private void checkPhoneExist(String phone) {
        if (driverRepository.existsByPhone(phone)) {
            log.info(String.format(ConstantsMessages.DRIVER_WITH_PHONE_ALREADY_EXIST, phone));
            throw new PhoneAlreadyExistException(String.format(ExceptionMessages.DRIVER_WITH_PHONE_ALREADY_EXIST, phone));
        }
    }

    private void checkCarNumberExist(String carNum) {
        if (driverRepository.existsByNumber(carNum)) {
            log.info(String.format(DRIVER_WITH_NUMBER_ALREADY_EXIST, carNum));
            throw new CarNumberAlreadyExistException(String.format(ExceptionMessages.DRIVER_WITH_CAR_NUMBER_ALREADY_EXIST, carNum));
        }
    }


    private void preCreateDriverCheck(DriverRequest driverRequest) {
        checkCarNumberExist(driverRequest.getNumber());
        checkPhoneExist(driverRequest.getPhone());
        checkEmailExist(driverRequest.getEmail());
    }

    @Override
    public DriverListResponse getAvailableDrivers() {
        List<Driver> listOfAvailableDrivers = driverRepository.getAllByAvailable(true);
        List<DriverResponse> listOfAvailable = listOfAvailableDrivers.stream()
                .map(driverMapper::fromEntityToResponse)
                .toList();
        log.info(String.format(GET_DRIVER_AVAILABLE_LIST_LOG_MESSAGE));
        return new DriverListResponse(listOfAvailable);
    }

    private PageRequest getPageRequest(int page, int size, String orderBy) {
        if (page < 1 || size < 1) {
            throw new PaginationParamException(String.format(PAGINATION_FORMAT_EXCEPTION));
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

    @Override
    public DriverPageResponse getDriverPage(int page, int size, String orderBy) {

        PageRequest pageRequest = getPageRequest(page, size, orderBy);
        Page<Driver> driverPage = driverRepository.findAll(pageRequest);

        List<Driver> retrievedDrivers = driverPage.getContent();
        long total = driverPage.getTotalElements();

        List<DriverResponse> passengers = retrievedDrivers.stream()
                .map(driverMapper::fromEntityToResponse)
                .toList();
        log.info(GETTING_DRIVER_PAGE);
        return DriverPageResponse.builder()
                .driverList(passengers)
                .totalPages(page)
                .totalElements(total)
                .build();
    }

    private void validateSortingParameter(String orderBy) {
        Arrays.stream(DriverResponse.class.getDeclaredFields())
                .map(Field::getName)
                .filter(field -> field.equals(orderBy))
                .findFirst()
                .orElseThrow(() -> new SortTypeException(INVALID_TYPE_OF_SORT));
    }

    private Driver getOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(String.format(DRIVER_NOT_FOUND_EXCEPTION, id)));
    }

    @Override
    public DriverResponse changeStatus(Long driverId) {
        Driver driver = getOrThrow(driverId);
        log.info(String.format(CHANGING_STATUS_DRIVER_WITH_ID, driverId));
        driver.setAvailable(!driver.isAvailable());
        if (driver.isAvailable()) {
            availableDriverProducer.sendMessage(ConstantsMessages.NEW_AVAILABLE_DRIVERS);
        }
        driverRepository.save(driver);
        return driverMapper.fromEntityToResponse(driver);
    }

    public void handleDriver(Long driverId) {
        DriverForRide driver = findDriverForRide(driverId);
        if (driver == null) {
            log.info(DRIVERS_NOT_AVAILABLE);
        } else {
            driverProducer.sendMessage(driver);
        }

    }

    private DriverForRide findDriverForRide(Long id) {
        DriverRatingListResponse ratingListResponse = ratingService.getDriversRateList();
        List<Driver> drivers = driverRepository.getAllByAvailable(true);
        Optional<Driver> highestRatedDriver = drivers.stream()
                .filter(Driver::isAvailable)
                .max(Comparator.comparingDouble(driver ->
                        getDriverRating(ratingListResponse, driver.getId())));
        return highestRatedDriver.map(driver -> DriverForRide.builder()
                .rideId(id)
                .driverId(driver.getId())
                .build()
        ).orElse(null);
    }

    private double getDriverRating(DriverRatingListResponse ratingListResponse, Long driverId) {
        return ratingListResponse.getDriverRatingResponseList().stream()
                .filter(ratingResponse -> ratingResponse.getDriver().equals(driverId))
                .mapToDouble(DriverRatingResponse::getRate)
                .findFirst()
                .orElse(Constants.DEFAULT_RATE);
    }

}
