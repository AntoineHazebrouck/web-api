package fr.imt.authentication.dtos.user;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto implements UserDtoDefinition {
	@Size(min = 2)
	private String login;
	@Size(min = 2)
	private String password;

	public UserDto() {
	}

	public UserDto(String login, String password) {
		this.login = login;
		this.password = password;
	}
}
