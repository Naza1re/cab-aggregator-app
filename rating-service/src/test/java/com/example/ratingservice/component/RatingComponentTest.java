package com.example.ratingservice.component;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.mapper.DriverMapper;
import com.example.ratingservice.mapper.PassengerMapper;
import com.example.ratingservice.model.DriverRating;
import com.example.ratingservice.model.PassengerRating;
import com.example.ratingservice.repository.DriverRatingRepository;
import com.example.ratingservice.repository.PassengerRatingRepository;
import com.example.ratingservice.service.impl.DriverRatingServiceImpl;
import com.example.ratingservice.service.impl.PassengerRatingServiceImpl;
import com.example.ratingservice.util.DriverRatingUtilTest;
import com.example.ratingservice.util.ExceptionMessages;
import com.example.ratingservice.util.PassengerRatingUtilTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@CucumberContextConfiguration
public class RatingComponentTest {

    @Mock
    private PassengerRatingRepository passengerRatingRepository;
    @InjectMocks
    private PassengerRatingServiceImpl passengerRatingService;
    @Mock
    private PassengerMapper passengerMapper;
    private PassengerRatingResponse passengerRatingResponse;
    @Mock
    private DriverRatingRepository driverRatingRepository;
    @Mock
    private DriverMapper driverMapper;
    @InjectMocks
    private DriverRatingServiceImpl driverRatingService;
    private DriverRatingResponse driverRatingResponse;
    private Exception exception;

    @Given("A passenger rating with id {long} exist")
    public void aPassengerRatingWithIdExist(long id) {
        PassengerRatingResponse expected = PassengerRatingUtilTest.getDefaultPassengerRatingResponse();
        PassengerRating retrievedPassengerRating = PassengerRatingUtilTest.getDefaultPassengerRating();

        doReturn(Optional.of(retrievedPassengerRating))
                .when(passengerRatingRepository)
                .findPassengerRatingByPassenger(id);
        doReturn(expected)
                .when(passengerMapper)
                .fromEntityToResponse(retrievedPassengerRating);
        doReturn(true)
                .when(passengerRatingRepository)
                .existsById(id);

        Optional<PassengerRating> passenger = passengerRatingRepository.findPassengerRatingByPassenger(id);
        assertTrue(passenger.isPresent());
    }

    @When("The id {long} is passed to the findByPassengerId method")
    public void theIdIsPassedToTheFindByPassengerIdMethod(long id) {
        try {
            passengerRatingResponse = passengerRatingService.getPassengerRecordById(id);
        } catch (PassengerRatingNotFoundException ex) {
            exception = ex;
        }
    }

    @Then("The response should contain passenger rating with id {long}")
    public void theResponseShouldContainPassengerRatingWithId(long id) {
        PassengerRating passenger = passengerRatingRepository.findPassengerRatingByPassenger(id).get();
        PassengerRatingResponse expected = passengerMapper.fromEntityToResponse(passenger);

        assertThat(passengerRatingResponse).isEqualTo(expected);
    }

    @Then("The PassengerRatingNotFoundException with id {long} should be thrown")
    public void thePassengerRatingNotFoundExceptionWithIdShouldBeThrown(long id) {
        assertThat(exception.getMessage()).isEqualTo(String.format(ExceptionMessages.PASSENGER_RATING_NOT_FOUND, id));
    }

    @Given("A passenger rating with id {long} doesn't exist")
    public void aPassengerRatingWithIdDoesntExist(long id) {
        Optional<PassengerRating> passengerRating = passengerRatingRepository.findPassengerRatingByPassenger(id);
        assertFalse(passengerRating.isPresent());
    }

    @Given("A driver rating with id {long} exist")
    public void aDriverRatingWithIdExist(long id) {
        DriverRatingResponse expected = DriverRatingUtilTest.getDefaultDriverRatingResponse();
        DriverRating retrievedPassengerRating = DriverRatingUtilTest.getDefaultDriverRating();

        doReturn(Optional.of(retrievedPassengerRating))
                .when(driverRatingRepository)
                .findDriverRatingByDriver(id);
        doReturn(expected)
                .when(driverMapper)
                .fromEntityToResponse(retrievedPassengerRating);
        doReturn(true)
                .when(driverRatingRepository)
                .existsById(id);

        Optional<DriverRating> driver = driverRatingRepository.findDriverRatingByDriver(id);
        assertTrue(driver.isPresent());
    }

