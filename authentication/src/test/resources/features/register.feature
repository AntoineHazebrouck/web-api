Feature: Register a user

  Rule: User registers via the /register endpoint

    Scenario: User registers properly
      Given the login is "antoine.hazebrouck@gmail.com"
      And the password is "123456"
      When the client calls POST "/register"
      Then the client receives status code 200
      And the client receives "antoine.hazebrouck@gmail.com"
      And the password is encrypted

  Rule: User needs long information

    Scenario: User's password is too short
      Given the login is "antoine.hazebrouck@gmail.com"
      And the password is "E"
      When the client calls POST "/register"
      Then the client receives status code 400

    Scenario: User's login is too short
      Given the login is "a"
      And the password is "123456"
      When the client calls POST "/register"
      Then the client receives status code 400
