package fr.imt.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.imt.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByLoginAndPassword(String login, String password);
}
