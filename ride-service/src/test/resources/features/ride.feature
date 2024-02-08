Feature: Ride service

  Scenario: Getting a ride by existing id
    Given A ride with id 1 exist
    When The id 1 is passed to the findById method
    Then The response should contain ride with id 1

  Scenario: Getting a ride by non-existing id
    Given A ride with id 100 doesn't exist
    When The id 100 is passed to the findById method
    Then The RideNotFoundException with id 100 should be thrown

  Scenario: Creating a new ride with correct data
    Given A ride with ride request is correct
    When A create request with ride request is passed to the add method
    Then The response should contain created ride response

  Scenario: Starting ride by ride id
    Given A ride with id 1 not started
    When Ride id 1 is passed to the start method
    Then The response should contain ride with id 1

  Scenario: Ending ride by ride id
    Given A ride with id 1 started
    When Ride id 1 is passed to the end method
    Then The response should contain ride with id 1

  Scenario: Getting ride list by driver id
    Given A ride list with driver id 1
    When Driver id 1 is passed to get ride by id method
    Then The response should contain list of rides

  Scenario: Getting ride list by passenger id
    Given A ride list with passenger id 1
    When Passenger id 1 is passed to get ride by id method
    Then The response should contain list of rides