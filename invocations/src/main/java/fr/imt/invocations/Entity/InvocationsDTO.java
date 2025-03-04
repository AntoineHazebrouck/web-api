package fr.imt.invocations.Entity;

import jakarta.validation.constraints.Size;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

public class InvocationsDTO {

    private UUID id;

    private int idMonstre;

    private int idJoueur;

    private static Random rand = new Random();

    private static final NavigableMap<Integer, Integer> dropRates = new TreeMap<>();

    static {
        for (Drop drop : Drop.values()) {
            dropRates.put(drop.getId(), drop.getProbabilite());
        }
    }

    public InvocationsDTO() {}

    public InvocationsDTO(UUID id, int monstre, int joueur) {
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

    public void setIdJoueur(int idJoueur) { this.idJoueur = idJoueur; }

    public static int generateMonstre() {
        int randomValue = rand.nextInt(100);

        for (var entry : dropRates.entrySet()) {
            if (randomValue < entry.getKey()) {
                return entry.getValue();
            }
        }
        return 1; // Valeur de secours
    }

    public static int fetchExternalMonstreId() {
        int idMonstre = generateMonstre();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api-monstres:8080/monstres/new/" + idMonstre)) // Remplace avec ton URL
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return Integer.parseInt(response.body().trim());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 1; // Valeur de secours si l'API échoue
    }

    public static void sendMonsterToPlayer(int playerId, int monsterId) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api-joueur:8080/" + playerId + "/monsters/" + monsterId + "/add"))
                .header("Accept", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.noBody()) // PATCH sans corps
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("✅ Monstre ajouté au joueur avec succès !");
            } else {
                System.err.println("❌ Erreur lors de l'ajout du monstre : " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
