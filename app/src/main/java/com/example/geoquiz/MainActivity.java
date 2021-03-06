package com.example.geoquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//My class extends AppCompatActivity, which is an Android class that
//lets my code run on older versions of Android
public class MainActivity extends AppCompatActivity {
    //This is a tag for logging
    private final String TAG = "MainActivity";
    //This is the key for our savedInstanceState Bundle
    private final String KEY_INDEX = "index";
    //This is the key for figuring out if the user cheated (communicating with CheatActivity)
    private final int REQUEST_CODE_CHEAT = 0;
    //Other members
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button cheatButton;
    private TextView questionTextView;

    //This is a getter for the quiz ViewModel
    private QuizViewModel getQuizViewModel() {
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

        //Initialize the current index to 0
        int currentIndex = 0;
        //Check if the bundle is not null and contains the key KEY_INDEX
        //If so then we need to update currentIndex to be the currentIndex that
        //was saved from when saveInstanceState() was called
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_INDEX)) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
        }
        quizViewModel.setCurrentIndex(currentIndex);

        //Instantiate the buttons
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        cheatButton = findViewById(R.id.cheat_button);
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
                getQuizViewModel().moveToNextQuestion();
                //Update the question
                updateQuestion();            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intents are objects for 2 way communication. Main activity says that is intends to
                //make a CheatActivity and asks the Android OS to do so. The Android OS has an
                //ActivityManager, which manages activities. The intent specifies which class the
                //ActivityManager should start and where it can be found so that info
                //can then be passed through startActivity()
                boolean answerIsTrue = getQuizViewModel().getCurrQuestionAns();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);

                //This actually sends the request to the OS ActivityManager to create the activity
                //specified in the intent. It also passes extras in the intent to that activity.
                //startActivity(intent);

                //This function does the same stuff as startActivity, but also takes a request code,
                //which is sent to the child and back to the parent.
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        //Set the first question
        this.updateQuestion();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Make sure the codes are OK
        if (resultCode != Activity.RESULT_OK) {
            //This is unexpected, so be done
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            boolean isCheater = false;
            if (data != null && data.hasExtra(CheatActivity.EXTRA_ANSWER_IS_SHOWN)) {
                //The user cheated! Make them feel sad
                isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_SHOWN, false);
            }
            getQuizViewModel().setCheater(isCheater);
        }
    }

    //This updates the view to show the next question
    private void updateQuestion() {
        //Get the ID of the current question
        int questionTextResID = getQuizViewModel().getCurrQuestionText();
        //Set the text view to show that question
        questionTextView.setText(questionTextResID);
    }

    //This compare what the user answered to the correct answer to see if the user is right or not
    private void checkAnswer(boolean userAns) {
        //Get the correct answer
        boolean correctAns = getQuizViewModel().getCurrQuestionAns();
        //Set the message to display based on if the user is correct or not or if they cheated
        int messageResID;
        if (getQuizViewModel().isCheater()) {
            //Display a toast to make them sad
            messageResID = R.string.judgement_toast;
        }
        else {
            //Display toast on whether they were right or wrong
            messageResID = ((userAns == correctAns) ? R.string.correct_toast : R.string.incorrect_toast);
        }
        //Make a Toast appear
        Toast.makeText(this, messageResID, Toast.LENGTH_LONG).show();
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

    //This function is called before onStop. It saves the state of things outside of memory temporarily
    //A Bundle is just a map.
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //Call the super function because you have to
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, getQuizViewModel().getCurrentIndex());
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