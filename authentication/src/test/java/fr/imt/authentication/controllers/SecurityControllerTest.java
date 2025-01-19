package fr.imt.authentication.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SecurityControllerTest {
	@Autowired
	SecurityController securityController;

	@Autowired
	TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	void testCheckToken() {
	}

	@Test
	void testLogin() {
		assertThat(true).isFalse();
	}

	@Test
	void testRegister() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("user@gmail.com", "password");
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:%s/register".formatted(port),
				HttpMethod.POST,
				new HttpEntity<>(headers),
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("user@gmail.com");
	}
}
