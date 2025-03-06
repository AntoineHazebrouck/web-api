package fr.imt.authentication.controllers.login;

import fr.imt.authentication.dtos.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "login-controller")
interface LoginControllerDefinition {

	@Operation(summary = "Login a registered user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns a bearer token that is valid for an hour", content = {
					@Content(mediaType = "text/plain")
			}),
			@ApiResponse(responseCode = "401", description = "If the user is unregistered or the password is wrong", content = {
					@Content
			}),
			@ApiResponse(responseCode = "400", description = "The user sent is malformed", content = {
					@Content
			}),
	})
	String login(@Valid UserDto user);
}
