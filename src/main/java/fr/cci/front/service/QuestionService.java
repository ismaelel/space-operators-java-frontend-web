package fr.cci.front.service;

import fr.cci.front.datalayer.QuestionProxy;
import fr.cci.front.model.QuestionModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsable de la gestion des questions.
 * Sert de couche intermédiaire entre les contrôleurs et le proxy d'accès aux données de questions.
 */
@Service
public class QuestionService {

    private final QuestionProxy questionProxy;

    /**
     * Constructeur avec injection du proxy question.
     *
     * @param questionProxy le proxy pour accéder aux questions
     */
    public QuestionService(QuestionProxy questionProxy) {
        this.questionProxy = questionProxy;
    }

    /**
     * Ajoute une nouvelle question.
     *
     * @param question la question à ajouter
     */
    public void add(QuestionModel question) {
        questionProxy.add(question);
    }

    /**
     * Récupère la liste de toutes les questions.
     *
     * @return la liste des questions
     */
    public List<QuestionModel> get() {
        return questionProxy.getQuestions();
    }

    /**
     * Supprime une question par son identifiant.
     *
     * @param id l'identifiant de la question à supprimer
     */
    public void delete(Long id) {
        questionProxy.delete(id);
    }

    /**
     * Récupère une question à partir de son identifiant.
     *
     * @param id l'identifiant de la question
     * @return la question correspondante
     */
    public QuestionModel getById(Long id) {
        return questionProxy.getById(id);
    }

    /**
     * Met à jour une question existante.
     *
     * @param question la question mise à jour
     */
    public void update(QuestionModel question) {
        questionProxy.update(question);
    }
}
