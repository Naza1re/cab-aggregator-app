Feature: Passenger Service
  Scenario: Getting passenger rating by existing id
    Given A passenger rating with id 1 exist
    When The id 1 is passed to the findByPassengerId method
    Then The response should contain passenger rating with id 1

  Scenario: Getting a passenger by non-existing id
    Given A passenger rating with id 100 doesn't exist
    When The id 100 is passed to the findByPassengerId method
    Then The PassengerRatingNotFoundException with id 100 should be thrown

  Scenario: Getting driver rating by existing id
    Given A driver rating with id 1 exist
    When The id 1 is passed to the findByDriverId method
    Then The response should contain driver rating with id 1

  Scenario: Getting a passenger by non-existing id
    Given A driver rating with id 100 doesn't exist
    When The id 100 is passed to the findByDriverId method
    Then The DriverRatingNotFoundException with id 100 should be thrown

  Scenario: Delete passenger rating by non-existing id
    Given A passenger rating with id 100 doesn't exist
    When The id 100 is passed to the deleteByPassengerId method
    Then The PassengerRatingNotFoundException with id 100 should be thrown

  Scenario: Delete driver rating by non-existing id
    Given A driver rating with id 100 doesn't exist
    When The id 100 is passed to the deleteByDriverId method
    Then The DriverRatingNotFoundException with id 100 should be thrown

  Scenario: Creating a new passenger rating with unique data
    Given A passenger rating with id 1 is unique
    When A create request with id 1 is passed to the passenger rating add method
    Then The response should contain created passenger rating response

  Scenario: Creating a new driver rating with unique data
    Given A driver rating with id 1 is unique
    When A create request with id 1 is passed to the driver rating add method
    Then The response should contain created driver rating response
