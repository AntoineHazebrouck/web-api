package fr.imt.invocations.Controller;

import fr.imt.invocations.Entity.Invocations;
import fr.imt.invocations.Service.InvocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invocations")
public class InvocationsController {

    @Autowired
    private InvocationsService invocationsService;

    public final static String AUTHENTICATION_SCHEMA = "http";
    public final static String AUTHENTICATION_HOST = "authentication-api";
    public final static int AUTHENTICATION_PORT = 8080;
    public final static String AUTHENTICATION_PATH = "/check-token";

    RestClient restClient = RestClient.create();

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String error) {
            super(error);
        }
    }

    public boolean isAuthenticated(String token) {
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme(AUTHENTICATION_SCHEMA)
                        .host(AUTHENTICATION_HOST)
                        .port(AUTHENTICATION_PORT)
                        .path(AUTHENTICATION_PATH)
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .onStatus(code -> code.is4xxClientError(), (request, response) -> {
                    throw new UnauthorizedException(response.getStatusText());
                })
                .toEntity(String.class);
        return responseEntity.getStatusCode() == HttpStatusCode.valueOf(200);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invocations> getInvocation(@PathVariable UUID id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(invocationsService.getInvocationById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, @RequestHeader String token) {
        invocationsService.deleteInvocation(id);
    }

    @GetMapping("/monstres/{id}")
    public ResponseEntity<List<Invocations>> getMonstreById(@PathVariable int id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(invocationsService.getInvocationsByIdMonstre(id));
    }

    @GetMapping("/joueurs/{id}")
    public ResponseEntity<List<Invocations>> getJoueurById(@PathVariable int id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(invocationsService.getInvocationsByIdJoueur(id));
    }

    @PostMapping("/new/{playerId}")
    public ResponseEntity<Integer> invoquerMonstre(@PathVariable int playerId, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(invocationsService.invoquerMonstre(playerId, token));
    }
}
