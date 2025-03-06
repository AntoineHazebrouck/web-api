package fr.imt.authentication.controllers.register;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.imt.authentication.dtos.user.UserDto;
import fr.imt.authentication.services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
class RegisterController implements RegisterControllerDefinition {
	private final UserService userService;

	@Override
	@PostMapping
	public String register(@RequestBody UserDto user) {

		return userService.register(user.getLogin(), user.getPassword()).getLogin();
	}
}
