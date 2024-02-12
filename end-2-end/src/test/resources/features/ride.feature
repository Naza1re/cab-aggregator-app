Feature: end to end tests
  Scenario: Find ride for existing passenger
    Given Existing passenger with id 11
    When A passenger with id 11 sends request to find ride
    Then Passenger should get response with ride details with passenger id 11 and status active

  Scenario: Find ride for non existing passenger
    Given Non existing passenger with id 100
    When A passenger with id 100 sends request to find ride
    Then NotfoundException should be thrown for passenger with id 100




