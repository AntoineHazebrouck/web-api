package fr.imt.authentication.entities;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Document
public class User {
	@MongoId
	private String login;
	private String password;

	@EqualsAndHashCode.Exclude
	private String token;
	@EqualsAndHashCode.Exclude
	private LocalDateTime expiration;
}
