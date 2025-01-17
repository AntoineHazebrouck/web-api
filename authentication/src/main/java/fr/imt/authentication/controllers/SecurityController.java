package fr.imt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.imt.dto.UserDto;
import fr.imt.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
class SecurityController {
	private final UserRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody UserDto user) {
		return userRepository.findByLoginAndPassword(
				user.getLogin(),
				user.getPassword())
				.map(UserDto::from)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(401).build());
	}

	@PostMapping("/register")
	public UserDto register(@RequestBody UserDto user) {
		return UserDto.from(userRepository.save(user.toEntity()));
	}
}