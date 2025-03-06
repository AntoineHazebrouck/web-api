package fr.imt.players.controller;

import fr.imt.players.entity.Player;
import fr.imt.players.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

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
    public ResponseEntity<Player> getPlayerFromId(@PathVariable int id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(playerService.findById(id));
    }

    @GetMapping("/{id}/level")
    public ResponseEntity<Integer> getLevel(@PathVariable int id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(playerService.getPlayerLevel(id));
    }

    @GetMapping("/{id}/monsters")
    public ResponseEntity<List<Integer>> getMonsters(@PathVariable int id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(playerService.getPlayerMonsters(id));
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody @Valid Player player, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(playerService.createPlayer(player));
    }

    @PatchMapping("/{playerId}/monsters/{monsterId}/add")
    public ResponseEntity<List<Integer>> addMonster(@PathVariable int playerId, @PathVariable int monsterId, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(playerService.addMonster(playerId, monsterId));
    }

    @PatchMapping("/{playerId}/monsters/{monsterId}/remove")
    public ResponseEntity<List<Integer>> removeMonster(@PathVariable int playerId, @PathVariable int monsterId, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(playerService.removeMonster(playerId, monsterId));
    }

    @PatchMapping("/{playerId}/experience")
    public ResponseEntity<Player> addExperience(@RequestBody Integer experience, @PathVariable int playerId, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(playerService.addExperience(playerId, experience));
    }

    @GetMapping("/{playerId}/level-up")
    public ResponseEntity<Player> gainLevel(@PathVariable int playerId, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(playerService.gainLevel(playerId));
    }
}
