package fr.imt.authentication.dtos;

import fr.imt.authentication.entities.User;
import lombok.Data;

@Data
public class UserDto {
	private String login;
	private String password;

	private String token;

	public User toEntity() {
		var user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setToken(token);
		return user;
	}

	public static UserDto from(User user) {
		var dto = new UserDto();
		dto.setLogin(user.getLogin());
		dto.setPassword(user.getPassword());
		dto.setToken(user.getToken());
		return dto;
	}
}