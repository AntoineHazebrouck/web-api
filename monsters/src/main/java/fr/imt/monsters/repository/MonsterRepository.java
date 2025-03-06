package fr.imt.monsters.repository;

import fr.imt.monsters.entity.Monster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRepository extends MongoRepository<Monster, Integer> {

    Monster findById(int id);

}
