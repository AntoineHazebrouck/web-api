package fr.imt.players.repository;

import fr.imt.players.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, Integer> {

    Player findById(int id);
    Player findByLogin(String login);
}
