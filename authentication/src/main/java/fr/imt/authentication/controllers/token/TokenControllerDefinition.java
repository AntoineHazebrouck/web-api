package fr.imt.authentication.controllers.token;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "token-controller")
interface TokenControllerDefinition {

	@Operation(summary = "Check a bearer token")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returns the login sent as a success indicator", content = {
					@Content(mediaType = "text/plain")
			}),
			@ApiResponse(responseCode = "401", description = "If the token doesn't exist or has already expired", content = {
					@Content
			})
	})
	String checkToken(@Parameter(description = "Token retrieved from the login controller") String token);
}
