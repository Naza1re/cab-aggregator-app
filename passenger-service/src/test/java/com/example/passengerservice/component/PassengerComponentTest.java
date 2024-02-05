package com.example.passengerservice.component;

import com.example.passengerservice.client.RatingFeignClient;
import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.exception.EmailAlreadyExistException;
import com.example.passengerservice.exception.PassengerNotFoundException;
import com.example.passengerservice.exception.PhoneAlreadyExistException;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.impl.PassengerServiceImpl;
import com.example.passengerservice.util.ExceptionMessages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.example.passengerservice.util.ExceptionMessages.PASSENGER_WITH_EMAIL_ALREADY_EXIST;
import static com.example.passengerservice.util.ExceptionMessages.PASSENGER_WITH_PHONE_ALREADY_EXIST;
import static com.example.passengerservice.util.PassengerTestUtil.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@CucumberContextConfiguration
public class PassengerComponentTest {
    @Mock
    private RatingFeignClient ratingFeignClient;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private PassengerMapper passengerMapper;
    @InjectMocks
    private PassengerServiceImpl passengerService;
    private PassengerResponse passengerResponse;
    private Exception exception;

    @Given("A passenger with id {long} exist")
    public void PassengerWithIdExist(long id) {
        PassengerResponse expected = getPassengerResponse();
        Passenger retrievedPassenger = getPassenger();

        doReturn(Optional.of(retrievedPassenger))
                .when(passengerRepository)
                .findById(id);
        doReturn(expected)
                .when(passengerMapper)
                .fromEntityToResponse(retrievedPassenger);
        doReturn(true)
                .when(passengerRepository)
                .existsById(id);

        Optional<Passenger> passenger = passengerRepository.findById(id);
        assertTrue(passenger.isPresent());
    }

    @Then("The response should contain passenger with id {long}")
    public void theResponseShouldContainPassengerWithId(long id) {
        Passenger passenger = passengerRepository.findById(id).get();
        PassengerResponse expected = passengerMapper.fromEntityToResponse(passenger);

        assertThat(passengerResponse).isEqualTo(expected);
    }

    @When("The id {long} is passed to the findById method")
    public void idPassedToFindByIdMethod(long id) {
        try {
            passengerResponse = passengerService.getPassengerById(id);
        } catch (PassengerNotFoundException e) {
            exception = e;
        }
    }

    @Given("A passenger with id {long} doesn't exist")
    public void aPassengerWithIdDoesntExist(long id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        assertFalse(passenger.isPresent());
    }

    @Then("The PassengerNotFoundException with id {long} should be thrown")
    public void theNotFoundExceptionWithIdShouldBeThrown(long id) {
        String expected = String.format(ExceptionMessages.PASSENGER_NOT_FOUND_EXCEPTION, id);
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Given("A passenger with phone {string} exists")
    public void aPassengerWithPhoneExists(String phone) {
        doReturn(true)
                .when(passengerRepository)
                .existsByPhone(phone);

        assertTrue(passengerRepository.existsByPhone(phone));
    }

    @When("A create request with phone {string} is passed to the add method")
    public void aCreateRequestWithEmailPhoneIsPassedToTheAddMethod(String phone) {
        PassengerRequest createRequest = getPassengerRequest(phone);
        try {
            passengerResponse = passengerService.createPassenger(createRequest);
        } catch (PhoneAlreadyExistException e) {
            exception = e;
        }
    }

    @Then("The PhoneAlreadyExistsException should be thrown for phone {string}")
    public void thePhoneAlreadyExistsExceptionShouldBeThrown(String phone) {
        assertThat(exception.getMessage()).isEqualTo(String.format(PASSENGER_WITH_PHONE_ALREADY_EXIST, phone));
    }

    @Given("A passenger with email {string} exists")
    public void aPassengerWithEmailExists(String email) {
        doReturn(true)
                .when(passengerRepository)
                .existsByEmail(email);

        assertTrue(passengerRepository.existsByEmail(email));
    }

    @When("A create request with email {string} is passed to the add method")
    public void aCreateRequestWithEmailIsPassedToTheAddMethod(String email) {
            PassengerRequest createRequest = getPassengerRequest(email);
            try {
                passengerResponse = passengerService.createPassenger(createRequest);
            } catch (EmailAlreadyExistException e) {
                exception = e;
            }
    }

    @Then("The EmailAlreadyExistException should be thrown for email {string}")
    public void theEmailAlreadyExistExceptionShouldBeThrownForEmail(String email) {
        assertThat(exception.getMessage()).isEqualTo(String.format(PASSENGER_WITH_EMAIL_ALREADY_EXIST, email));
    }

    @When("The id {long} is passed to the deleteById method")
    public void theIdIsPassedToTheDeleteByIdMethod(long id) {
        try {
            passengerService.deletePassenger(id);
        } catch (PassengerNotFoundException e) {
            exception = e;
        }
    }

    @When("A create request with email {string}, phone {string} is passed to the add method")
    public void aCreateRequestWithEmailPhoneIsPassedToTheAddMethod(String email, String phone) {
        PassengerRequest createRequest = getPassengerRequest();
        try {
            passengerResponse = passengerService.createPassenger(createRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("A passenger with email {string} and phone {string} doesn't exist")
    public void aPassengerWithEmailAndPhoneDoesntExist(String email, String phone) {
        PassengerResponse expected = getPassengerResponse();
        Passenger passengerToSave = getNotSavedPassenger();
        Passenger savedPassenger = getPassenger();

        doReturn(false)
                .when(passengerRepository)
                .existsByEmail(email);
        doReturn(false)
                .when(passengerRepository)
                .existsByPhone(phone);
        doReturn(passengerToSave)
                .when(passengerMapper)
                .fromRequestToEntity(any(PassengerRequest.class));
        doReturn(savedPassenger)
                .when(passengerRepository)
                .save(passengerToSave);
        System.out.println(savedPassenger);
        doReturn(expected)
                .when(passengerMapper)
                .fromEntityToResponse(any(Passenger.class));
    }

    @Then("The response should contain created passenger response")
    public void theResponseShouldContainCreatedPassenger() {
        var expected = getPassengerResponse();
        assertThat(passengerResponse).isEqualTo(expected);
    }

    @When("A update request and id {long} is passed to the update method")
    public void aUpdateRequestIsPassedToTheUpdateMethod(long id) {
        PassengerRequest createRequest = getUpdatePassengerRequest();
        try {
            passengerResponse = passengerService.updatePassenger(id,createRequest);
        } catch (Exception e) {
            exception = e;
        }
    }
}
