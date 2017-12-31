package mif50.com.onlinequizapp.model;

/**
 * this model class deal with firebase json object Questions
 */

public class Question {
    private String AnswerA;
    private String AnswerB;
    private String AnswerC;
    private String AnswerD;
    private String CategoryId;
    private String CorrectAnswer;
    private String IsImageQuestion;
    private String QuestionText;

    public Question() {}

    public Question(String answerA, String answerB, String answerC, String answerD, String categoryId, String correctAnswer, String isImageQuestion, String questionText) {
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        CategoryId = categoryId;
        CorrectAnswer = correctAnswer;
        IsImageQuestion = isImageQuestion;
        QuestionText = questionText;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        IsImageQuestion = isImageQuestion;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public void setQuestionText(String questionText) {
        QuestionText = questionText;
    }
}
