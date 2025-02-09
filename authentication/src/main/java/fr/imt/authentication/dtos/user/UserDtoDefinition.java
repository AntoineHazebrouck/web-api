package fr.imt.authentication.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;

interface UserDtoDefinition {
	@Schema(example = "antoine.hazebrouck@gmail.com")
	String getLogin();

	@Schema(example = "123456")
	String getPassword();
}
