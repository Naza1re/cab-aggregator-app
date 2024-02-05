package com.example.rideservice.service;

import com.example.rideservice.client.DriverClient;
import com.example.rideservice.client.PassengerClient;
import com.example.rideservice.client.PaymentClient;
import com.example.rideservice.dto.request.CustomerChargeRequest;
import com.example.rideservice.dto.request.RideForDriver;
import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.*;
import com.example.rideservice.exception.PaginationParamException;
import com.example.rideservice.exception.RideNotHaveDriverException;
import com.example.rideservice.exception.SortTypeException;
import com.example.rideservice.kafka.producer.RideProducer;
import com.example.rideservice.mapper.RideMapper;
import com.example.rideservice.model.Ride;
import com.example.rideservice.repository.RideRepository;
import com.example.rideservice.service.impl.RideServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.example.rideservice.util.RideTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RideServiceImplTest {
    @Mock
    private RideRepository rideRepository;
    @InjectMocks
    private RideServiceImpl rideService;
    @Mock
    private RideMapper rideMapper;
    @Mock
    private PassengerClient passengerClient;
    @Mock
    private PaymentClient paymentClient;
    @Mock
    private RideProducer rideProducer;
    @Mock
    private DriverClient driverClient;


    @Test
    void getRideByIdWhenRideExist() {
        RideResponse response = getRideResponse();
        Ride ride = getRide();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(DEFAULT_ID);
        doReturn(response)
                .when(rideMapper)
                .fromEntityToResponse(ride);

        rideService.getRideById(DEFAULT_ID);

        verify(rideRepository).findById(DEFAULT_ID);
        verify(rideMapper).fromEntityToResponse(ride);
    }

    @Test
    void creatRide() {
        RideRequest rideRequest = getRideRequest();
        Ride notSavedRide = getNotSavedRide();
        Ride savedRide = getRide();
        RideResponse rideResponse = getRideResponse();
        PassengerResponse passengerResponse = getPassengerResponse();
        ChargeResponse chargeResponse = getChargeResponse();

        doReturn(notSavedRide)
                .when(rideMapper)
                .fromRequestToEntity(rideRequest);
        doReturn(passengerResponse)
                .when(passengerClient)
                .getPassenger(DEFAULT_PASSENGER_ID);
        doReturn(chargeResponse)
                .when(paymentClient)
                .chargeFromCustomer(ArgumentMatchers.any(CustomerChargeRequest.class));
        doReturn(savedRide)
                .when(rideRepository)
                .save(notSavedRide);
        doNothing()
                .when(rideProducer)
                .sendMessage(ArgumentMatchers.any(RideForDriver.class));
        doReturn(rideResponse)
                .when(rideMapper)
                .fromEntityToResponse(savedRide);

        RideResponse actual = rideService.findRide(rideRequest);

        verify(rideMapper).fromRequestToEntity(rideRequest);
        verify(rideRepository).save(notSavedRide);
        verify(passengerClient).getPassenger(PASSENGER_ID);
        verify(rideMapper).fromEntityToResponse(savedRide);

        assertThat(actual).isEqualTo(rideResponse);

    }

    @Test
    void GetPageWhenPaginationParamsIsInvalid() {
        assertThrows(
                PaginationParamException.class,
                () -> rideService.getRidePage(INVALID_PAGINATION_PAGE,INVALID_PAGINATION_SIZE,INVALID_PAGINATION_SORTED_TYPE)
        );
    }

    @Test
    void GetPageWhenOrderByIsInvalid() {
        assertThrows(
                SortTypeException.class,
                () -> rideService.getRidePage(DEFAULT_PAGINATION_PAGE,DEFAULT_PAGINATION_SIZE,INVALID_PAGINATION_SORTED_TYPE)
        );
    }
    @Test
    void setDriverWhenRideExist() {
        Ride savedRide = getRideWithDriver();
        Ride ride = getRide();
        DriverForRide driverForRide = getDriverForRide();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(DEFAULT_ID);
        doReturn(savedRide)
                .when(rideRepository)
                .save(ArgumentMatchers.any(Ride.class));

        rideService.setDriver(driverForRide);

        verify(rideRepository).findById(DEFAULT_ID);
        verify(rideRepository).save(ArgumentMatchers.any(Ride.class));

    }

    @Test
    void handleRideForAvailableDriverWhenRideExist() {
        Ride ride = getRide();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findFirstByDriverIdIsNull();
        doNothing()
                .when(rideProducer)
                .sendMessage(ArgumentMatchers.any(RideForDriver.class));

        rideService.handleRideForAvailableDriver();

        verify(rideRepository).findFirstByDriverIdIsNull();
        verify(rideProducer).sendMessage(ArgumentMatchers.any(RideForDriver.class));
    }

    @Test
    void startRideWhenDriverIsNotExist() {
        Ride ride = getRide();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(DEFAULT_ID);
        assertThrows(
                RideNotHaveDriverException.class,
                () -> rideService.startRide(DEFAULT_ID)
        );

        verify(rideRepository).findById(DEFAULT_ID);

    }

    @Test
    void startRideWhenDriverExist() {
        Ride ride = getRideWithDriver();
        Ride startedRide = getStartedRide();
        RideResponse response = getStartedRideResponse();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(DEFAULT_ID);
        doReturn(startedRide)
                .when(rideRepository)
                .save(ArgumentMatchers.any(Ride.class));
        doReturn(response)
                .when(rideMapper)
                .fromEntityToResponse(ArgumentMatchers.any(Ride.class));

        RideResponse actual = rideService.startRide(DEFAULT_ID);

        verify(rideRepository).findById(DEFAULT_ID);
        verify(rideRepository).save(ArgumentMatchers.any(Ride.class));
        verify(rideMapper).fromEntityToResponse(ArgumentMatchers.any(Ride.class));

        assertThat(actual).isEqualTo(response);

    }

    @Test
    void endRideWhenDriverExist() {
        Ride ride = getStartedRide();
        Ride endedRide = getEndedRide();
        RideResponse response = getEndedRideResponse();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(DEFAULT_ID);
        doReturn(endedRide)
                .when(rideRepository)
                .save(ArgumentMatchers.any(Ride.class));
        doReturn(response)
                .when(rideMapper)
                .fromEntityToResponse(ArgumentMatchers.any(Ride.class));

        RideResponse actual = rideService.endRide(DEFAULT_ID);

        verify(rideRepository).findById(DEFAULT_ID);
        verify(rideMapper).fromEntityToResponse(ArgumentMatchers.any(Ride.class));
        verify(rideRepository).save(ArgumentMatchers.any(Ride.class));

        assertThat(actual).isEqualTo(response);
    }

    @Test
    void getRidesByPassengerId() {
        RideListResponse expected = getRideListResponse();
        List<Ride> rideList = getRideList();

        doReturn(rideList)
                .when(rideRepository)
                .getAllByPassengerId(DEFAULT_PASSENGER_ID);
        doReturn(expected.getRideResponseList().get(0))
                .when(rideMapper)
                .fromEntityToResponse(rideList.get(0));
        doReturn(expected.getRideResponseList().get(1))
                .when(rideMapper)
                .fromEntityToResponse(rideList.get(1));

        RideListResponse actual = rideService.getListOfRidesByPassengerId(DEFAULT_PASSENGER_ID);

        verify(rideRepository).getAllByPassengerId(DEFAULT_PASSENGER_ID);
        verify(rideMapper).fromEntityToResponse(rideList.get(0));
        verify(rideMapper).fromEntityToResponse(rideList.get(1));

        assertThat(actual.getRideResponseList()).isEqualTo(expected.getRideResponseList());
    }

    @Test
    void getRidesByDriverId() {
        RideListResponse expected = getRideListResponse();
        List<Ride> rideList = getRideList();

        doReturn(rideList)
                .when(rideRepository)
                .getAllByDriverId(DEFAULT_DRIVER_ID);
        doReturn(expected.getRideResponseList().get(0))
                .when(rideMapper)
                .fromEntityToResponse(rideList.get(0));
        doReturn(expected.getRideResponseList().get(1))
                .when(rideMapper)
                .fromEntityToResponse(rideList.get(1));

        RideListResponse actual = rideService.getListOfRidesByDriverId(DEFAULT_DRIVER_ID);

        verify(rideRepository).getAllByDriverId(DEFAULT_DRIVER_ID);
        verify(rideMapper).fromEntityToResponse(rideList.get(0));
        verify(rideMapper).fromEntityToResponse(rideList.get(1));

        assertThat(actual.getRideResponseList()).isEqualTo(expected.getRideResponseList());
    }

    @Test
    void getRidePageReturnsPageResponseWhenValidParams() {
        Page<Ride> ridesPage = mock(Page.class);
        List<Ride> ridesList = getRideList();

        when(ridesPage.getContent()).thenReturn(ridesList);
        when(ridesPage.getTotalElements()).thenReturn((long) ridesList.size());
        when(rideRepository.findAll(any(PageRequest.class))).thenReturn(ridesPage);

        RidePageResponse pageResponse = rideService.getRidePage(DEFAULT_PAGINATION_PAGE, DEFAULT_PAGINATION_SIZE, null);

        assertNotNull(pageResponse);
        assertEquals(ridesList.size(), pageResponse.getTotalElements());
        assertEquals(DEFAULT_PAGINATION_PAGE, pageResponse.getTotalPages());
    }

}