Feature: Get Domain

  Scenario: Returns Valid Domain
    When get domain with id 1
    Then domain is returned

  Scenario: Returns Domain Null
    When get domain with id 234
    Then domain is null