package com.example.end2end.end2endtest;

import com.example.end2end.client.RideFeignClient;
import com.example.end2end.dto.request.RideRequest;
import com.example.end2end.dto.response.RideResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.RequiredArgsConstructor;

@CucumberContextConfiguration
@RequiredArgsConstructor
public class End2EndRideTest {

    private final RideFeignClient rideFeignClient;


    private RideResponse rideResponse;


    @Given("Existing passenger with id {long}")
    public void ExistingPassengerWithId(long id) {

    }

    @When("A passenger with id {long} sends request to find ride")
    public void aPassengerWithIdSendsRequestToFindRide(long id) {
        RideRequest rideRequest =
        try {
            rideResponse = rideFeignClient.findRide()
        }
    }

    @Then("Passenger should get response with ride details")
    public void passengerShouldGetResponseWithRideDetails() {

    }
}
