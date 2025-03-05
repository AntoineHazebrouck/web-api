package fr.imt.monsters.service;

import fr.imt.monsters.entity.Monster;
import fr.imt.monsters.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Monster createMonster(int id) {
        Monster monster = new Monster(id);
        return monsterRepository.save(monster);
    }
}
