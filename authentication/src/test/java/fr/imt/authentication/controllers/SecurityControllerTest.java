// package fr.imt.authentication.controllers;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.within;

// import java.net.URI;
// import java.time.LocalDateTime;
// import java.time.temporal.ChronoUnit;

// import org.assertj.core.api.Condition;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.util.UriComponentsBuilder;

// import fr.imt.authentication.entities.User;
// import fr.imt.authentication.repositories.UserRepository;

// // @ActiveProfiles("local")
// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// class SecurityControllerTest {
// 	@Autowired
// 	PasswordEncoder passwordEncoder;

// 	@Autowired
// 	UserRepository userRepository;

// 	@Autowired
// 	SecurityController securityController;

// 	@Autowired
// 	TestRestTemplate restTemplate;

// 	final String login = "user@gmail.com";
// 	final String password = "password";

// 	@AfterEach
// 	@BeforeEach
// 	void init() {
// 		userRepository.deleteAll();
// 	}

// 	@Test
// 	void check_token_sends_unauthorized_if_token_is_wrong() {
// 		post("/register", login, password);
// 		post("/login", login, password);

// 		assertThat(post("check-token", "a wrong token ").getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
// 	}

// 	@Test
// 	void check_token_sends_unauthorized_when_expired() {
// 		post("/register", login, password);
// 		String token = post("/login", login, password).getBody();

// 		virtuallyChangeTokenExpirationForTestingPurpose(token, LocalDateTime.now().minusMinutes(1));

// 		assertThat(post("/check-token", token).getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
// 	}

// 	@Test
// 	void login_sets_token_expiration() {
// 		post("/register", login, password);
// 		String token = post("/login", login, password).getBody();

// 		assertThat(userRepository.findByToken(token)
// 				.map(User::getExpiration)
// 				.get())

// 				.isCloseTo(LocalDateTime.now().plusHours(1), within(2, ChronoUnit.MINUTES));
// 	}

// 	@Test
// 	void check_token_resets_token_expiration() {
// 		post("/register", login, password);
// 		String token = post("/login", login, password).getBody();

// 		virtuallyChangeTokenExpirationForTestingPurpose(token, LocalDateTime.now().plusMinutes(5));

// 		post("/check-token", token);
// 		assertThat(userRepository.findByToken(token)
// 				.map(User::getExpiration)
// 				.get())

// 				.isCloseTo(LocalDateTime.now().plusHours(1), within(2, ChronoUnit.MINUTES));

// 	}

// 	@Test
// 	void check_token_sends_ok_with_login() {
// 		post("/register", login, password);
// 		String token = post("/login", login, password).getBody();
// 		ResponseEntity<String> response = post("/check-token", token);

// 		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
// 		assertThat(response.getBody()).isEqualTo(login);
// 	}

// 	@Test
// 	void login_unregistered_sends_unauthorized() {
// 		ResponseEntity<String> response = post("/login", login, password);

// 		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
// 	}

// 	@Test
// 	void login_sends_ok_with_token() {
// 		post("/register", login, password);
// 		ResponseEntity<String> response = post("/login", login, password);

// 		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
// 		assertThat(response.getBody()).isNotBlank().startsWith("$");
// 	}



// 	private ResponseEntity<String> post(
// 			String path,
// 			String login,
// 			String password) {
// 		HttpHeaders headers = new HttpHeaders();
// 		headers.setBasicAuth(login, password);
// 		return post(path, headers);
// 	}

// 	private ResponseEntity<String> post(
// 			String path,
// 			String token) {
// 		HttpHeaders headers = new HttpHeaders();
// 		headers.setBearerAuth(token);
// 		return post(path, headers);
// 	}

// 	private ResponseEntity<String> post(String path, HttpHeaders headers) {
// 		return restTemplate.exchange(
// 				url(path),
// 				HttpMethod.POST,
// 				new HttpEntity<>(headers),
// 				String.class);
// 	}

// 	private void virtuallyChangeTokenExpirationForTestingPurpose(String token, LocalDateTime newTime) {
// 		User user = userRepository.findByToken(token).get();
// 		user.setExpiration(newTime);
// 		userRepository.save(user);
// 	}

// 	@LocalServerPort
// 	int port;

// 	private URI url(String path) {
// 		return UriComponentsBuilder.newInstance()
// 				.scheme("http")
// 				.host("localhost")
// 				.port(port)
// 				.path(path)
// 				.build().toUri();
// 	}

// 	private Condition<String> matches(String rawPassword) {
// 		return new Condition<>(
// 				encodedPassword -> passwordEncoder.matches(rawPassword, encodedPassword),
// 				"matching with '%s' according to PasswordEncoder".formatted(rawPassword));
// 	}
// }
