package fr.imt.authentication.features;

import java.net.URI;
import java.util.Map;

import org.assertj.core.api.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import fr.imt.authentication.repositories.UserRepository;

public class CommonControllerTest {
	@Autowired
	protected TestRestTemplate restTemplate;
	@Autowired
	protected PasswordEncoder passwordEncoder;
	@Autowired
	protected UserRepository userRepository;

	@LocalServerPort
	private int port;

	protected final URI url(String path) {
		return url(path, Map.of());
	}

	protected final URI url(String path, Map<String, String> params) {
		return UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("localhost")
				.port(port)
				.path(path)
				.queryParams(MultiValueMap.fromSingleValue(params))
				.build().toUri();
	}

	protected final Condition<String> matches(String rawPassword) {
		return new Condition<>(
				encodedPassword -> passwordEncoder.matches(rawPassword, encodedPassword),
				"matching with '%s' according to PasswordEncoder".formatted(rawPassword));
	}
}
