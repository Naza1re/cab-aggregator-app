package com.example.passengerservice.service;

import com.example.passengerservice.client.RatingFeignClient;
import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.request.RatingRequest;
import com.example.passengerservice.dto.response.PassengerListResponse;
import com.example.passengerservice.dto.response.PassengerPageResponse;
import com.example.passengerservice.dto.response.PassengerRatingResponse;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.exception.*;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.impl.PassengerServiceImpl;
import com.example.passengerservice.util.PassengerTestUtil;
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

import static com.example.passengerservice.util.PassengerTestUtil.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {

    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private PassengerMapper passengerMapper;
    @InjectMocks
    private PassengerServiceImpl passengerService;
    @Mock
    private RatingFeignClient ratingFeignClient;


    @Test
    void findAllDrivers() {

        PassengerListResponse expected = getPassengerListResponse();
        List<Passenger> passengerList = getPassengerList();


        doReturn(passengerList)
                .when(passengerRepository)
                .findAll();
        doReturn(expected.getListOfPassengers().get(0))
                .when(passengerMapper)
                .fromEntityToResponse(passengerList.get(0));
        doReturn(expected.getListOfPassengers().get(1))
                .when(passengerMapper)
                .fromEntityToResponse(passengerList.get(1));

        PassengerListResponse actual = passengerService.getAllPassengers();

        verify(passengerRepository).findAll();
        verify(passengerMapper).fromEntityToResponse(passengerList.get(0));
        verify(passengerMapper).fromEntityToResponse(passengerList.get(1));

        assertThat(actual.getListOfPassengers()).isEqualTo(expected.getListOfPassengers());

    }

    @Test
    void findPassengerByIdWhenPassengerExist() {
        Passenger retrievedPassenger = getPassenger();
        PassengerResponse expectedResponse = getPassengerResponse();

        doReturn(Optional.of(retrievedPassenger))
                .when(passengerRepository)
                .findById(DEFAULT_ID);

        doReturn(expectedResponse)
                .when(passengerMapper)
                .fromEntityToResponse(retrievedPassenger);

        PassengerResponse actual = passengerService.getPassengerById(DEFAULT_ID);
        verify(passengerRepository).findById(DEFAULT_ID);
        verify(passengerMapper).fromEntityToResponse(retrievedPassenger);

        assertThat(actual).isEqualTo(expectedResponse);
    }

    @Test
    void findPassengerByIdWhenPassengerNotExist() {
        doReturn(Optional.empty())
                .when(passengerRepository)
                .findById(PassengerTestUtil.NOT_FOUND_ID);
        assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.getPassengerById(NOT_FOUND_ID)
        );
        verify(passengerRepository).findById(NOT_FOUND_ID);

    }

    @Test
    void createPassengerWhenPassengerUnique() {
        PassengerResponse passengerResponse = getPassengerResponse();
        Passenger passengerToSave = getPassenger();
        PassengerRequest request = getPassengerRequest();
        Passenger savedPassenger = getNotSavedPassenger();
        PassengerRatingResponse passengerRatingResponse = getPassengerRatingResponse();

        doReturn(false)
                .when(passengerRepository)
                .existsByEmail(DEFAULT_EMAIL);
        doReturn(false)
                .when(passengerRepository)
                .existsByPhone(DEFAULT_PHONE);
        doReturn(passengerToSave)
                .when(passengerMapper)
                .fromRequestToEntity(request);
        doReturn(savedPassenger)
                .when(passengerRepository)
                .save(passengerToSave);
        doReturn(passengerResponse)
                .when(passengerMapper)
                .fromEntityToResponse(savedPassenger);
        doReturn(passengerRatingResponse)
                .when(ratingFeignClient)
                .createPassengerRecord(ArgumentMatchers.any(RatingRequest.class));

        PassengerResponse actual = passengerService.createPassenger(request);

        verify(passengerRepository).existsByEmail(DEFAULT_EMAIL);
        verify(passengerRepository).existsByPhone(DEFAULT_PHONE);
        verify(passengerRepository).save(passengerToSave);
        verify(passengerMapper).fromRequestToEntity(request);
        verify(passengerMapper).fromEntityToResponse(savedPassenger);
        verify(ratingFeignClient).createPassengerRecord(ArgumentMatchers.any(RatingRequest.class));

        assertThat(actual).isEqualTo(passengerResponse);
    }

    @Test
    void GetPageWhenPaginationParamsIsInvalid() {
        assertThrows(
                PaginationParamException.class,
                () -> passengerService.getPassengerPage(INVALID_PAGINATION_PAGE,INVALID_PAGINATION_SIZE,INVALID_PAGINATION_SORTED_TYPE)
        );
    }

    @Test
    void GetPageWhenOrderByIsInvalid() {
        assertThrows(
                SortTypeException.class,
                () -> passengerService.getPassengerPage(DEFAULT_PAGINATION_PAGE,DEFAULT_PAGINATION_SIZE,INVALID_PAGINATION_SORTED_TYPE)
        );
    }

    @Test
    void createPassengerWhenEmailIsNotUnique() {
        PassengerRequest passengerRequest = getPassengerRequest();

        doReturn(true)
                .when(passengerRepository)
                .existsByEmail(DEFAULT_EMAIL);

        assertThrows(
                EmailAlreadyExistException.class,
                () -> passengerService.createPassenger(passengerRequest)
        );

        verify(passengerRepository).existsByEmail(DEFAULT_EMAIL);

    }

    @Test
    void createPassengerWhenPhoneIsNotUnique() {
        PassengerRequest passengerRequest = getPassengerRequest();

        doReturn(true)
                .when(passengerRepository)
                .existsByPhone(DEFAULT_PHONE);

        assertThrows(
                PhoneAlreadyExistException.class,
                () -> passengerService.createPassenger(passengerRequest)
        );

        verify(passengerRepository).existsByPhone(DEFAULT_PHONE);
    }

    @Test
    void updatePassengerWhenPassengerNotFound() {

        PassengerRequest passengerRequest = getUpdatePassengerRequest();

        doReturn(Optional.empty())
                .when(passengerRepository)
                .findById(NOT_FOUND_ID);

        assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.updatePassenger(NOT_FOUND_ID, passengerRequest)
        );

        verify(passengerRepository).findById(NOT_FOUND_ID);
    }

    @Test
    void deletePassengerWhenPassengerNotFound() {


        doReturn(Optional.empty())
                .when(passengerRepository)
                .findById(NOT_FOUND_ID);

        assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.deletePassenger(NOT_FOUND_ID)
        );

        verify(passengerRepository).findById(NOT_FOUND_ID);

    }

    @Test
    void deletePassengerWhenPassengerExist() {
        Passenger passenger = getPassenger();
        PassengerResponse passengerResponse = getPassengerResponse();
        PassengerRatingResponse passengerRatingResponse = getPassengerRatingResponse();

        doReturn(Optional.of(passenger))
                .when(passengerRepository)
                .findById(DEFAULT_ID);
        doNothing()
                .when(passengerRepository)
                .delete(passenger);
        doReturn(passengerRatingResponse)
                .when(ratingFeignClient)
                .deletePassengerRecord(DEFAULT_ID);
        doReturn(passengerResponse)
                .when(passengerMapper)
                .fromEntityToResponse(passenger);

        passengerService.deletePassenger(DEFAULT_ID);

        verify(passengerRepository).delete(passenger);
        verify(ratingFeignClient).deletePassengerRecord(DEFAULT_ID);

    }

    @Test
    void updatePassengerWhenEmailIsNotUnique() {
        Passenger updatePassenger = getPassenger();
        PassengerRequest passengerRequest = getUpdatePassengerRequest();

        doReturn(Optional.of(updatePassenger))
                .when(passengerRepository)
                .findById(DEFAULT_ID);
        doReturn(true)
                .when(passengerRepository)
                .existsByEmail(passengerRequest.getEmail());

        assertThrows(
                EmailAlreadyExistException.class,
                () -> passengerService.updatePassenger(DEFAULT_ID, passengerRequest)
        );

        verify(passengerRepository).findById(DEFAULT_ID);
        verify(passengerRepository).existsByEmail(passengerRequest.getEmail());

    }

    @Test
    void updatePassengerWhenPhoneIsNotUnique() {
        Passenger updatePassenger = getPassenger();
        PassengerRequest passengerRequest = getUpdatePassengerRequest();

        doReturn(Optional.of(updatePassenger))
                .when(passengerRepository)
                .findById(DEFAULT_ID);
        doReturn(false)
                .when(passengerRepository)
                .existsByEmail(passengerRequest.getEmail());
        doReturn(true)
                .when(passengerRepository)
                .existsByPhone(passengerRequest.getPhone());

        assertThrows(
                PhoneAlreadyExistException.class,
                () -> passengerService.updatePassenger(DEFAULT_ID, passengerRequest)
        );

        verify(passengerRepository).findById(DEFAULT_ID);
        verify(passengerRepository).existsByPhone(passengerRequest.getPhone());
        verify(passengerRepository).existsByEmail(passengerRequest.getEmail());

    }

    @Test
    void updatePassengerWhenDataIsUnique() {
        Passenger passenger = getPassenger();
        PassengerRequest passengerUpdateRequest = getUpdatePassengerRequest();
        PassengerResponse expected = getUpdatePassengerResponse();
        Passenger updatePassenger = getUpdatePassenger();

        doReturn(Optional.of(passenger))
                .when(passengerRepository)
                .findById(DEFAULT_ID);
        doReturn(false)
                .when(passengerRepository)
                .existsByEmail(passengerUpdateRequest.getEmail());
        doReturn(false)
                .when(passengerRepository)
                .existsByPhone(passengerUpdateRequest.getPhone());
        doReturn(updatePassenger)
                .when(passengerMapper)
                .fromRequestToEntity(passengerUpdateRequest);
        doReturn(expected)
                .when(passengerMapper)
                .fromEntityToResponse(updatePassenger);
        doReturn(updatePassenger)
                .when(passengerRepository)
                .save(updatePassenger);

        PassengerResponse actual = passengerService.updatePassenger(DEFAULT_ID, passengerUpdateRequest);

        verify(passengerRepository).findById(DEFAULT_ID);
        verify(passengerRepository).existsByPhone(passengerUpdateRequest.getPhone());
        verify(passengerRepository).existsByEmail(passengerUpdateRequest.getEmail());
        verify(passengerMapper).fromEntityToResponse(updatePassenger);
        verify(passengerMapper).fromRequestToEntity(passengerUpdateRequest);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void getPassengerPage_ReturnsPageResponse_WhenValidParams() {
        Page<Passenger> passengersPage = mock(Page.class);
        List<Passenger> passengersList = getPassengerList();
        when(passengersPage.getContent()).thenReturn(passengersList);
        when(passengersPage.getTotalElements()).thenReturn((long) passengersList.size());
        when(passengerRepository.findAll(any(PageRequest.class))).thenReturn(passengersPage);

        PassengerPageResponse pageResponse = passengerService.getPassengerPage(DEFAULT_PAGINATION_PAGE, DEFAULT_PAGINATION_SIZE, null);

        assertNotNull(pageResponse);
        assertEquals(passengersList.size(), pageResponse.getTotalElements());
        assertEquals(DEFAULT_PAGINATION_PAGE, pageResponse.getTotalPages());
    }

    @Test
    void getPassengerPage_ReturnsEmptyResponse_WhenNoPassengersFound() {
        Page<Passenger> passengersPage = mock(Page.class);
        when(passengersPage.getContent()).thenReturn(List.of());
        when(passengersPage.getTotalElements()).thenReturn(0L);
        when(passengerRepository.findAll(any(PageRequest.class))).thenReturn(passengersPage);

        PassengerPageResponse pageResponse = passengerService.getPassengerPage(DEFAULT_PAGINATION_PAGE, DEFAULT_PAGINATION_SIZE, null);

        assertNotNull(pageResponse);
        assertEquals(0, pageResponse.getTotalElements());
        assertEquals(DEFAULT_PAGINATION_PAGE, pageResponse.getTotalPages());
    }

}