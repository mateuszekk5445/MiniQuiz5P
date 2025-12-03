package com.example.miniquiz;

public class Question {
    private String questionText;
    private String answerA;
    private String answerB;
    private String answerC;
    private String correctAnswer;

    public Question(String questionText, String answerA, String answerB, String answerC, String correctAnswer) {
        this.questionText = questionText;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}