package fr.imt.invocations.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

public class Invocations {

    @MongoId
    private UUID id;

    private int idMonstre;

    private int idJoueur;

    private Random rand = new Random();

    private static final NavigableMap<Integer, Integer> dropRates = new TreeMap<>();

    static {
        for (Drop drop : Drop.values()) {
            dropRates.put(drop.getId(), drop.getProbabilite());
        }
    }

    public Invocations() {}

    public Invocations(UUID id, int monstre, int joueur) {
        this.id = id;
        this.idMonstre = monstre;
        this.idJoueur = joueur;
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

    public int getIdJoueur() { return idJoueur; }

    public void setIdJoueur(int idJoueur) {this.idJoueur = idJoueur; }

    public int generateMonstre() {
        int randomValue = rand.nextInt(100);

        for (var entry : dropRates.entrySet()) {
            if (randomValue < entry.getKey()) {
                return entry.getValue();
            }
        }
        return 1; // Valeur de secours
    }

}
