package fr.imt.monsters.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.List;
import java.util.UUID;

@Document
public class Monster {

    private final static int LEVEL_UP_HP_GAIN =  50;
    private final static int LEVEL_UP_ATK_GAIN =  5;
    private final static int LEVEL_UP_DEF_GAIN =  5;
    private final static int LEVEL_UP_VIT_GAIN = 3;

    @MongoId
    private String id;
    private ElementaryType type;
    private int hp;
    private int atk;
    private int def;
    private int vit;
    private int level;
    List<MonsterSkill> skills;
    private double lootRate;

    public Monster(int id) {
        switch (id){
            case 1 -> {
                this.id = UUID.randomUUID().toString();
                this.type = ElementaryType.FEU;
                this.hp = 1200;
                this.atk = 450;
                this.def = 300;
                this.vit = 85;
                this.addSkill(new MonsterSkill(1, 125,"atk",25,0,5));
                this.addSkill(new MonsterSkill(2, 250,"atk",27.5,2,7));
                this.addSkill(new MonsterSkill(3, 425,"atk",40,5,5));
                this.lootRate = 0.3;
            }
            case 2 -> {
                this.id = UUID.randomUUID().toString();
                this.type = ElementaryType.VENT;
                this.hp = 1500;
                this.atk = 200;
                this.def = 450;
                this.vit = 80;
                this.addSkill(new MonsterSkill(1, 200,"def",10,0,4));
                this.addSkill(new MonsterSkill(2, 315,"def",17.5,2,5));
                this.addSkill(new MonsterSkill(3, 525,"def",20,6,7));
                this.lootRate = 0.3;
            }
            case 3 -> {
                this.id = UUID.randomUUID().toString();
                this.type = ElementaryType.EAU;
                this.hp = 2500;
                this.atk = 150;
                this.def = 200;
                this.vit = 70;
                this.addSkill(new MonsterSkill(1, 150,"hp",5,0,7));
                this.addSkill(new MonsterSkill(2, 350,"hp",7,2,4));
                this.addSkill(new MonsterSkill(3, 250,"atk",12,5,5));
                this.lootRate = 0.3;
            }
            case 4 -> {
                this.id = UUID.randomUUID().toString();
                this.type = ElementaryType.EAU;
                this.hp = 1200;
                this.atk = 550;
                this.def = 350;
                this.vit = 80;
                this.addSkill(new MonsterSkill(1, 150,"atk",27.5,0,6));
                this.addSkill(new MonsterSkill(2, 285,"atk",27.5,2,9));
                this.addSkill(new MonsterSkill(3, 550,"atk",60,4,4));
                this.lootRate = 0.1;
            }
        }
    }

    public int getLevel(){
        return level;
    }

    public MonsterSkill searchSkill(int num) {
        for (MonsterSkill monsterSkill : skills) {
            if (monsterSkill.getNum() == num) {
                return monsterSkill;
            }
        }
        return null;
    }

    public void gainLevel( int skillNumber) {
        this.level++;
        this.hp += LEVEL_UP_HP_GAIN;
        this.atk += LEVEL_UP_ATK_GAIN;
        this.def += LEVEL_UP_DEF_GAIN;
        this.vit += LEVEL_UP_VIT_GAIN;
        searchSkill(skillNumber).gainLevel();
    }

    public void addSkill(MonsterSkill skill){
        skills.add(skill);
    }
}