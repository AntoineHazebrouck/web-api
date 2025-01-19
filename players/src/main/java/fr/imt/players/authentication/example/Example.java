package fr.imt.players.authentication.example;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
class Example {
	private final RestClient authenticationClient;

	public Example() {
		this.authenticationClient = RestClient.builder()
				.baseUrl(UriComponentsBuilder.newInstance()
						.scheme("http")
						.host("authentication-api")
						.port(8080)
						.path("/check-token")
						.build().toUri())
				.build();
	}

	@GetMapping("/hello")
	public ResponseEntity<String> getMethodName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

		final AtomicBoolean unauthorized = new AtomicBoolean(false);
		ResponseEntity<String> token = authenticationClient.post()
				.header(HttpHeaders.AUTHORIZATION, authorization)
				.retrieve()
				.onStatus(status -> HttpStatus.UNAUTHORIZED.isSameCodeAs(status), (request, response) -> {
					unauthorized.set(true);
				})
				.toEntity(String.class);

		return unauthorized.get() ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() : token;
	}

}
