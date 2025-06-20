package fr.cci.front.model;

public class QuestionModel {
    private Long id;
    private String question;

    public QuestionModel() {}

    public QuestionModel(String question) {
        super();
        this.question = question;
    }

    public QuestionModel(Long id, String question) {
        this.id = id;
        this.question = question;
    }

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


}
