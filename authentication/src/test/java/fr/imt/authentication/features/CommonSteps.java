package fr.imt.authentication.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import fr.imt.authentication.dtos.user.UserDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonSteps extends CommonControllerTest {
	UserDto user = new UserDto();
	ResponseEntity<String> response;

	@Before
	void clear() {
		userRepository.deleteAll();
	}

	@When("the client calls POST {string}")
	public void the_client_calls_post(String endpoint) {
		response = restTemplate.postForEntity(url(endpoint), user, String.class);
	}

	@When("the client calls POST {string} with token")
	public void the_client_calls_POST_with_token(String endpoint) {
		response = restTemplate.postForEntity(
				url(endpoint, Map.of("token", response.getBody())),
				endpoint,
				String.class);
	}

	@Given("the login is {string}")
	public void the_login_is(String login) {
		user.setLogin(login);
	}

	@Given("the password is {string}")
	public void the_password_is(String password) {
		user.setPassword(password);
	}

	@Then("the client receives status code {int}")
	public void the_client_receives_status_code(int statusCode) {
		assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
	}

	@Then("the client receives {string}")
	public void the_client_receives(String body) {
		assertThat(response.getBody()).isEqualTo(body);
	}

	@Then("the password is encrypted")
	public void the_password_is_encrypted() {
		assertThat(passwordEncoder.matches(
				user.getPassword(),
				userRepository.findByLogin(user.getLogin()).get().getPassword())).isTrue();
	}

	@Then("the client receives a valid token")
	public void the_client_receives_a_valid_token() {
		String token = response.getBody();
		assertThat(userRepository.findByTokenAndExpirationAfter(token, LocalDateTime.now())).isPresent();
	}

	@Then("the token expires after an hour")
	public void the_token_expires_after_an_hour() {
		String token = response.getBody();
		assertThat(userRepository.findByTokenAndExpirationAfter(token, LocalDateTime.now()).get().getExpiration())
				.isBetween(
						LocalDateTime.now().plusHours(1).minusMinutes(2),
						LocalDateTime.now().plusHours(1).plusMinutes(2));
	}

	@Given("the token is {string}")
	public void the_token_is(String token) {
		response = ResponseEntity.status(response.getStatusCode()).body(token);
	}

	@Given("an hour has passed")
	public void an_hour_has_passed() {
		// virtually go one hour in the future by moving the expiration back one hour
		userRepository.findByTokenAndExpirationAfter(response.getBody(), LocalDateTime.now())
				.map(user -> {
					user.setExpiration(user.getExpiration().minusHours(1));
					return user;
				})
				.ifPresent(userRepository::save);
	}

}
