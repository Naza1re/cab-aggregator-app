package com.example.end2end.end2endtest;

import com.example.end2end.client.RideFeignClient;
import com.example.end2end.dto.request.RideRequest;
import com.example.end2end.dto.response.RideResponse;
import com.example.end2end.util.End2EndUtilTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@CucumberContextConfiguration
@RequiredArgsConstructor
public class End2EndRideTest {

    private final RideFeignClient rideFeignClient;


    private RideResponse rideResponse;
    private Exception exception;


    @Given("Existing passenger with id {long}")
    public void ExistingPassengerWithId(long id) {

    }

    @When("A passenger with id {long} sends request to find ride")
    public void aPassengerWithIdSendsRequestToFindRide(long id) {
        RideRequest rideRequest = End2EndUtilTest.getRideRequest(id);
        try {
            rideResponse = rideFeignClient.findRide(rideRequest);
        }catch (Exception ex) {
            exception = ex;
        }
    }

    @Then("Passenger should get response with ride details with passenger id {long}")
    public void passengerShouldGetResponseWithRideDetails(long id) {
            assertThat(rideResponse.getPassengerId()).isEqualTo(id);
            assertThat(rideResponse.getPrice()).isNotZero();
    }
}
