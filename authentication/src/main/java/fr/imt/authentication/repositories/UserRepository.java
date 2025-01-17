package fr.imt.authentication.repositories;

import java.util.Optional;

import fr.imt.authentication.entities.User;

public interface UserRepository {
	Optional<User> findByToken(String token);

	Optional<User> findByLogin(String login);

	User save(User user);
}
