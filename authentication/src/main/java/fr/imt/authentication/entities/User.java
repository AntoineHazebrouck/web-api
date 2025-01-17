package fr.imt.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
@Document
public class User {
	@MongoId
	private String login;
	private String password;

	private String token;
	private LocalDateTime expiration;
}
