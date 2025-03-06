Feature: Assert that a client is logged in with an access token

  Background: Antoine is registered and logged in, thus having received a token
    Given the login is "antoine.hazebrouck@gmail.com"
    And the password is "123456"
    And the client calls POST "/register"
    And the client calls POST "/login"

  Scenario: Token is valid
    When the client calls POST "/check-token" with token
    Then the client receives status code 200
    And the client receives "antoine.hazebrouck@gmail.com"

  Scenario: Token is not valid
    Given the token is "a wrong token"
    When the client calls POST "/check-token" with token
    Then the client receives status code 401

  Scenario: Token expired
    Given an hour has passed
    When the client calls POST "/check-token" with token
    Then the client receives status code 401
