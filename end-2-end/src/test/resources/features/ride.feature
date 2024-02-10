Feature: Ride test
  Scenario: Find ride for existing passenger
    Given Existing passenger with id 1
    When A passenger with id 1 sends request to find ride
    Then Passenger should get response with ride details

  Scenario: Find ride for not existing passenger
    Given Not Existing Passenger with id 100
    When A passenger with id 100 sends request to find ride
    Then A passenger should get PassengerNotFoundException with passenger id 100