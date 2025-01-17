package fr.imt.invocations.Repository;

import fr.imt.invocations.Entity.Invocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface InvocationRepository extends MongoRepository<Invocation, Integer> {

    Invocation findById(UUID ID);

    List<Invocation> findAllByIdMonstre(int monstre);

    void deleteById(UUID id);
}
