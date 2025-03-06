package fr.imt.monsters.entity;

import jakarta.validation.constraints.Max;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Document
public class MonsterSkill {
    @MongoId
    private int num;
    @Min(0)
    private int level;
    private int dmg;
    private String ratioStat;
    @Min(0)
    @Max(100)
    private double ratioPercent;
    @Min(value = 0)
    private int cooldown;
    @Min(value = 1)
    private int levelMax;

    public MonsterSkill(int num, int dmg, String ratioStat, double ratioPercent, int cooldown, int levelMax) {
        this.num = num;
        this.ratioStat = ratioStat;
        this.ratioPercent = ratioPercent;
        this.cooldown = cooldown;
        this.levelMax = levelMax;
    }

    public int getNum() {
        return this.num;
    }

    public int getLevel() {
        return this.level;
    }

    public int getDmg() {
        return this.dmg;
    }

    public String getRatioStat() {
        return this.ratioStat;
    }

    public double getRatioPercent() {
        return this.ratioPercent;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public int getLevelMax() {
        return this.levelMax;
    }

    public void gainLevel(){
        if (level < levelMax){
            level++;
            dmg +=10;
        }
    }
}