package com.example.end2end.end2endtest;

import com.example.end2end.client.RideFeignClient;
import com.example.end2end.dto.request.RideRequest;
import com.example.end2end.dto.response.RideResponse;
import com.example.end2end.enums.Status;
import com.example.end2end.util.End2EndUtilTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public class End2EndTest {

    private final RideFeignClient rideFeignClient;

    private RideResponse rideResponse;
    private RideRequest rideRequest;
    private Exception exception;


    @Given("Existing passenger with id {long}")
    public void ExistingPassengerWithId(long id) {
        rideRequest = End2EndUtilTest.getRideRequest(1L);
    }

    @When("A passenger with id {long} sends request to find ride")
    public void aPassengerWithIdSendsRequestToFindRide(long id) {
        try {
            rideResponse = rideFeignClient.findRide(rideRequest);
        } catch (Exception ex) {
            exception = ex;
        }

    }

    @Then("Passenger should get response with ride details with passenger id {long} and status active")
    public void passengerShouldGetResponseWithRideDetails(long id) {
        assertThat(rideResponse.getPassengerId()).isEqualTo(id);
        assertThat(rideResponse.getPrice()).isNotZero();
        assertThat(rideResponse.getStatus()).isEqualTo(Status.CREATED);
    }

    @Given("Non existing passenger with id {long}")
    public void nonExistingPassengerWithId(long id) {
        rideRequest = End2EndUtilTest.getRideRequest(id);
    }

    @Then("NotfoundException should be thrown for passenger with id {long}")
    public void notFoundExceptionShouldBeThrown(long id) {
        assertThat(exception.getMessage()).isEqualTo(String.format(End2EndUtilTest.PASSENGER_NOT_FOUND_EXCEPTION, id));
    }

    @Given("Exist ride with id {long} that dont have driver")
    public void existRideWithIdThatDontHaveDriver(long id) {
        rideResponse = rideFeignClient.getRideById(id);
        assertThat(rideResponse.getStatus()).isEqualTo(Status.CREATED);
    }

    @When("Driver with id try to start ride with id {long} that not accepted")
    public void driverWithIdTryToStartRideWithIdThatNotAccepted(long id) {
        try {
            rideResponse = rideFeignClient.startRide(id);
        } catch (Exception ex) {
            exception = ex;
        }
    }

    @Then("RideDontHaveDriverException should be thrown for ride with id {long}")
    public void rideDontHaveDriverExceptionShouldBeThrown(long id) {
        assertThat(exception.getMessage()).isEqualTo(String.format(End2EndUtilTest.RIDE_DONT_HAVE_DRIVER_TO_START, id));
    }
}
