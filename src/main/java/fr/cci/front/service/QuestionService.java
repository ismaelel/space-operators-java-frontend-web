package fr.cci.front.service;

import fr.cci.front.datalayer.QuestionProxy;
import fr.cci.front.datalayer.UserProxy;
import fr.cci.front.model.QuestionModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private QuestionProxy questionProxy;

    public QuestionService(QuestionProxy questionProxy) {
        this.questionProxy = questionProxy;
    }

    //public List<UserModel> get() {
      //  return questionProxy.getQuestions();
    //}

    public void add(QuestionModel question) {
        questionProxy.add(question);
    }

    public List<QuestionModel> get() {
        return questionProxy.getQuestions();
    }

    public void delete(Long id) {
        questionProxy.delete(id);
    }
    public QuestionModel getById(Long id) {
        return questionProxy.getById(id);
    }

    public void update(QuestionModel question) {
        questionProxy.update(question);
    }

}
