package fr.imt.invocations.Entity;

import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Random;
import java.util.UUID;

public class Invocation {

    private UUID id;

    private int idMonstre;

    Random random = new Random();

    public Invocation(UUID id, int monstre) {
        this.id = id;
        this.idMonstre = monstre;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getIdMonstre() {
        return idMonstre;
    }

    public void setIdMonstre(int idMonstre) {
        this.idMonstre = idMonstre;
    }

}
