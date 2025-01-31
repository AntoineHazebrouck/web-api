package fr.imt.authentication.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import fr.imt.authentication.entities.User;

public interface UserRepository {
	Optional<User> findByTokenAndExpirationAfter(String token, LocalDateTime time);

	Optional<User> findByLogin(String login);

	User save(User user);

	void deleteAll();

	void deleteById(String login);
}
