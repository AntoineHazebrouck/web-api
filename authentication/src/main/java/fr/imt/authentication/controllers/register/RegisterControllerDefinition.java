package fr.imt.authentication.controllers.register;

import fr.imt.authentication.dtos.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "register-controller")
interface RegisterControllerDefinition {

	@Operation(summary = "Register a user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the login sent as a success indicator", content = {
					@Content(mediaType = "text/plain")
			}),
			@ApiResponse(responseCode = "400", description = "The user sent is malformed", content = {
					@Content
			}),
	})
	String register(@Valid UserDto user);
}
