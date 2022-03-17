package com.example.geoquiz;

public class Question {
    //We store the questionID instead of a String because this int will be the ID of the string
    //kept in res/values/strings.xml
    private int questionID;
    private boolean answer;

    public Question(int q, boolean a) {
        this.questionID = q;
        this.answer = a;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}