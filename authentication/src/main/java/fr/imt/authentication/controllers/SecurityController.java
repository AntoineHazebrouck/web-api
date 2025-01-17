package fr.imt.authentication.controllers;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.imt.authentication.dtos.UserDto;
import fr.imt.authentication.entities.User;
import fr.imt.authentication.repositories.UserRepository;
import fr.imt.authentication.services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
class SecurityController {
	private final UserService userService;
	private final UserRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto userDto) {

		// @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language // TODO use http
		// Authorization header
		return userRepository.findByLoginAndPassword(
				userDto.getLogin(),
				userDto.getPassword())
				.map(user -> {
					user.setToken("TODO");
					user.setExpiration(LocalDateTime.now().plusHours(1));
					return user;
				})
				// .map(user -> userRepository.deleteByLogin(user.getLogin()))
				.map(userRepository::save)
				.map(User::getToken)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(401).build());
	}

	@PostMapping("/register")
	public UserDto register(@RequestBody UserDto user) {
		return UserDto.from(userRepository.save(user.toEntity()));
	}

	@PostMapping("/check-token")
	public ResponseEntity<String> checkToken(@RequestParam String token) {
		return userRepository.findByToken(token)
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