Feature: Driver service

  Scenario: Getting a driver by existing id
    Given A driver with id 1 exist
    When The id 1 is passed to the findById method
    Then The response should contain driver with id 1

  Scenario: Getting a driver by non-existing id
    Given A driver with id 100 doesn't exist
    When The id 100 is passed to the findById method
    Then The DriverNotFoundException with id 100 should be thrown

  Scenario: Creating a passenger with non-unique phone
    Given A driver with phone "37525948179" exists
    When A create request with phone "37525948179" is passed to the add method
    Then The PhoneAlreadyExistsException should be thrown for phone "37525948179"

  Scenario: Creating a driver with non-unique email
    Given A driver with email "mcarim@mail.ru" exists
    When A create request with email "mcarim@mail.ru" is passed to the add method
    Then The EmailAlreadyExistException should be thrown for email "mcarim@mail.ru"

  Scenario: Creating a driver with non-unique car number
    Given A driver with car number "3856 KX-5" exists
    When A create request with car number "3856 KX-5" is passed to the add method
    Then The CarNumberAlreadyExistException should be thrown for сar number "3856 KX-5"

  Scenario: Update driver by non-existing id
    Given A driver with id 100 doesn't exist
    When A update request and id 100 is passed to the update method
    Then The DriverNotFoundException with id 100 should be thrown