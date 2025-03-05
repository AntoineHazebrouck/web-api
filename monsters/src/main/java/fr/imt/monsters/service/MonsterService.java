package fr.imt.monsters.service;

import fr.imt.monsters.entity.Monster;
import fr.imt.monsters.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MonsterService {

    @Autowired
    private MonsterRepository monsterRepository;

    public Monster findById(int id) {
        return monsterRepository.findById(id);
    }

    public int getLevel(int id) {
        Monster monster = monsterRepository.findById(id);
        return monster.getLevel();
    }

    public Monster gainLevel(int monsterId, int skillId) {
        Monster monster = monsterRepository.findById(monsterId);
        monster.gainLevel(skillId);
        return monsterRepository.save(monster);
    }

    public int createMonster(int typeId) {
        List<Monster> monsters = monsterRepository.findAll();

        int maxId = monsters.stream()
                .max(Comparator.comparingInt(Monster::getId))
                .map(Monster::getId)
                .orElse(0);

        int newId = maxId + 1;

        Monster monster = new Monster(typeId, newId);

        monsterRepository.save(monster);

        return monster.getId();
    }
}