package fr.imt.players.service;

import fr.imt.players.entity.Player;
import fr.imt.players.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player findById(int id) {
        return playerRepository.findById(id);
    }

    public int getPlayerLevel(int id) {
        Player player = playerRepository.findById(id);
        return player.getLevel();
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Integer> getPlayerMonsters(int id) {
        Player player = playerRepository.findById(id);
        return player.getMonsters();
    }

    public List<Integer> addMonster(int playerId, int monsterId) {
        Player player = findById(playerId);
        if (player.canAddMonster() && !player.hasMonster(monsterId)) {
            player.addMonster(monsterId);
            playerRepository.save(player);
        }
        return player.getMonsters();
    }

    public List<Integer> removeMonster(int playerId, int monsterId) {
        Player player = findById(playerId);
        if (player.hasMonster(monsterId)) {
            player.removeMonster(monsterId);
            playerRepository.save(player);
        }
        return player.getMonsters();
    }

    public Player addExperience(int playerId, int experience) {
        Player player = findById(playerId);
        player.addExperience(experience);
        return playerRepository.save(player);
    }

    public Player gainLevel(int playerId) {
        Player player = findById(playerId);
        player.gainLevel();
        return playerRepository.save(player);
    }
}
