package fr.imt.invocations.Repository;

import fr.imt.invocations.Entity.Invocations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvocationsRepository extends MongoRepository<Invocations, Integer> {

    Invocations findById(int ID);

    List<Invocations> findAllByIdMonstre(int monstre);

    List<Invocations> findAllByIdJoueur(int joueur);

    void deleteById(int id);
}
