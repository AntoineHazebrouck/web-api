package fr.imt.players.controller;

import fr.imt.players.entity.Player;
import fr.imt.players.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public Player getPlayerFromId(@PathVariable int id) {
        return playerService.findById(id);
    }

    @GetMapping("/{id}/level")
    public int getLevel(@PathVariable int id) {
        return playerService.getPlayerLevel(id);
    }

    @GetMapping("/{id}/monsters")
    public List<Integer> getMonsters(@PathVariable int id) {
        return playerService.getPlayerMonsters(id);
    }

    @PostMapping
    public Player createPlayer(@RequestBody @Valid Player player) {
        return playerService.createPlayer(player);
    }

    @PatchMapping("/{playerId}/monsters/{monsterId}/add")
    public List<Integer> addMonster(@PathVariable int playerId, @PathVariable int monsterId) {
        return playerService.addMonster(playerId, monsterId);
    }

    @PatchMapping("/{playerId}/monsters/{monsterId}/remove")
    public List<Integer> removeMonster(@PathVariable int playerId, @PathVariable int monsterId) {
        return playerService.removeMonster(playerId, monsterId);
    }

    @PatchMapping("/{playerId}/experience")
    public Player addExperience(@RequestBody Integer experience, @PathVariable int playerId) {
        return playerService.addExperience(playerId, experience);
    }

    @GetMapping("/{playerId}/level-up")
    public Player gainLevel(@PathVariable int playerId) {
        return playerService.gainLevel(playerId);
    }
}
