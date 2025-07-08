package fr.cci.front.model;

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
     * Constructeur par défaut.
     */
    public QuestionModel() {}

    /**
     * Constructeur avec texte de question.
     *
     * @param question le contenu de la question
     */
    public QuestionModel(String question) {
        this.question = question;
    }

    /**
     * Constructeur complet avec ID.
     *
     * @param id       identifiant de la question
     * @param question le contenu de la question
     */
    public QuestionModel(Long id, String question) {
        this.id = id;
        this.question = question;
    }

    /**
     * Retourne l'identifiant de la question.
     *
     * @return id de la question
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la question.
     *
     * @param id identifiant à définir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le texte de la question.
     *
     * @return contenu de la question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Définit le texte de la question.
     *
     * @param question contenu à définir
     */
    public void setQuestion(String question) {
        this.question = question;
    }
}
