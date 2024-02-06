package com.example.ratingservice.service;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;
import com.example.ratingservice.exception.PassengerRatingAlreadyExistException;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.mapper.PassengerMapper;
import com.example.ratingservice.model.PassengerRating;
import com.example.ratingservice.repository.PassengerRatingRepository;
import com.example.ratingservice.service.impl.PassengerRatingServiceImpl;
import com.example.ratingservice.util.PassengerRatingUtilTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.ratingservice.util.PassengerRatingUtilTest.DEFAULT_PASSENGER_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerRatingServiceImplTest {
    @Mock
    private PassengerRatingRepository passengerRatingRepository;
    @InjectMocks
    private PassengerRatingServiceImpl passengerRatingService;
    @Mock
    private PassengerMapper passengerMapper;

    @Test
    void getPassengerByIdWhenDriverExist() {
        PassengerRatingResponse passengerRatingResponse = PassengerRatingUtilTest.getDefaultPassengerRatingResponse();
        PassengerRating passengerRating = PassengerRatingUtilTest.getSavedPassengerRating();

        doReturn(Optional.of(passengerRating))
                .when(passengerRatingRepository)
                .findPassengerRatingByPassenger(DEFAULT_PASSENGER_ID);
        doReturn(passengerRatingResponse)
                .when(passengerMapper)
                .fromEntityToResponse(passengerRating);

        PassengerRatingResponse actual = passengerRatingService.getPassengerRecordById(DEFAULT_PASSENGER_ID);

        verify(passengerRatingRepository).findPassengerRatingByPassenger(DEFAULT_PASSENGER_ID);
        verify(passengerMapper).fromEntityToResponse(passengerRating);

        assertThat(actual).isEqualTo(passengerRatingResponse);
    }

    @Test
    void createPassengerRatingWhenRatingAlreadyExist() {
        CreateRequest request = PassengerRatingUtilTest.getCreateRequest();

        doReturn(true)
                .when(passengerRatingRepository)
                .existsByPassenger(request.getId());
        assertThrows(
                PassengerRatingAlreadyExistException.class,
                () -> passengerRatingService.createPassenger(request)
        );
    }

    @Test
    void creteDriverRating() {
        PassengerRatingResponse passengerRatingResponse = PassengerRatingUtilTest.getDefaultPassengerRatingResponse();
        CreateRequest request = PassengerRatingUtilTest.getCreateRequest();
        PassengerRating passengerRating = PassengerRatingUtilTest.getDefaultPassengerRating();
        PassengerRating passengerRatingSaved = PassengerRatingUtilTest.getSavedPassengerRating();


        doReturn(false)
                .when(passengerRatingRepository)
                .existsByPassenger(DEFAULT_PASSENGER_ID);
        doReturn(passengerRatingSaved)
                .when(passengerRatingRepository)
                .save(passengerRating);
        doReturn(passengerRatingResponse)
                .when(passengerMapper)
                .fromEntityToResponse(passengerRatingSaved);

        PassengerRatingResponse actual = passengerRatingService.createPassenger(request);

        verify(passengerRatingRepository).save(passengerRating);
        verify(passengerRatingRepository).existsByPassenger(DEFAULT_PASSENGER_ID);
        verify(passengerMapper).fromEntityToResponse(passengerRatingSaved);

        assertThat(actual).isEqualTo(passengerRatingResponse);
    }

    @Test
    void deleteDriverRatingByIdWhenRatingExist() {
        PassengerRating passengerRating = PassengerRatingUtilTest.getDefaultPassengerRating();

        doReturn(Optional.of(passengerRating))
                .when(passengerRatingRepository)
                .findPassengerRatingByPassenger(DEFAULT_PASSENGER_ID);
        doNothing()
                .when(passengerRatingRepository)
                .delete(passengerRating);

        passengerRatingService.deletePassengerRecord(DEFAULT_PASSENGER_ID);

        verify(passengerRatingRepository).delete(passengerRating);
        verify(passengerRatingRepository).findPassengerRatingByPassenger(DEFAULT_PASSENGER_ID);

    }

    @Test
    void deleteDriveRatingByIdWhenRatingIsNotExist() {
        doReturn(Optional.empty())
                .when(passengerRatingRepository)
                .findPassengerRatingByPassenger(PassengerRatingUtilTest.NOT_EXIST_ID);

        assertThrows(
                PassengerRatingNotFoundException.class,
                () -> passengerRatingService.deletePassengerRecord(PassengerRatingUtilTest.NOT_EXIST_ID)
        );
    }

    @Test
    void updateDriverRating() {
        PassengerRating passengerRating = PassengerRatingUtilTest.getDefaultPassengerRating();
        UpdateRequest updateRequest = PassengerRatingUtilTest.getUpdateRequest();
        PassengerRatingResponse passengerRatingResponse = PassengerRatingUtilTest.getDefaultPassengerRatingResponse();

        doReturn(Optional.of(passengerRating))
                .when(passengerRatingRepository)
                .findPassengerRatingByPassenger(DEFAULT_PASSENGER_ID);
        doReturn(passengerRatingResponse)
                .when(passengerMapper)
                .fromEntityToResponse(passengerRating);

        PassengerRatingResponse actual = passengerRatingService.updatePassengerRating(updateRequest);

        verify(passengerRatingRepository).findPassengerRatingByPassenger(DEFAULT_PASSENGER_ID);
        verify(passengerMapper).fromEntityToResponse(passengerRating);

        assertThat(actual).isEqualTo(passengerRatingResponse);
    }

}

