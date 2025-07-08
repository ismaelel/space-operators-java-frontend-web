package fr.cci.front.datalayer;

import fr.cci.front.configuration.TokenContext;
import fr.cci.front.datalayer.util.RestAuthHelper;
import fr.cci.front.model.PlayerModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Classe responsable des appels REST vers l'API pour la gestion des joueurs.
 * Sert de pont entre le service et les endpoints REST sécurisés ou publics.
 */
@Repository
public class PlayerProxy {

    private final TokenContext tokenContext;
    private final String baseApiUrl = "http://26.195.1.69:8080/api";
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Constructeur avec injection du contexte de token.
     *
     * @param tokenContext le contexte contenant le token JWT
     */
    public PlayerProxy(final TokenContext tokenContext) {
        this.tokenContext = tokenContext;
    }

    /**
     * Crée les en-têtes HTTP avec le token d'authentification.
     *
     * @param token le token JWT
     * @return les en-têtes HTTP avec autorisation
     */
    private HttpHeaders createTokenHeader(final String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }


    /**
     * Inscription d’un nouvel utilisateur.
     *
     * @param player le joueur à enregistrer
     */
    public void add(PlayerModel player) {
        Map<String, String> payload = Map.of(
                "email", player.getEmail(),
                "playerName", player.getPlayerName(),
                "password", player.getPassword()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
        restTemplate.exchange(
                baseApiUrl + "/auth/register",
                HttpMethod.POST,
                request,
                Void.class
        );
    }

    /**
     * Authentifie un utilisateur et retourne le token JWT.
     *
     * @param player l’utilisateur avec email et mot de passe
     * @return le token JWT
     */
    public String login(PlayerModel player) {
        Map<String, String> payload = Map.of(
                "email", player.getEmail(),
                "password", player.getPassword()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                baseApiUrl + "/auth/login",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody().get("token");
    }


    /**
     * Récupère la liste de tous les joueurs.
     *
     * @return liste des joueurs
     */
    public List<PlayerModel> getPlayers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenContext.getToken());
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List<PlayerModel>> response = restTemplate.exchange(
                baseApiUrl + "/player/all",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

    /**
     * Récupère les informations du joueur actuellement connecté.
     *
     * @return informations du joueur connecté
     */
    public PlayerModel getUserInformation() {
        RestAuthHelper authHelper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<Void> request = authHelper.buildAuthEntity();

        ResponseEntity<PlayerModel> response = restTemplate.exchange(
                baseApiUrl + "/player/profile2",
                HttpMethod.GET,
                request,
                PlayerModel.class
        );

        return response.getBody();
    }

    /**
     * Récupère les informations d’un joueur par ID.
     *
     * @param id identifiant du joueur
     * @return joueur correspondant
     */
    public PlayerModel getById(String id) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<Void> request = helper.buildAuthEntity();

        ResponseEntity<PlayerModel> response = restTemplate.exchange(
                baseApiUrl + "/player/" + id,
                HttpMethod.GET,
                request,
                PlayerModel.class
        );

        return response.getBody();
    }

    /**
     * Met à jour les informations d’un joueur.
     *
     * @param player joueur à mettre à jour
     */
    public void update(PlayerModel player) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<PlayerModel> request = helper.buildJsonEntity(player);

        restTemplate.exchange(
                baseApiUrl + "/player/" + player.getPlayerId(),
                HttpMethod.PUT,
                request,
                Void.class
        );
    }

    /**
     * Supprime un joueur par son identifiant.
     *
     * @param id identifiant du joueur à supprimer
     */
    public void delete(String id) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<Void> request = helper.buildAuthEntity();

        restTemplate.exchange(
                baseApiUrl + "/player/" + id,
                HttpMethod.DELETE,
                request,
                Void.class
        );
    }

    /**
     * Récupère le profil d’un joueur en utilisant un token spécifique.
     *
     * @param token token JWT à utiliser (non utilisé ici)
     * @return joueur correspondant
     */
    public PlayerModel getPlayerProfile(String token) {
        HttpEntity<Void> request = new HttpEntity<>(createTokenHeader(tokenContext.getToken()));

        ResponseEntity<PlayerModel> response = restTemplate.exchange(
                baseApiUrl + "/me",
                HttpMethod.GET,
                request,
                PlayerModel.class
        );

        return response.getBody();
    }

    /**
     * Récupère un joueur par son nom d’utilisateur.
     *
     * @param username le nom du joueur
     * @return joueur correspondant
     */
    public PlayerModel getPlayerByPlayername(String username) {
        HttpEntity<Void> request = new HttpEntity<>(createTokenHeader(tokenContext.getToken()));

        ResponseEntity<PlayerModel> response = restTemplate.exchange(
                baseApiUrl + "/user/" + username,
                HttpMethod.GET,
                request,
                PlayerModel.class
        );

        return response.getBody();
    }

    /**
     * Ancienne méthode (non utilisée ?) pour récupérer la liste des joueurs.
     *
     * @return liste des joueurs
     */
    public List<PlayerModel> getPlayers1() {
        HttpEntity<Void> request = new HttpEntity<>(createTokenHeader(tokenContext.getToken()));

        ResponseEntity<List<PlayerModel>> response = restTemplate.exchange(
                baseApiUrl + "/users",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }
}
