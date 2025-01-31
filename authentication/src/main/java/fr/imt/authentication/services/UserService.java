package fr.imt.authentication.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.imt.authentication.entities.User;
import fr.imt.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User register(String login, String password) {
		userRepository.deleteById(login);
		User user = new User();
		user.setLogin(login);
		user.setPassword(passwordEncoder.encode(password));
		return userRepository.save(user);
	}

	public Optional<String> login(String login, String password) {

		return userRepository.findByLogin(login)
				.filter(user -> passwordEncoder.matches(password, user.getPassword()))
				.map(this::setGenerateEncryptedToken)
				.map(UserService::postponeExpiration)
				.map(userRepository::save)
				.map(User::getToken);
	}

	public Optional<User> postponeTokenExpiration(String token) {

		return userRepository
				.findByToken(token)
				.filter(user -> user.getExpiration().isAfter(LocalDateTime.now()))
				.map(UserService::postponeExpiration)
				.map(userRepository::save);
	}

	private User setGenerateEncryptedToken(User user) {
		String generatedToken = passwordEncoder.encode("%s-%s".formatted(user.getLogin(), LocalDateTime.now()));
		user.setToken(generatedToken);
		return user;
	}

	private static User postponeExpiration(User user) {
		user.setExpiration(LocalDateTime.now().plusHours(1));
		return user;
	}
}
