package fr.cci.front.datalayer;

import fr.cci.front.model.QuestionModel;
import fr.cci.front.model.UserModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class QuestionProxy {
    private final String baseApiUrl = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();
    public void add(QuestionModel question) {

        HttpEntity<QuestionModel> request = new HttpEntity<QuestionModel>(question);
        restTemplate.exchange(
                baseApiUrl + "/question",
                HttpMethod.POST,
                request,
                Void.class);
    }

    public List<QuestionModel> getQuestions() {

        ResponseEntity<List<QuestionModel>> response =
                restTemplate.exchange(
                        baseApiUrl + "/question/all",
                        HttpMethod.GET,
                         null,
                        new ParameterizedTypeReference<List<QuestionModel>>() {});

        return response.getBody();
    }

    public void delete(Long id) {
        restTemplate.exchange(
                baseApiUrl + "/question/" + id, //?
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

    public QuestionModel getById(Long id) {
        return restTemplate.getForObject(baseApiUrl + "/question/" + id, QuestionModel.class);
    }

    public void update(QuestionModel question) {
        HttpEntity<QuestionModel> request = new HttpEntity<>(question);
        restTemplate.exchange(
                baseApiUrl + "/question/" + question.getId(),
                HttpMethod.PUT,
                request,
                Void.class
        );
    }


}
