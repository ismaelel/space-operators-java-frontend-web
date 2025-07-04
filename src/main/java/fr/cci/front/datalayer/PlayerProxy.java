package fr.cci.front.datalayer;

import fr.cci.front.configuration.TokenContext;
import fr.cci.front.datalayer.util.RestAuthHelper;
import fr.cci.front.model.PlayerModel;
import fr.cci.front.model.UserModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Repository
public class PlayerProxy {
    private final TokenContext tokenContext;
    private final String baseApiUrl = "http://localhost:8080/api";
    private final RestTemplate restTemplate = new RestTemplate();

    public PlayerProxy(final TokenContext tokenContext) {
        this.tokenContext = tokenContext;
    }

    private HttpHeaders createTokenHeader(final String token) {
        String authHeader = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        return headers;
    }

    /** PermitAll routes **/
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

    /** Secured routes **/
    public List<PlayerModel> getPlayers1() {
        HttpEntity<Void> request = new HttpEntity<>(
                createTokenHeader(tokenContext.getToken())
        );

        ResponseEntity<List<PlayerModel>> response = restTemplate.exchange(
                //baseApiUrl + "/user",
                baseApiUrl + "/users",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<PlayerModel>>() {}
        );

        return response.getBody();
    }

    public PlayerModel getPlayerByPlayername(String username) {
        HttpEntity<Void> request = new HttpEntity<>(
                createTokenHeader(tokenContext.getToken())
        );

        ResponseEntity<PlayerModel> response = restTemplate.exchange(
                baseApiUrl + "/user/" + username,
                HttpMethod.GET,
                request,
                PlayerModel.class
        );

        return response.getBody();
    }

    public PlayerModel getPlayerInformation() {
//		HttpEntity<Void> request = new HttpEntity<>(
//				createTokenHeader(tokenContext.getToken())
//		);
//		System.out.println("TOKEN AVAVA = " + tokenContext.getToken());

        String token = tokenContext.getToken(); System.out.println("Playerproxy : TOKEN : " + token);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PlayerModel> response = restTemplate.exchange(
                //baseApiUrl + "/me",
                baseApiUrl + "/player/profile2",
                HttpMethod.GET,
                request,
                PlayerModel.class
        );

        System.out.println("2606 RESPONSE BODY : " + response.getBody());

        return response.getBody();
    }

    public PlayerModel getPlayerProfile(String token) {
        HttpEntity<Void> request = new HttpEntity<>(
                createTokenHeader(tokenContext.getToken())
        );

        ResponseEntity<PlayerModel> response = restTemplate.exchange(
                baseApiUrl + "/me",
                HttpMethod.GET,
                request,
                PlayerModel.class
        );

        return response.getBody();
    }

    public List<PlayerModel> getPlayers() {
        String token = tokenContext.getToken();
        System.out.println("DASHBOARD TOKEN : " + token); // pour debug

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // ARRET ICI
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<PlayerModel>> response = restTemplate.exchange(
                baseApiUrl + "/player/all",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<PlayerModel>>() {}
        );

        return response.getBody();
    }

    public PlayerModel getById(String id) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<Void> request = helper.buildAuthEntity();
        System.out.println("TOLKIEN + " + tokenContext.getToken());
        ResponseEntity<PlayerModel> response = restTemplate.exchange(
                baseApiUrl + "/player/" + id,
                HttpMethod.GET,
                request,
                PlayerModel.class
        );

        return response.getBody();
    }

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
}
