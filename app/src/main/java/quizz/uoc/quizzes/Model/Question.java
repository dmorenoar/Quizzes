package quizz.uoc.quizzes.Model;

public class Question {

    private int questionTitle;
    private String questionMath;
    private String[] listAnswers;
    private String correctAnswer;

    public Question(int questionTitle, String questionMath, String[] listAnswes,String correctAnswer) {
        this.questionTitle = questionTitle;
        this.questionMath = questionMath;
        this.listAnswers = listAnswes;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(int questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionMath() {
        return questionMath;
    }

    public void setQuestionMath(String questionMath) {
        this.questionMath = questionMath;
    }

    public String[] getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(String[] listAnswers) {
        this.listAnswers = listAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
