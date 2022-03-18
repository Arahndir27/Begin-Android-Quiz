package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//My class extends AppCompatActivity, which is an Android class that
//lets my code run on older versions of Android
public class MainActivity extends AppCompatActivity {
    //This is a tag for logging
    private final String TAG = "MainActivity";
    //Other members
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;

    //This is a getter for the quiz ViewModel
    private QuizViewModel getQuizModel() {
        return ViewModelProviders.of(this).get(QuizViewModel.class);
    }

    //This runs when the activity is created. When it is created, it needs a UI to manage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //You have to call the super method because it does things that need to happen
        super.onCreate(savedInstanceState);

        //Log a message to show that this method was called
        Log.d(TAG, "onCreate(Bundle) called");

        //This gives the activity a UI to manage, activity_main.xml
        //This inflates the layout and puts it on the screen. On inflation,
        //each widget get instantiated
        setContentView(R.layout.activity_main);

        //ViewModelProviders gets me a ViewModelProvider
        //ViewModelProvider gets me a ViewModel
        //The ViewModel is scoped to the life of this activity. It will stay in RAM across
        //configuration changes and only leaves when the activity is destroyed.
        ViewModelProvider provider = ViewModelProviders.of(this);
        QuizViewModel quizViewModel = provider.get(QuizViewModel.class);
        Log.d(TAG, "Got a QuizViewModel: " + quizViewModel);

        //Instantiate the buttons
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        //Add event listeners to the buttons
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Takes a View as a parameter. A VIEW IS A WIDGET AND VISA VERSA!!!
            public void onClick(View view) {
                //Check the user's answer and display a toast accordingly
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Takes a View as a parameter. A VIEW IS A WIDGET AND VISA VERSA!!!
            public void onClick(View view) {
                //Check the user's answer and display a toast accordingly
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Move to the next question via the ViewModel
                getQuizModel().moveToNextQuestion();
                //Update the question
                updateQuestion();            }
        });

        //Set the first question
        this.updateQuestion();
    }

    //This updates the view to show the next question
    private void updateQuestion() {
        //Get the ID of the current question
        int questionTextResID = getQuizModel().getCurrQuestionText();
        //Set the text view to show that question
        questionTextView.setText(questionTextResID);
    }

    //This compare what the user answered to the correct answer to see if the user is right or not
    private void checkAnswer(boolean userAns) {
        //Get the correct answer
        boolean correctAns = getQuizModel().getCurrQuestionAns();
        //Set the message to display based on if the user is correct or not
        int messageResID = ((userAns == correctAns) ? R.string.correct_toast : R.string.incorrect_toast);
        //Make a Toast appear
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
    }

    //Override Activity Lifecycle Functions to display log messages when they are called
    @Override
    protected void onStart() {
        //You have to call the super method because it does things that need to happen
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        //You have to call the super method because it does things that need to happen
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        //You have to call the super method because it does things that need to happen
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        //You have to call the super method because it does things that need to happen
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        //You have to call the super method because it does things that need to happen
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}