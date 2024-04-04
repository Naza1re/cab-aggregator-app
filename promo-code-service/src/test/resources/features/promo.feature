Feature: PromoCode test
  Scenario: Get promoCode by id when PromoCode exist
    Given PromoCode with id 1 exist
    When The id 1 is passed to the findById method
    Then The response should contain promoCode with id 1

  Scenario: Delete promoCode by id when PromoCode exist
    Given PromoCode with id "" doesn't exist
    When The id 1 is passed to the delete method
    Then The PromoCodeNotFoundException should be thrown with id 1

  Scenario: Create promoCode with unique data
    Given A promoCode with value "1222" doesn't exist
    When A create request with value "1222" is passed to the add method
    Then The response should contain created promoCode with value "1222"

  Scenario: Update promoCode when promoCode exist
    Given PromoCode with id 1 exist to update
    When PromoCode request is passed with id 1 to update method
    Then The response should contain update promoCode with id 1
