package fr.cci.front.datalayer;

import fr.cci.front.configuration.TokenContext;
import fr.cci.front.datalayer.util.RestAuthHelper;
import fr.cci.front.model.QuestionModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Proxy chargé de communiquer avec l’API REST pour la gestion des questions.
 * Il encapsule toutes les requêtes HTTP concernant les entités {@link QuestionModel}.
 */
@Repository
public class QuestionProxy {

    //private final String baseApiUrl = "http://26.195.1.69:8080/api";
    private final String baseApiUrl = "http://localhost:8080/api";

    private final RestTemplate restTemplate = new RestTemplate();
    private final TokenContext tokenContext;

    /**
     * Constructeur du proxy, avec injection du contexte contenant le token JWT.
     *
     * @param tokenContext contexte du token JWT pour les requêtes sécurisées
     */
    public QuestionProxy(final TokenContext tokenContext) {
        this.tokenContext = tokenContext;
    }

    /**
     * Crée les en-têtes HTTP avec le token JWT pour les appels sécurisés.
     *
     * @param token le token JWT
     * @return les en-têtes HTTP avec le token
     */
    private HttpHeaders createTokenHeader(final String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Envoie une requête POST pour ajouter une nouvelle question.
     *
     * @param question la question à ajouter
     */
    public void add(QuestionModel question) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<QuestionModel> request = helper.buildJsonEntity(question);

        System.out.println("Question envoyée : " + question);

        restTemplate.exchange(
                baseApiUrl + "/question",
                HttpMethod.POST,
                request,
                Void.class
        );
    }

    /**
     * Récupère toutes les questions depuis l'API.
     *
     * @return liste des questions
     */
    public List<QuestionModel> getQuestions() {
        HttpEntity<Void> request = new HttpEntity<>(createTokenHeader(tokenContext.getToken()));

        ResponseEntity<List<QuestionModel>> response = restTemplate.exchange(
                baseApiUrl + "/question/all",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

    /**
     * Supprime une question à partir de son identifiant.
     *
     * @param id identifiant de la question à supprimer
     */
    public void delete(Long id) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<Void> request = helper.buildAuthEntity();

        restTemplate.exchange(
                baseApiUrl + "/question/" + id,
                HttpMethod.DELETE,
                request,
                Void.class
        );
    }

    /**
     * Récupère une question à partir de son identifiant.
     *
     * @param id identifiant de la question
     * @return la question correspondante
     */
    public QuestionModel getById(Long id) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<Void> request = helper.buildAuthEntity();

        ResponseEntity<QuestionModel> response = restTemplate.exchange(
                baseApiUrl + "/question/" + id,
                HttpMethod.GET,
                request,
                QuestionModel.class
        );

        return response.getBody();
    }

    /**
     * Met à jour une question existante.
     *
     * @param question la question mise à jour
     */
    public void update(QuestionModel question) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<QuestionModel> request = helper.buildJsonEntity(question);

        restTemplate.exchange(
                baseApiUrl + "/question/" + question.getId(),
                HttpMethod.PUT,
                request,
                Void.class
        );
    }
}
