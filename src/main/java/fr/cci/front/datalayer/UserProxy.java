package fr.cci.front.datalayer;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import fr.cci.front.configuration.TokenContext;
import fr.cci.front.model.UserModel;

@Repository
public class UserProxy {

	private TokenContext tokenContext;
	private final String baseApiUrl = "http://localhost:8080/api";
	private RestTemplate restTemplate = new RestTemplate();

	public UserProxy(final TokenContext tokenContext) {
		this.tokenContext = tokenContext;
	}

	private HttpHeaders createTokenHeader(final String token) {
		String authHeader = "Bearer " + token;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authHeader);
		return headers;
	}

	/** PermitAll routes **/
	public void add(UserModel user) {
		HttpEntity<UserModel> request = new HttpEntity<UserModel>(user);
		restTemplate.exchange(
				baseApiUrl + "/user",
				HttpMethod.POST,
				request,
				Void.class);
	}

	public String login(UserModel user) {
		// Warning : change UserModel object by a
		// specific DTO to respect best practices
		HttpEntity<UserModel> request = new HttpEntity<UserModel>(user);
		ResponseEntity<String> response = restTemplate.exchange(
				baseApiUrl + "/login",
				HttpMethod.POST,
				request,
				String.class);
		return response.getBody();
	}

	/** Secured routes **/
	public List<UserModel> getUsers() {

		HttpEntity request = new HttpEntity<>(
				createTokenHeader(
						tokenContext.getToken()));

		ResponseEntity<List<UserModel>> response =
				restTemplate.exchange(
						baseApiUrl + "/user",
						HttpMethod.GET,
						request,
						new ParameterizedTypeReference<List<UserModel>>() {});

		return response.getBody();
	}

	public UserModel getUserByUsername(String username) {

		HttpEntity request = new HttpEntity<>(
				createTokenHeader(
						tokenContext.getToken()));

		ResponseEntity<UserModel> response = restTemplate.exchange(
				baseApiUrl + "/user/" + username,
				HttpMethod.GET,
				request,
				UserModel.class
		);

		return response.getBody();
	}

	public UserModel getUserInformation() {

		HttpEntity request = new HttpEntity<>(
				createTokenHeader(
						tokenContext.getToken()));

		ResponseEntity<UserModel> response = restTemplate.exchange(
				baseApiUrl + "/me",
				HttpMethod.GET,
				request,
				UserModel.class
		);

		return response.getBody();
	}

}