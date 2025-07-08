package fr.cci.front.service;

import fr.cci.front.datalayer.PlayerProxy;
import fr.cci.front.model.PlayerModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service permettant de gérer les opérations liées aux joueurs (Player).
 * Il sert de couche intermédiaire entre les contrôleurs et le proxy d'accès aux données.
 */
@Service
public class PlayerService {

    private final PlayerProxy playerProxy;

    /**
     * Constructeur du service avec injection du proxy d'accès aux joueurs.
     *
     * @param playerProxy le proxy pour accéder aux données des joueurs
     */
    public PlayerService(PlayerProxy playerProxy) {
        this.playerProxy = playerProxy;
    }

    /**
     * Récupère la liste de tous les joueurs.
     *
     * @return la liste des joueurs
     */
    public List<PlayerModel> get() {
        return playerProxy.getPlayers();
    }

    /**
     * Ajoute un nouveau joueur.
     *
     * @param player le joueur à ajouter
     */
    public void add(PlayerModel player) {
        playerProxy.add(player);
    }

    /**
     * Authentifie un joueur et retourne un token JWT si la connexion est valide.
     *
     * @param player le joueur contenant les identifiants
     * @return le token JWT
     */
    public String login(PlayerModel player) {
        return playerProxy.login(player);
    }

    /**
     * Récupère les informations du joueur connecté via le token stocké.
     *
     * @return les informations du joueur connecté
     */
    public PlayerModel getUserInformation() {
        return playerProxy.getUserInformation();
    }

    /**
     * Récupère le profil du joueur connecté via un token fourni.
     *
     * @param token le token JWT
     * @return le profil du joueur
     */
    public PlayerModel getPlayerProfile(String token) {
        return playerProxy.getPlayerProfile(token);
    }

    /**
     * Récupère tous les joueurs (identique à {@link #get()}).
     *
     * @return la liste de tous les joueurs
     */
    public List<PlayerModel> getAllPlayers() {
        return playerProxy.getPlayers();
    }

    /**
     * Récupère un joueur par son identifiant.
     *
     * @param id l'identifiant du joueur
     * @return le joueur correspondant
     */
    public PlayerModel getPlayerById(String id) {
        return playerProxy.getById(id);
    }

    /**
     * Met à jour les informations d'un joueur.
     *
     * @param player le joueur à mettre à jour
     */
    public void updatePlayer(PlayerModel player) {
        playerProxy.update(player);
    }

    /**
     * Supprime un joueur par son identifiant.
     *
     * @param id l'identifiant du joueur à supprimer
     */
    public void deletePlayer(String id) {
        playerProxy.delete(id);
    }
}
