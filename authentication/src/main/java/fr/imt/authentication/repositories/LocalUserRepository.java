package fr.imt.authentication.repositories;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import fr.imt.authentication.entities.User;

@Repository
@Profile("local")
public class LocalUserRepository implements UserRepository {
	private final Set<User> users = new HashSet<>();

	@Override
	public Optional<User> findByLoginAndPassword(String login, String password) {
		return users.stream()
				.filter(user -> user.getLogin().equals(login))
				.filter(user -> user.getPassword().equals(password))
				.findFirst();
	}

	@Override
	public User save(User user) {
		users.add(user);
		return user;
	}

	@Override
	public Optional<User> findByToken(String token) {
		return users.stream()
				.filter(user -> user.getToken().equals(token))
				.findFirst();
	}
}
