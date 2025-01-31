Feature: Login a registered user to provide an access token valid for an hour

  Background: Antoine is registered
    Given the login is "antoine.hazebrouck@gmail.com"
    And the password is "123456"
    And the client calls POST "/register"

  Scenario: User logs in properly and retrieves a token
    Given the login is "antoine.hazebrouck@gmail.com"
    And the password is "123456"
    When the client calls POST "/login"
    Then the client receives status code 200
    And the client receives a valid token
    And the token expires after an hour

  Scenario: User is unknown
    Given the login is "toto.tata@gmail.com"
    And the password is "123456"
    When the client calls POST "/login"
    Then the client receives status code 401

  Scenario: User password is wrong
    Given the login is "antoine.hazebrouck@gmail.com"
    And the password is "999999"
    When the client calls POST "/login"
    Then the client receives status code 401
