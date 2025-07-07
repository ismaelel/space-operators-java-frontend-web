package fr.cci.front.datalayer;

import fr.cci.front.configuration.TokenContext;
import fr.cci.front.datalayer.util.RestAuthHelper;
import fr.cci.front.model.QuestionModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Repository
public class QuestionProxy {
    private final String baseApiUrl = "http://26.195.1.69:8080/api";
    private RestTemplate restTemplate = new RestTemplate();
    private final TokenContext tokenContext;

    public QuestionProxy(final TokenContext tokenContext) {
        this.tokenContext = tokenContext;
    }

    private HttpHeaders createTokenHeader(final String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    public void add(QuestionModel question) {

        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<QuestionModel> request = helper.buildJsonEntity(question);

        System.out.println("Question envoyée : " + question);

        restTemplate.exchange(
                baseApiUrl + "/question",
                HttpMethod.POST,
                request,
                Void.class);
    }

    public List<QuestionModel> getQuestions() {
        HttpEntity<Void> request = new HttpEntity<>(
                createTokenHeader(tokenContext.getToken())
        );

        ResponseEntity<List<QuestionModel>> response =
                restTemplate.exchange(
                        baseApiUrl + "/question/all",
                        HttpMethod.GET,
                         request,
                        new ParameterizedTypeReference<List<QuestionModel>>() {});

        return response.getBody();
    }

    public void delete(Long id) {
        RestAuthHelper helper = new RestAuthHelper(tokenContext.getToken());
        HttpEntity<Void> request = helper.buildAuthEntity();

        restTemplate.exchange(baseApiUrl + "/question/" + id,
                HttpMethod.DELETE,
                request,
                Void.class);
    }

//    public QuestionModel getById(Long id) {
//
//        return restTemplate.getForObject(baseApiUrl + "/question/" + id, QuestionModel.class);
//    }

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
