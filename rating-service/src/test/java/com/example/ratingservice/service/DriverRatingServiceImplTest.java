package com.example.ratingservice.service;


import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.mapper.DriverMapper;
import com.example.ratingservice.model.DriverRating;
import com.example.ratingservice.repository.DriverRatingRepository;
import com.example.ratingservice.service.impl.DriverRatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.ratingservice.util.DriverRatingUtilTest.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverRatingServiceImplTest {
    @Mock
    private DriverRatingRepository driverRatingRepository;
    @Mock
    private DriverMapper driverMapper;
    @InjectMocks
    private DriverRatingServiceImpl driverRatingService;


    @Test
    void getDriverByIdWhenDriverExist() {
        DriverRatingResponse driverRatingResponse = getDefaultDriverRatingResponse();
        DriverRating driverRating = getSavedDriverRating();

        doReturn(Optional.of(driverRating))
                .when(driverRatingRepository)
                .findDriverRatingByDriver(DEFAULT_DRIVER_ID);
        doReturn(driverRatingResponse)
                .when(driverMapper)
                .fromEntityToResponse(driverRating);

        DriverRatingResponse actual = driverRatingService.getDriverById(DEFAULT_DRIVER_ID);

        verify(driverRatingRepository).findDriverRatingByDriver(DEFAULT_DRIVER_ID);
        verify(driverMapper).fromEntityToResponse(driverRating);

        assertThat(actual).isEqualTo(driverRatingResponse);
    }

    @Test
    void creteDriverRating() {
        DriverRatingResponse driverRatingResponse = getDefaultDriverRatingResponse();
        CreateRequest request = getCreateRequest();
        DriverRating driverRating = getDefaultDriverRating();
        DriverRating driverRatingSaved = getSavedDriverRating();


        doReturn(false)
                .when(driverRatingRepository)
                .existsByDriver(DEFAULT_DRIVER_ID);
        doReturn(driverRatingSaved)
                .when(driverRatingRepository)
                .save(driverRating);
        doReturn(driverRatingResponse)
                .when(driverMapper)
                .fromEntityToResponse(driverRating);

        DriverRatingResponse actual = driverRatingService.createDriver(request);

        verify(driverRatingRepository).save(driverRating);
        verify(driverRatingRepository).existsByDriver(DEFAULT_DRIVER_ID);
        verify(driverMapper).fromEntityToResponse(driverRating);

        assertThat(actual).isEqualTo(driverRatingResponse);
    }

    @Test
    void deleteDriverRatingByIdWhenRatingExist() {
        DriverRating driverRating = getDefaultDriverRating();

        doReturn(Optional.of(driverRating))
                .when(driverRatingRepository)
                .findDriverRatingByDriver(DEFAULT_DRIVER_ID);
        doNothing()
                .when(driverRatingRepository)
                .delete(driverRating);

        driverRatingService.deleteDriverRecord(DEFAULT_DRIVER_ID);

        verify(driverRatingRepository).delete(driverRating);
        verify(driverRatingRepository).findDriverRatingByDriver(DEFAULT_DRIVER_ID);

    }

    @Test
    void deleteDriveRatingByIdWhenRatingIsNotExist() {
        doReturn(Optional.empty())
                .when(driverRatingRepository)
                .findDriverRatingByDriver(NOT_EXIST_ID);

        assertThrows(
                DriverRatingNotFoundException.class,
                () -> driverRatingService.deleteDriverRecord(NOT_EXIST_ID)
        );
    }

    @Test
    void updateDriverRating() {
        DriverRating driverRating = getDefaultDriverRating();
        UpdateRequest updateRequest = getUpdateRequest();
        DriverRatingResponse driverRatingResponse = getDefaultDriverRatingResponse();

        doReturn(Optional.of(driverRating))
                .when(driverRatingRepository)
                .findDriverRatingByDriver(DEFAULT_DRIVER_ID);
        doReturn(driverRatingResponse)
                .when(driverMapper)
                .fromEntityToResponse(driverRating);

        DriverRatingResponse actual = driverRatingService.updateDriverRate(updateRequest);

        verify(driverRatingRepository).findDriverRatingByDriver(DEFAULT_DRIVER_ID);
        verify(driverMapper).fromEntityToResponse(driverRating);

        assertThat(actual).isEqualTo(driverRatingResponse);
    }


}