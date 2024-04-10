package com.example.rideservice.component;

import com.example.rideservice.client.DriverClient;
import com.example.rideservice.client.PassengerClient;
import com.example.rideservice.client.PaymentClient;
import com.example.rideservice.dto.request.CustomerChargeRequest;
import com.example.rideservice.dto.request.RideForDriver;
import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.ChargeResponse;
import com.example.rideservice.dto.response.PassengerResponse;
import com.example.rideservice.dto.response.RideListResponse;
import com.example.rideservice.dto.response.RideResponse;
import com.example.rideservice.exception.RideNotFoundException;
import com.example.rideservice.kafka.producer.RideProducer;
import com.example.rideservice.mapper.RideMapper;
import com.example.rideservice.model.Ride;
import com.example.rideservice.repository.RideRepository;
import com.example.rideservice.service.impl.RideServiceImpl;
import com.example.rideservice.util.ExceptionMessages;
import com.example.rideservice.util.RideTestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.example.rideservice.util.RideTestUtil.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@CucumberContextConfiguration
public class RideComponentTest {
    @Mock
    private PaymentClient paymentClient;
    @Mock
    private RideProducer rideProducer;
    @Mock
    private DriverClient driverClient;
    @Mock
    PassengerClient passengerClient;
    @Mock
    private RideRepository rideRepository;
    @Mock
    private RideMapper rideMapper;
    @InjectMocks
    private RideServiceImpl rideService;
    private RideResponse rideResponse;
    private Exception exception;
    private RideListResponse rideListResponse;

    @Given("A ride with id {long} exist")
    public void aRideWithIdExist(long id) {
        RideResponse expected = RideTestUtil.getRideResponse();
        Ride retrievedRide = RideTestUtil.getRide();

        doReturn(Optional.of(retrievedRide))
                .when(rideRepository)
                .findById(id);
        doReturn(expected)
                .when(rideMapper)
                .fromEntityToResponse(retrievedRide);
        doReturn(true)
                .when(rideRepository)
                .existsById(id);

        Optional<Ride> ride = rideRepository.findById(id);
        assertTrue(ride.isPresent());
    }

    @When("The id {long} is passed to the findById method")
    public void theIdIsPassedToTheFindByIdMethod(long id) {
        try {
            rideResponse = rideService.getRideById(id);
        } catch (RideNotFoundException ex) {
            exception = ex;
        }
    }

    @Then("The response should contain ride with id {long}")
    public void theResponseShouldContainRideWithId(long id) {
        Ride ride = rideRepository.findById(id).get();
        RideResponse expected = rideMapper.fromEntityToResponse(ride);

        assertThat(rideResponse).isEqualTo(expected);
    }

    @Given("A ride with id {long} doesn't exist")
    public void aRideWithIdDoesntExist(long id) {
        Optional<Ride> ride = rideRepository.findById(id);

        assertFalse(ride.isPresent());
    }

    @Then("The RideNotFoundException with id {long} should be thrown")
    public void theRideNotFoundExceptionWithIdShouldBeThrown(long id) {
        assertThat(exception.getMessage()).isEqualTo(String.format(ExceptionMessages.RIDE_NOT_FOUND_EXCEPTION, id));
    }

    @Given("A ride with ride request is correct")
    public void aRideRatingWithIdIsCorrect() {
        Ride notSavedRide = getNotSavedRide();
        Ride savedRide = getRide();
        RideResponse rideResponse = getRideResponse();
        PassengerResponse passengerResponse = getPassengerResponse();
        ChargeResponse chargeResponse = getChargeResponse();

        doReturn(notSavedRide)
                .when(rideMapper)
                .fromRequestToEntity(any(RideRequest.class));
        doReturn(passengerResponse)
                .when(passengerClient)
                .getPassenger(DEFAULT_PASSENGER_ID);
        doReturn(chargeResponse)
                .when(paymentClient)
                .chargeFromCustomer(any(CustomerChargeRequest.class));
        doReturn(savedRide)
                .when(rideRepository)
                .save(any(Ride.class));
        doNothing()
                .when(rideProducer)
                .sendMessage(any(RideForDriver.class));
        doReturn(rideResponse)
                .when(rideMapper)
                .fromEntityToResponse(any(Ride.class));
    }

