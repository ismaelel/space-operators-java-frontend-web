package fr.cci.front.model;

import java.util.List;

/**
 * Représente une question utilisée dans l'application.
 * Elle peut être affichée, éditée ou supprimée par un administrateur.
 */
public class QuestionModel {

    /**
     * Identifiant unique de la question.
     */
    private Long id;

    /**
     * Texte de la question.
     */
    private String question;

    /**
     * Liste des options de réponse.
     */
    private List<String> options;

    /**
     * Indices des bonnes réponses (ex: "1,2").
     */
    private String correctOptionIndex;

    /**
     * Constructeur par défaut.
     */
    public QuestionModel() {}

    /**
     * Constructeur avec question uniquement.
     *
     * @param question texte de la question
     */
    public QuestionModel(String question) {
        this.question = question;
    }

    /**
     * Constructeur complet.
     *
     * @param id                 identifiant
     * @param question           texte de la question
     * @param options            liste des options
     * @param correctOptionIndex index ou indices corrects
     */
    public QuestionModel(Long id, String question, List<String> options, String correctOptionIndex) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(String correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", correctOptionIndex='" + correctOptionIndex + '\'' +
                '}';
    }
}
