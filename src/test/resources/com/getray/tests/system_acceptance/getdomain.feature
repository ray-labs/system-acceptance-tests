Feature: Get Domain

  Scenario: Return Valid Domain
    When get domain of sandbox1
    Then domain is returned
