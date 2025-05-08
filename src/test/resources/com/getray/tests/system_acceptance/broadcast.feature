Feature: Broadcast
  Scenario: Broadcast Message To User(s)
    Given users with ids:
      | 35568 |
    When message "Hiba is testing" is sent
    Then broadcast recieved