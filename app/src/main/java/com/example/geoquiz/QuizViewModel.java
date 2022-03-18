package com.example.geoquiz;

import android.util.Log;
import androidx.lifecycle.ViewModel;

public class QuizViewModel extends ViewModel {
    private final String TAG = "QuizViewModel";
    //I moved these over from MainActivity so they will persist on
    //configuration change like a screen rotation
    private Question[] questionBank = new Question[] {
            new Question(R.string.question_cor, true),
            new Question(R.string.question_endor, true),
            new Question(R.string.question_high_ground, false),
            new Question(R.string.question_hall, true),
            new Question(R.string.question_palp, false),
            new Question(R.string.question_tatooine, true)
    };
    public int currentIndex = 0;

    public QuizViewModel() {
        super();
    }

    public void setCurrentIndex(int val) {
        currentIndex = val;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean getCurrQuestionAns() {
        return questionBank[currentIndex].getAnswer();
    }

    public int getCurrQuestionText() {
        return questionBank[currentIndex].getQuestionID();
    }

    public void moveToNextQuestion() {
        //Mod by questionBank.length to prevent out of bounds index
        currentIndex = (currentIndex + 1) % questionBank.length;
    }
}