    @When("The id {long} is passed to the findByDriverId method")
    public void theIdIsPassedToTheFindByIdMethod(long id) {
        try {
            driverRatingResponse = driverRatingService.getDriverById(id);
        } catch (DriverRatingNotFoundException ex) {
            exception = ex;
        }
    }

    @Then("The response should contain driver rating with id {long}")
    public void theResponseShouldContainDriverRatingWithId(long id) {
        DriverRating driverRating = driverRatingRepository.findDriverRatingByDriver(id).get();
        DriverRatingResponse expected = driverMapper.fromEntityToResponse(driverRating);

        assertThat(driverRatingResponse).isEqualTo(expected);
    }

    @Given("A driver rating with id {long} doesn't exist")
    public void aDriverRatingWithIdDoesntExist(long id) {
        Optional<DriverRating> driverRating = driverRatingRepository.findDriverRatingByDriver(id);
        assertFalse(driverRating.isPresent());
    }

    @Then("The DriverRatingNotFoundException with id {long} should be thrown")
    public void theDriverRatingNotFoundExceptionWithIdShouldBeThrown(long id) {
        assertThat(exception.getMessage()).isEqualTo(String.format(ExceptionMessages.DRIVER_RATING_NOT_FOUND, id));
    }

    @When("The id {long} is passed to the deleteByPassengerId method")
    public void theIdIsPassedToTheDeleteByIdMethod(long id) {
        try {
            passengerRatingResponse = passengerRatingService.deletePassengerRecord(id);
        } catch (PassengerRatingNotFoundException  ex) {
            exception = ex;
        }
    }

    @When("The id {long} is passed to the deleteByDriverId method")
    public void theIdIsPassedToTheDeleteByDriverIdMethod(long id) {
        try {
            driverRatingResponse = driverRatingService.deleteDriverRecord(id);
        } catch (DriverRatingNotFoundException  ex) {
            exception = ex;
        }
    }

    @When("A create request with id {long} is passed to the passenger rating add method")
    public void aCreateRequestWithIdIsPassedToTheAddMethod(long id) {
        CreateRequest createRequest = PassengerRatingUtilTest.getCreateRequest();
        try {
            passengerRatingResponse = passengerRatingService.createPassenger(createRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should contain created passenger rating response")
    public void theResponseShouldContainCreatedPassengerRatingResponse() {
        var expected = PassengerRatingUtilTest.getDefaultPassengerRatingResponse();
        assertThat(passengerRatingResponse).isEqualTo(expected);
    }

    @Given("A passenger rating with id {long} is unique")
    public void aPassengerRatingWithIdIsUnique(long id) {

        PassengerRatingResponse expected = PassengerRatingUtilTest.getDefaultPassengerRatingResponse();
        PassengerRating saved = PassengerRatingUtilTest.getSavedPassengerRating();
        doReturn(false)
                .when(passengerRatingRepository)
                .existsByPassenger(id);
        doReturn(saved)
                .when(passengerRatingRepository)
                .save(any(PassengerRating.class));
        doReturn(expected)
                .when(passengerMapper)
                .fromEntityToResponse(saved);
    }

    @When("A create request with id {long} is passed to the driver rating add method")
    public void aCreateRequestWithIdIsPassedToTheDriverRatingAddMethod(long id) {
        CreateRequest createRequest = DriverRatingUtilTest.getCreateRequest();
        try {
            driverRatingResponse = driverRatingService.createDriver(createRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("A driver rating with id {long} is unique")
    public void aDriverRatingWithIdIsUnique(long id) {
        DriverRatingResponse expected = DriverRatingUtilTest.getDefaultDriverRatingResponse();
        DriverRating saved = DriverRatingUtilTest.getSavedDriverRating();

        doReturn(false)
                .when(driverRatingRepository)
                .existsByDriver(id);
        doReturn(saved)
                .when(driverRatingRepository)
                .save(any(DriverRating.class));
        doReturn(expected)
                .when(driverMapper)
                .fromEntityToResponse(saved);

    }

    @Then("The response should contain created driver rating response")
    public void theResponseShouldContainCreatedDriverRatingResponse() {
        var expected = DriverRatingUtilTest.getDefaultDriverRatingResponse();
        assertThat(driverRatingResponse).isEqualTo(expected);
    }
}
