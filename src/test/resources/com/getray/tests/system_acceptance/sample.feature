Feature: Sample HttpBin

  Scenario: Test a GET API
    When calls an API
    Then should be working

  Scenario: Test Capture Bearer Authentication
    When send bearer "token"
    Then capture token

    When authenticating with captured token
    Then successful bearer authentication

  Scenario: Test Basic Authentication
    Given username "user" and password "passwd"
    When authenticate with credentials
    Then successful basic authentication
