package fr.imt.authentication.repositories;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import fr.imt.authentication.entities.User;

@Repository
@Profile("local")
class LocalUserRepository implements UserRepository {
	private final Set<User> users = new HashSet<>();

	@Override
	public Optional<User> findByLogin(String login) {
		return users.stream()
				.filter(user -> user.getLogin().equals(login))
				.findFirst();
	}

	@Override
	public User save(User user) {
		users.add(user);
		return user;
	}

	@Override
	public void deleteAll() {
		users.clear();
	}

	@Override
	public void deleteById(String login) {
		users.removeIf(user -> user.getLogin().equals(login));
	}

	@Override
	public Optional<User> findByTokenAndExpirationAfter(String token, LocalDateTime time) {
		return users.stream()
				.filter(user -> user.getToken().equals(token))
				.filter(user -> user.getExpiration().isAfter(time))
				.findFirst();
	}
}
