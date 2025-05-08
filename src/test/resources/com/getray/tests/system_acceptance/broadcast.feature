Feature: Broadcast

  Scenario: Broadcast Message To User(s)
    Given users:
      | userA |
    When broadcast message "burning ❤️‍🔥" is sent
    Then broadcast recieved