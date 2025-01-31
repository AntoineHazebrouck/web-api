package fr.imt.authentication.features;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.ResponseEntity;

import fr.imt.authentication.controllers.CommonControllerTest;
import fr.imt.authentication.dtos.user.UserDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonSteps extends CommonControllerTest {
	UserDto user = new UserDto();
	ResponseEntity<String> response;

	@When("the client calls POST {string}")
	public void the_client_calls_post(String endpoint) {
		response = restTemplate.postForEntity(url(endpoint), user, String.class);
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
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the token expires after an hour")
	public void the_token_expires_after_an_hour() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}
}
