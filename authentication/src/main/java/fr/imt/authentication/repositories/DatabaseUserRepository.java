package fr.imt.authentication.repositories;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.imt.authentication.entities.User;

@Profile("!local")
interface DatabaseUserRepository extends UserRepository, MongoRepository<User, String> {

	@Override
	Optional<User> findByLogin(String login);
}
