Feature: Broadcast

  Scenario: Broadcast Message To User(s)
    Given users:
      | userA |
    When message "burning ❤️‍🔥" is sent to users
    Then broadcast recieved