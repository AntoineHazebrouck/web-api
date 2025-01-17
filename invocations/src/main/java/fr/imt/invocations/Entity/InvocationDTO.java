package fr.imt.invocations.Entity;

import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Random;
import java.util.UUID;

public class InvocationDTO {

    @MongoId
    private UUID id;

    @Size(min = 1,max =  4)
    private int idMonstre;

    Random random = new Random();

    public InvocationDTO() {
        this.id = UUID.randomUUID();
        this.idMonstre = random.nextInt(1,5);
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
