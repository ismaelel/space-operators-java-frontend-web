package fr.cci.front.datalayer.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


public class RestAuthHelper {

    private final String token;

    public RestAuthHelper(String token) {
        this.token = token;
    }

    /**
     * Crée des headers avec Bearer token et Content-Type application/json.
     * Utile pour POST, PUT.
     */
    public HttpHeaders buildJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Crée des headers avec Bearer token uniquement.
     * Utile pour GET, DELETE (pas toujours besoin de Content-Type).
     */
    public HttpHeaders buildAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }

    /**
     * Crée un HttpEntity avec un corps JSON et les headers auth.
     */
    public <T> HttpEntity<T> buildJsonEntity(T body) {
        return new HttpEntity<>(body, buildJsonHeaders());
    }

    /**
     * Crée un HttpEntity vide avec headers auth.
     */
    public HttpEntity<Void> buildAuthEntity() {
        return new HttpEntity<>(buildAuthHeaders());
    }
}
