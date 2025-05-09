Feature: Broadcast

  Scenario: Broadcast Message To User(s)
    Given users:
      | userA |
    When broadcast message "final test" is sent
    Then broadcast recieved

  Scenario: Broadcast Message To Single User
    When broadcast message "final test" is sent to userA
    Then broadcast recieved