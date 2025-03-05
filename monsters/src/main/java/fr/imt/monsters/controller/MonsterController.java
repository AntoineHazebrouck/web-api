package fr.imt.monsters.controller;

import fr.imt.monsters.entity.Monster;
import fr.imt.monsters.service.MonsterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monsters")
public class MonsterController {

    @Autowired
    private MonsterService monsterService;

    @GetMapping("/{id}")
    public Monster getMonsterFromId(@PathVariable int id) {
        return monsterService.findById(id);
    }

    @GetMapping("/{id}/level")
    public int getMonsterLevel(@PathVariable int id) {
        return monsterService.getLevel(id);
    }

    @PostMapping("/create/{id}")
    public Monster createMonster(@PathVariable int id) {
        return monsterService.createMonster(id);
    }
    @GetMapping("/{monsterId}/level-up/{skillId}")
    public Monster gainLevel(@PathVariable int monsterId, @PathVariable int skillId) {
        return monsterService.gainLevel(monsterId, skillId);
    }
}
