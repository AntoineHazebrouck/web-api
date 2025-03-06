package fr.imt.invocations.Service;

import fr.imt.invocations.Entity.Invocations;
import fr.imt.invocations.Entity.InvocationsDTO;
import fr.imt.invocations.Repository.InvocationsRepository;
import org.aopalliance.intercept.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static fr.imt.invocations.Entity.Invocations.generateMonstre;

@Service
public class InvocationsService {

    @Autowired
    private InvocationsRepository invocationsRepository;

//    public Invocations createInvocation(InvocationsDTO invocationsDTO) {
//        return invocationsRepository.save(new Invocations(invocationsDTO.getId(), invocationsDTO.getIdMonstre(), invocationsDTO.getIdJoueur()));
//    }

    public Invocations getInvocationById(UUID id) {
        return invocationsRepository.findById(id);
    }

    public void deleteInvocation(int id) {
        invocationsRepository.deleteById(id);
    }

    public List<Invocations> getInvocationsByIdMonstre(int id) {
        return invocationsRepository.findAllByIdMonstre(id);
    }

    public List<Invocations> getInvocationsByIdJoueur(int id) {
        return invocationsRepository.findAllByIdJoueur(id);
    }

    public int invoquerMonstre(int playerId) {
        int monstreId = getApiMonstreId();

        if (monstreId != -1 ) {
            Invocations invocation = new Invocations(UUID.randomUUID(), monstreId, playerId);
            invocationsRepository.save(invocation);

            sendMonstreToPlayer(playerId, monstreId);
        } else {
            System.err.println("❌ Erreur lors de l'invocation du monstre ");
        }

        return monstreId;
    }

    public int getApiMonstreId() {
        int idMonstre = generateMonstre();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://monsters-api:8080/monsters/create/" + idMonstre)) // Remplace avec ton URL
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return Integer.parseInt(response.body().trim());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1; // Valeur de secours si l'API échoue
    }

    private void sendMonstreToPlayer(int playerId, int monstreId) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://players-api:8080/players/" + playerId + "/monsters/" + monstreId + "/add"))
                .header("Accept", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("✅ Monstre invoqué et ajouté au joueur !");
            } else {
                System.err.println("❌ Erreur lors de l'ajout du monstre au joueur : " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
