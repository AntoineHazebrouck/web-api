package fr.imt.authentication.controllers.register;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.imt.authentication.controllers.CommonControllerTest;
import fr.imt.authentication.dtos.user.UserDto;
import fr.imt.authentication.repositories.UserRepository;

class RegisterControllerTest extends CommonControllerTest {
	@Autowired
	public RegisterControllerTest(
			TestRestTemplate restTemplate,
			PasswordEncoder passwordEncoder) {
		super(restTemplate, passwordEncoder);
	}

	@Autowired
	RegisterController registerController;

	@Autowired
	UserRepository userRepository;

	@AfterEach
	@BeforeEach
	void init() {
		userRepository.deleteAll();
	}

	@Test
	void register_sends_ok_with_login() {
		ResponseEntity<String> response = restTemplate.postForEntity(url("/register"), user, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(login);

		assertThat(userRepository.findByLogin(login))
				.map(user -> user.getPassword())
				.hasValueSatisfying(matches(password));
	}

	@Test
	void register_sends_bad_request_when_login_or_password_is_too_short() {
		ResponseEntity<String> response = restTemplate.postForEntity(url("/register"), new UserDto("a", "b"),
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
