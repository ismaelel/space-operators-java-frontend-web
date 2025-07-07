package fr.cci.front.datalayer;

import java.util.List;
import java.util.Map;

import fr.cci.front.datalayer.util.RestAuthHelper;
import fr.cci.front.model.PlayerModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import fr.cci.front.configuration.TokenContext;
//import fr.cci.front.model.UserModel;

@Repository
public class UserProxy {

	private final TokenContext tokenContext;
	private final String baseApiUrl = "http://26.195.1.69:8080/api";
	private final RestTemplate restTemplate = new RestTemplate();

	public UserProxy(final TokenContext tokenContext) {
		this.tokenContext = tokenContext;
	}

	private HttpHeaders createTokenHeader(final String token) {
		String authHeader = "Bearer " + token;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authHeader);
		return headers;
	}
//
//	/** PermitAll routes **/
//	public void add(UserModel user) {
//		System.out.println("ONESTLA + " + user.getEmail() + " + " + user.getPlayerName() + " + " + user.getPassword());
//
//		Map<String, String> payload = Map.of(
//				"email", user.getEmail(),
//				"playerName", user.getPlayerName(),
//				"password", user.getPassword()
//		);
//
//		System.out.println("ONESTLA2 + " + user.getEmail() + " + " + user.getPlayerName() + " + " + user.getPassword());
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
//		restTemplate.exchange(
//				baseApiUrl + "/auth/register",
//				HttpMethod.POST,
//				request,
//				Void.class
//		);
//	}
//
//	public String login(UserModel user) {
//
//		System.out.println("ONESTLA + " + user.getEmail() + " + " + user.getPlayerName() + " + " + user.getPassword());
//		Map<String, String> payload = Map.of(
//				"email", user.getEmail(),
//				"password", user.getPassword()
//		);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
//
//		ResponseEntity<Map<String, String>> response = restTemplate.exchange(
//				baseApiUrl + "/auth/login",
//				HttpMethod.POST,
//				request,
//				new ParameterizedTypeReference<>() {}
//		);
//
//		return response.getBody().get("token");
//	}
//
//	/** Secured routes **/
//	public List<UserModel> getUsers1() {
//		RestAuthHelper authHelper = new RestAuthHelper(tokenContext.getToken());
//		HttpEntity<Void> request = authHelper.buildAuthEntity();
//
//		ResponseEntity<List<UserModel>> response = restTemplate.exchange(
//				//baseApiUrl + "/user",
//				baseApiUrl + "/users",
//				HttpMethod.GET,
//				request,
//				new ParameterizedTypeReference<List<UserModel>>() {}
//		);
//
//		return response.getBody();
//	}
//
//	public UserModel getUserByUsername(String username) {
//		RestAuthHelper authHelper = new RestAuthHelper(tokenContext.getToken());
//		HttpEntity<Void> request = authHelper.buildAuthEntity();
//
//		ResponseEntity<UserModel> response = restTemplate.exchange(
//				baseApiUrl + "/user/" + username,
//				HttpMethod.GET,
//				request,
//				UserModel.class
//		);
//
//		return response.getBody();
//	}



//	public UserModel getUserProfile(String token) {
//		RestAuthHelper authHelper = new RestAuthHelper(tokenContext.getToken());
//		HttpEntity<Void> request = authHelper.buildAuthEntity();
//
//		ResponseEntity<UserModel> response = restTemplate.exchange(
//				baseApiUrl + "/me",
//				HttpMethod.GET,
//				request,
//				UserModel.class
//		);
//
//		return response.getBody();
//	}
//
//	public List<UserModel> getUsers() {
//		RestAuthHelper authHelper = new RestAuthHelper(tokenContext.getToken());
//		HttpEntity<Void> request = authHelper.buildAuthEntity();
//		ResponseEntity<List<UserModel>> response = restTemplate.exchange(
//				baseApiUrl + "/player/all",
//				HttpMethod.GET,
//				request,
//				new ParameterizedTypeReference<List<UserModel>>() {}
//		);
//
//		return response.getBody();
//	}



}
