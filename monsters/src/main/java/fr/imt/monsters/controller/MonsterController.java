package fr.imt.monsters.controller;

import fr.imt.monsters.entity.Monster;
import fr.imt.monsters.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/monsters")
public class MonsterController {

    @Autowired
    private MonsterService monsterService;

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
    public ResponseEntity<Monster> getMonsterFromId(@PathVariable int id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(monsterService.findById(id));
    }

    @GetMapping("/{id}/level")
    public ResponseEntity<Integer> getMonsterLevel(@PathVariable int id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(monsterService.getLevel(id));
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Integer> createMonster(@PathVariable int id, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(monsterService.createMonster(id));
    }
    @PostMapping("/{monsterId}/level-up/{skillId}")
    public ResponseEntity<Monster> gainLevel(@PathVariable int monsterId, @PathVariable int skillId, @RequestHeader String token) {
        if (!isAuthenticated(token)) return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        return ResponseEntity.ok(monsterService.gainLevel(monsterId, skillId));
    }
}
