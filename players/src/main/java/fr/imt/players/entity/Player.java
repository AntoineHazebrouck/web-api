package fr.imt.players.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Document
public class Player {

	private final static int MONSTERS_LIST_BASE_SIZE = 10;
	private final static int MAX_LEVEL = 50;
	private final static int BASE_EXPERIENCE = 50;
	private final static double PER_LEVEL_EXPERIENCE_MULTIPLIER = 1.1;

	@MongoId
	private Integer id;
	@NotBlank
	private String login;
	@Min(value = 0)
	@Max(value = 50)
	private int level;
	private int experience;
	private List<Integer> monsters = new ArrayList<>();
	private int experienceThreshold = BASE_EXPERIENCE;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public List<Integer> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Integer> monsters) {
		this.monsters = monsters;
	}

	public boolean hasMonster(Integer monster) {
		return this.monsters.contains(monster);
	}

	public boolean canAddMonster() {
		return (monsters.size() < MONSTERS_LIST_BASE_SIZE + level);
	}

	public void addMonster(Integer monster) {
		this.monsters.add(monster);
	}

	public void removeMonster(Integer monster) {
		this.monsters.remove(monster);
	}

	public void addExperience(int experience) {
		this.experience += experience;
		while (level < MAX_LEVEL && this.experience >= experienceThreshold) {
			this.experience -= experienceThreshold;
			experienceThreshold = (int) (experienceThreshold * PER_LEVEL_EXPERIENCE_MULTIPLIER);
			level += 1;
		}
	}

	public void gainLevel() {
		if (level < MAX_LEVEL) {
			experience = 0;
			level += 1;
		}
	}
}
