package com.example.driverservice.component;

import com.example.driverservice.client.RatingFeignClient;
import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverResponse;
import com.example.driverservice.exception.CarNumberAlreadyExistException;
import com.example.driverservice.exception.DriverNotFoundException;
import com.example.driverservice.exception.EmailAlreadyExistException;
import com.example.driverservice.exception.PhoneAlreadyExistException;
import com.example.driverservice.mapper.DriverMapper;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.service.impl.DriverServiceImpl;
import com.example.driverservice.util.ExceptionMessages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.example.driverservice.util.DriverTestUtil.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@CucumberContextConfiguration
public class DriverComponentTest {
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private RatingFeignClient ratingFeignClient;
    @Mock
    private DriverMapper driverMapper;
    @InjectMocks
    private DriverServiceImpl driverService;

    private DriverResponse driverResponse;
    private Exception exception;

    @Given("A driver with id {long} exist")
    public void driverWithIdExist(long id) {
        DriverResponse expected = getDefaultDriverResponse();
        Driver retrievedDriver = getDefaultDriver();

        doReturn(Optional.of(retrievedDriver))
                .when(driverRepository)
                .findById(id);
        doReturn(expected)
                .when(driverMapper)
                .fromEntityToResponse(retrievedDriver);
        doReturn(true)
                .when(driverRepository)
                .existsById(id);

        Optional<Driver> driver = driverRepository.findById(id);
        assertTrue(driver.isPresent());
    }


    @When("The id {long} is passed to the findById method")
    public void theIdIsPassedToTheFindByIdMethod(long id) {
        try {
            driverResponse = driverService.getDriverById(id);
        } catch (DriverNotFoundException ex) {
            exception = ex;
        }
    }

    @Then("The response should contain driver with id {long}")
    public void theResponseShouldContainDriverWithId(long id) {
        Driver driver = driverRepository.findById(id).get();
        DriverResponse expected = driverMapper.fromEntityToResponse(driver);

        assertThat(driverResponse).isEqualTo(expected);
    }

    @Given("A driver with id {long} doesn't exist")
    public void aDriverWithIdDoesntExist(long id) {
        Optional<Driver> passenger = driverRepository.findById(id);

        assertFalse(passenger.isPresent());
    }

    @Then("The DriverNotFoundException with id {long} should be thrown")
    public void theDriverNotFoundExceptionWithIdShouldBeThrown(long id) {
        String expected = String.format(ExceptionMessages.DRIVER_NOT_FOUND_EXCEPTION, id);
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Given("A driver with phone {string} exists")
    public void aDriverWithPhoneExists(String phone) {
        doReturn(true)
                .when(driverRepository)
                .existsByPhone(phone);

        assertTrue(driverRepository.existsByPhone(phone));

    }

    @When("A create request with phone {string} is passed to the add method")
    public void aCreateRequestWithPhoneIsPassedToTheAddMethod(String phone) {
        DriverRequest createRequest = getDriverRequest(phone);
        try {
            driverResponse = driverService.createDriver(createRequest);
        } catch (PhoneAlreadyExistException e) {
            exception = e;
        }

    }

    @Then("The PhoneAlreadyExistsException should be thrown for phone {string}")
    public void thePhoneAlreadyExistsExceptionShouldBeThrownForPhone(String phone) {
        assertThat(exception.getMessage()).isEqualTo(String.format(ExceptionMessages.DRIVER_WITH_PHONE_ALREADY_EXIST, phone));
    }

    @When("A create request with email {string} is passed to the add method")
    public void aCreateRequestWithEmailIsPassedToTheAddMethod(String email) {
        DriverRequest createRequest = getDriverRequest(email);
        try {
            driverResponse = driverService.createDriver(createRequest);
        } catch (EmailAlreadyExistException e) {
            exception = e;
        }
    }

    @Given("A driver with email {string} exists")
    public void aDriverWithEmailExists(String email) {
        doReturn(true)
                .when(driverRepository)
                .existsByEmail(email);

        assertTrue(driverRepository.existsByEmail(email));
    }

    @Then("The EmailAlreadyExistException should be thrown for email {string}")
    public void theEmailAlreadyExistExceptionShouldBeThrownForEmail(String email) {
        assertThat(exception.getMessage()).isEqualTo(String.format(ExceptionMessages.DRIVER_WITH_EMAIL_ALREADY_EXIST, email));
    }

    @Given("A driver with car number {string} exists")
    public void aDriverWithCarNumberExists(String carNum) {

        doReturn(true)
                .when(driverRepository)
                .existsByNumber(carNum);

        assertFalse(driverRepository.existsByEmail(carNum));
    }

    @When("A create request with car number {string} is passed to the add method")
    public void aCreateRequestWithCarNumberIsPassedToTheAddMethod(String carNum) {
        DriverRequest request = getDriverRequest(carNum);
        try {
            driverResponse = driverService.createDriver(request);
        } catch (CarNumberAlreadyExistException ex) {
            exception = ex;
        }
    }

    @Then("The CarNumberAlreadyExistException should be thrown for сar number {string}")
    public void theCarNumberAlreadyExistExceptionShouldBeThrownForСarNumber(String carNum) {
        assertThat(exception.getMessage()).isEqualTo(String.format(ExceptionMessages.DRIVER_WITH_CAR_NUMBER_ALREADY_EXIST, carNum));
    }

    @Given("A driver with email {string} , {string} and car number {string} doesn't exist")
    public void aDriverWithEmailAndCarNumberDoesntExist(String email, String phone, String carNum) {
        DriverResponse expected = getDefaultDriverResponse();
        Driver driverToSave = getNotSavedDriver();
        Driver savedDriver = getSavedDriver();

        doReturn(false)
                .when(driverRepository)
                .existsByEmail(email);
        doReturn(false)
                .when(driverRepository)
                .existsByPhone(phone);
        doReturn(false)
                .when(driverRepository)
                .existsByNumber(carNum);
        doReturn(driverToSave)
                .when(driverMapper)
                .fromRequestToEntity(any(DriverRequest.class));
        doReturn(savedDriver)
                .when(driverRepository)
                .save(driverToSave);
        doReturn(expected)
                .when(driverMapper)
                .fromEntityToResponse(any(Driver.class));
    }

    @When("A create request with email {string}, phone {string}, car number {string} is passed to the add method")
    public void aCreateRequestWithEmailPhoneCarNumberIsPassedToTheAddMethod(String email, String phone, String carNum) {
        DriverRequest createRequest = getDriverRequest();
        try {
            driverResponse = driverService.createDriver(createRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should contain created driver response")
    public void theResponseShouldContainCreatedDriverResponse() {
        var expected = getDefaultDriverResponse();
        assertThat(driverResponse).isEqualTo(expected);
    }

    @When("A update request and id {long} is passed to the update method")
    public void aUpdateRequestAndIdIsPassedToTheUpdateMethod(long id) {
        DriverRequest updateRequest = getDriverUpdateRequest();
        try {
            driverResponse = driverService.updateDriver(id, updateRequest);
        } catch (DriverNotFoundException ex) {
            exception = ex;
        }
    }
}
