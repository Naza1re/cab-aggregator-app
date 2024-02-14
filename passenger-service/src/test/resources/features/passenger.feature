Feature: Passenger Service
  Scenario: Getting a passenger by existing id
    Given A passenger with id 1 exist
    When The id 1 is passed to the findById method
    Then The response should contain passenger with id 1

  Scenario: Getting a passenger by non-existing id
    Given A passenger with id 100 doesn't exist
    When The id 100 is passed to the findById method
    Then The PassengerNotFoundException with id 100 should be thrown

  Scenario: Creating a passenger with non-unique phone
    Given A passenger with phone "37525948179" exists
    When A create request with phone "37525948179" is passed to the add method
    Then The PhoneAlreadyExistsException should be thrown for phone "37525948179"

  Scenario: Creating a passenger with non-unique email
    Given A passenger with email "mcarim@mail.ru" exists
    When A create request with email "mcarim@mail.ru" is passed to the add method
    Then The EmailAlreadyExistException should be thrown for email "mcarim@mail.ru"

  Scenario: Delete passenger by non-existing id
    Given A passenger with id 100 doesn't exist
    When The id 100 is passed to the deleteById method
    Then The PassengerNotFoundException with id 100 should be thrown

  Scenario: Creating a new passenger with unique data
    Given A passenger with email "mcarim@mail.ru" and phone "375259148179" doesn't exist
    When A create request with email "mcarim@mail.ru", phone "375259148179" is passed to the add method
    Then The response should contain created passenger response

  Scenario: Update passenger by non-existing id
    Given A passenger with id 100 doesn't exist
    When A update request and id 100 is passed to the update method
    Then The PassengerNotFoundException with id 100 should be thrown
