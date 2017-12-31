package mif50.com.onlinequizapp.model;

/**
 * this model class deal with firebase object json QuestionScore
 */

public class QuestionScore {

    private String QuestionScore;
    private String User;
    private String Score;
    private String categoryId;
    private String categoryName;
    public QuestionScore() {}

    public QuestionScore(String questionScore, String user, String score,String categoryId,String categoryName) {
        QuestionScore = questionScore;
        User = user;
        Score = score;
        this.categoryId=categoryId;
        this.categoryName=categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getQuestionScore() {
        return QuestionScore;
    }

    public void setQuestionScore(String questionScore) {
        QuestionScore = questionScore;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
