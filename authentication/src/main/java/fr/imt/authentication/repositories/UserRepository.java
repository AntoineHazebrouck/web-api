package fr.imt.authentication.repositories;

import java.util.Optional;

import fr.imt.authentication.entities.User;

public interface UserRepository {
	void deleteByLogin(String login);
	Optional<User> findByToken(String token);
	Optional<User> findByLoginAndPassword(String login, String password);
	User save(User user);
}
