package fr.imt.authentication.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import fr.imt.authentication.entities.User;
import fr.imt.authentication.repositories.UserRepository;
import fr.imt.authentication.services.AuthorizationHeaderService;
import fr.imt.authentication.services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
class SecurityController {
	private final UserService userService;
	private final UserRepository userRepository;
	private final AuthorizationHeaderService authorizationHeaderService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
		if (!authorizationHeaderService.isBasic(authorization))
			return ResponseEntity.badRequest().build();

		String login = authorizationHeaderService.retrieveCredentials(authorization).getFirst();
		String password = authorizationHeaderService.retrieveCredentials(authorization).getSecond();

		return userRepository.findByLoginAndPassword(
				login,
				password)
				.map(user -> {
					user.setToken(generateToken());
					user.setExpiration(LocalDateTime.now().plusHours(1));
					return user;
				})
				.map(userRepository::save)
				.map(User::getToken)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(401).build());
	}

	@PostMapping("/register")
	public String register(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
		User user = new User();
		user.setLogin(authorizationHeaderService.retrieveCredentials(authorization).getFirst());
		user.setPassword(authorizationHeaderService.retrieveCredentials(authorization).getSecond());
		return userRepository.save(user).getLogin();
	}

	@PostMapping("/check-token")
	public ResponseEntity<String> checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
		if (!authorizationHeaderService.isBearer(authorization))
			return ResponseEntity.badRequest().build();

		return userRepository.findByToken(authorizationHeaderService.retrieveToken(authorization))
				.filter(user -> user.getExpiration().isAfter(LocalDateTime.now()))
				.map(user -> {
					user.setExpiration(LocalDateTime.now().plusHours(1));
					return user;
				})
				.map(User::getLogin)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(401).build());
	}

	private String generateToken() {
		return "TODO";
	}
}