    @When("A create request with ride request is passed to the add method")
    public void aCreateRequestWithIdIsPassedToTheAddMethod() {
        RideRequest createRequest = RideTestUtil.getRideRequest();
        try {
            rideResponse = rideService.findRide(createRequest);
        } catch (Exception e) {
            exception = e;
        }

    }

    @Then("The response should contain created ride response")
    public void theResponseShouldContainCreatedRideResponse() {
        var expected = RideTestUtil.getRideResponse();
        assertThat(rideResponse).isEqualTo(expected);
    }

    @Given("A ride with id {long} not started")
    public void aRideWithIdNotStarted(long id) {
        Ride ride = getRideWithDriver();
        Ride startedRide = getStartedRide();
        RideResponse response = getStartedRideResponse();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(id);
        doReturn(startedRide)
                .when(rideRepository)
                .save(ArgumentMatchers.any(Ride.class));
        doReturn(response)
                .when(rideMapper)
                .fromEntityToResponse(ArgumentMatchers.any(Ride.class));
    }

    @When("Ride id {long} is passed to the start method")
    public void rideIdIsPassedToTheStartMethod(long id) {
        try {
            rideResponse = rideService.startRide(id);
        }catch (Exception ex) {
            exception = ex;
        }
    }

    @Given("A ride with id {long} started")
    public void aRideWithIdStarted(long id) {
        Ride ride = getStartedRide();
        Ride endedRide = getEndedRide();
        RideResponse response = getEndedRideResponse();

        doReturn(Optional.of(ride))
                .when(rideRepository)
                .findById(id);
        doReturn(endedRide)
                .when(rideRepository)
                .save(ArgumentMatchers.any(Ride.class));
        doReturn(response)
                .when(rideMapper)
                .fromEntityToResponse(ArgumentMatchers.any(Ride.class));
    }

    @When("Ride id {long} is passed to the end method")
    public void rideIdIsPassedToTheEndMethod(long id) {
        try {
            rideResponse = rideService.endRide(id);
        }catch (Exception ex) {
            exception = ex;
        }
    }

    @Given("A ride list with driver id {long}")
    public void aRideListWithDriverId(long id) {

        RideListResponse expected = getRideListResponse();
        List<Ride> rideList = getRideList();

        doReturn(rideList)
                .when(rideRepository)
                .getAllByDriverId(id);
        doReturn(expected.getRideResponseList().get(0))
                .when(rideMapper)
                .fromEntityToResponse(rideList.get(0));
        doReturn(expected.getRideResponseList().get(1))
                .when(rideMapper)
                .fromEntityToResponse(rideList.get(1));

    }

    @When("Driver id {long} is passed to get ride by id method")
    public void driverIdIsPassedToGetRideByIdMethod(long id) {
        try {
            rideListResponse = rideService.getListOfRidesByDriverId(id);
        }catch (Exception ex) {
            exception = ex;
        }

    }

    @Then("The response should contain list of rides")
    public void theResponseShouldContainListOfRides() {
        var expected = getRideListResponse();
        assertThat(rideListResponse).isEqualTo(expected);
    }

    @Given("A ride list with passenger id {long}")
    public void aRideListWithPassengerId(long id) {
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

    }

    @When("Passenger id {long} is passed to get ride by id method")
    public void passengerIdIsPassedToGetRideByIdMethod(long id) {
        try {
            rideListResponse = rideService.getListOfRidesByPassengerId(id);
        }catch (Exception ex) {
            exception = ex;
        }
    }
}
