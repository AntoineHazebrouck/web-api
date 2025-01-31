package fr.imt.authentication.controllers;

import java.net.URI;

import org.assertj.core.api.Condition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import fr.imt.authentication.dtos.user.UserDto;

@ActiveProfiles("local") // TODO remove
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommonControllerTest {
	protected final TestRestTemplate restTemplate;
	protected final PasswordEncoder passwordEncoder;

	@LocalServerPort
	private int port;

	protected final String login = "user@gmail.com";
	protected final String password = "password";
	protected final UserDto user = new UserDto(login, password);

	protected CommonControllerTest(
			TestRestTemplate restTemplate,
			PasswordEncoder passwordEncoder) {
		this.restTemplate = restTemplate;
		this.passwordEncoder = passwordEncoder;
	}

	protected final URI url(String path) {
		return UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("localhost")
				.port(port)
				.path(path)
				.build().toUri();
	}

	protected final Condition<String> matches(String rawPassword) {
		return new Condition<>(
				encodedPassword -> passwordEncoder.matches(rawPassword, encodedPassword),
				"matching with '%s' according to PasswordEncoder".formatted(rawPassword));
	}
}
