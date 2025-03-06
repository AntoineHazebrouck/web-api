package fr.imt.authentication.controllers.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.imt.authentication.dtos.user.UserDto;
import fr.imt.authentication.services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
class LoginController implements LoginControllerDefinition {
	private final UserService userService;

	@Override
	@PostMapping
	public String login(@RequestBody UserDto user) {
		return userService
				.login(user.getLogin(), user.getPassword())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
	}
}
