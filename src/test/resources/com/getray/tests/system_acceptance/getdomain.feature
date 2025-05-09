Feature: Get Domain

  Scenario: Valid Domain Given Valid Domain Id
    When get domain with id 1
    Then domain is returned

  Scenario: Domain Null Given Invalid Domain Id
    When get domain with id 234
    Then domain is null