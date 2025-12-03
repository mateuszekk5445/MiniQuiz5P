package com.example.miniquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textTitle;
    private TextView textQuestion;
    private TextView textScore;
    private Button buttonStart;
    private Button buttonReset;
    private Button buttonAnswerA, buttonAnswerB, buttonAnswerC;
    private LinearLayout layoutQuizArea;

    private Question[] allQuestions;
    private List<Question> selectedQuestions;
    private int currentQuestionIndex = 0;
    private int correctAnswersCount = 0;
    private final int MAX_QUESTIONS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        findViews();
        setupListeners();
        updateScoreDisplay();
        setInitialState();
    }

    private void initializeData() {
        allQuestions = new Question[]{
                new Question("Najszybszy styl to:", "Dowolny", "Glajt", "Klasyczny", "Dowolny"),
                new Question("Najbardziej utytułowany pływak to:", "Katie Ledecky", "Adam Peaty", "Michael Phelps", "Michael Phelps"),
                new Question("Basen Olimpijski ma długość:", "1 kilometr", "50 metrów", "250 decymetrów", "50 metrów"),
                new Question("Marka akcesorii pływackich to:", "Arena", "Arcteryx", "Nike", "Arena"),
                new Question("Najbardziej utytułowany kraj to:", "USA", "Chiny", "Wielka Brytania", "USA"),
                new Question("Najbardziej prestiżowe zawody pływackie w Polsce to:", "Mistrzostwa Polski Juniorów", "Główne Mistrzostwa Polski Seniorów i Młodzieżowców", "Zawody Międzynarodowe lub Wielkie Meetingi z Uczestnictwem Polskich Gwiazd", "Główne Mistrzostwa Polski Seniorów i Młodzieżowców"),
                new Question("Najwolniejszy styl to:", "Motylkowy", "Propeller", "Klasyczny", "Propeller")
        };
    }

    private void findViews() {
        textTitle = findViewById(R.id.text_title);
        textQuestion = findViewById(R.id.text_question);
        textScore = findViewById(R.id.text_score);
        buttonStart = findViewById(R.id.button_start);
        buttonReset = findViewById(R.id.button_reset);
        buttonAnswerA = findViewById(R.id.button_answer_a);
        buttonAnswerB = findViewById(R.id.button_answer_b);
        buttonAnswerC = findViewById(R.id.button_answer_c);
        layoutQuizArea = findViewById(R.id.layout_quiz_area);
    }

    private void setupListeners() {
        buttonStart.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        buttonAnswerA.setOnClickListener(this);
        buttonAnswerB.setOnClickListener(this);
        buttonAnswerC.setOnClickListener(this);
    }

    private void setInitialState() {
        buttonStart.setVisibility(View.VISIBLE);
        buttonReset.setVisibility(View.GONE);
        layoutQuizArea.setVisibility(View.GONE);
        textQuestion.setText("");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.button_start) {
            startQuiz();
        } else if (viewId == R.id.button_reset) {
            resetQuiz();
        } else if (viewId == R.id.button_answer_a || viewId == R.id.button_answer_b || viewId == R.id.button_answer_c) {
            checkAnswer(v);
        }
    }

    private void startQuiz() {
        List<Question> listAllQuestions = new ArrayList<>(Arrays.asList(allQuestions));
        Collections.shuffle(listAllQuestions);
        selectedQuestions = listAllQuestions.subList(0, MAX_QUESTIONS);

        currentQuestionIndex = 0;
        correctAnswersCount = 0;

        buttonStart.setVisibility(View.GONE);
        buttonReset.setVisibility(View.VISIBLE);
        layoutQuizArea.setVisibility(View.VISIBLE);

        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < MAX_QUESTIONS) {
            Question currentQuestion = selectedQuestions.get(currentQuestionIndex);
            String questionNumber = String.valueOf(currentQuestionIndex + 1);

            textQuestion.setText(questionNumber + ". " + currentQuestion.getQuestionText());
            buttonAnswerA.setText("A. " + currentQuestion.getAnswerA());
            buttonAnswerB.setText("B. " + currentQuestion.getAnswerB());
            buttonAnswerC.setText("C. " + currentQuestion.getAnswerC());
        } else {
            endQuiz();
        }
    }

    private void checkAnswer(View selectedButton) {
        Question currentQuestion = selectedQuestions.get(currentQuestionIndex);
        String selectedAnswer = ((Button) selectedButton).getText().toString().substring(3).trim();

        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            correctAnswersCount++;
            Toast.makeText(this, "Poprawna odpowiedź!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Błąd! Poprawna to: " + currentQuestion.getCorrectAnswer(), Toast.LENGTH_SHORT).show();
        }

        updateScoreDisplay();
        currentQuestionIndex++;
        showNextQuestion();
    }

    private void updateScoreDisplay() {
        textScore.setText("Wynik: " + correctAnswersCount);
    }

    private void endQuiz() {
        layoutQuizArea.setVisibility(View.GONE);
        String message = "Koniec quizu! Twój wynik: " + correctAnswersCount + " / " + MAX_QUESTIONS;

        new AlertDialog.Builder(this)
                .setTitle("Gratulacje!")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();

        textQuestion.setText(message);
    }

    private void resetQuiz() {
        correctAnswersCount = 0;
        currentQuestionIndex = 0;
        updateScoreDisplay();
        setInitialState();
        Toast.makeText(this, "Quiz zresetowany. Możesz zacząć ponownie.", Toast.LENGTH_SHORT).show();
    }
}