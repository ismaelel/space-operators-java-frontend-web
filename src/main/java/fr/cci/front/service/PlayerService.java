package fr.cci.front.service;

import fr.cci.front.datalayer.PlayerProxy;
import fr.cci.front.datalayer.UserProxy;
import fr.cci.front.model.PlayerModel;
import fr.cci.front.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private PlayerProxy playerProxy;

    public PlayerService(PlayerProxy playerProxy) {
        this.playerProxy = playerProxy;
    }

    public List<PlayerModel> get() {
        return playerProxy.getPlayers();
    }

    public void add(PlayerModel player) {
        playerProxy.add(player);
    }

    public String login(PlayerModel player) {
        return playerProxy.login(player);
    }

    public PlayerModel getPlayerInformation() {
        return playerProxy.getPlayerInformation();
    }

    public PlayerModel getPlayerProfile(String token) {
        return playerProxy.getPlayerProfile(token);
    }

    public List<PlayerModel> getAllPlayers() {
        return playerProxy.getPlayers();
    }

    public PlayerModel getPlayerById(String id) {
        return playerProxy.getById(id);
    }

    public void updatePlayer(PlayerModel player) {
        playerProxy.update(player);
    }

    public void deletePlayer(String id) {
        playerProxy.delete(id);
    }
}
