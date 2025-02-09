package fr.imt.authentication.controllers.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.imt.authentication.entities.User;
import fr.imt.authentication.services.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/check-token")
class TokenController implements TokenControllerDefinition {
	private final UserService userService;

	@Override
	@PostMapping
	public String checkToken(@RequestParam String token) {
		return userService.postponeTokenExpiration(token)
				.map(User::getLogin)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
	}
}
