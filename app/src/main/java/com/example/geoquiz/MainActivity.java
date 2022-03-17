package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private Question[] questionBank = new Question[] {
            new Question(R.string.question_cor, true),
            new Question(R.string.question_endor, true),
            new Question(R.string.question_high_ground, false),
            new Question(R.string.question_hall, true),
            new Question(R.string.question_palp, false),
            new Question(R.string.question_tatooine, true)
    };
    private int currentIndex = 0;

    //This runs when the activity is created. When it is created, it needs a UI to manage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This gives the activity a UI to manage, activity_main.xml
        //This inflates the layout and puts it on the screen. On inflation,
        //each widget get instantiated
        setContentView(R.layout.activity_main);

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
                //Increment current index
                //Mod by questionBank.length to prevent out of bounds index
                currentIndex = (currentIndex + 1) % questionBank.length;
                //Update the question
                updateQuestion();            }
        });

        //Set the first question
        this.updateQuestion();
    }

    //This updates the view to show the next question
    private void updateQuestion() {
        //Get the ID of the current question
        int questionTextResID = questionBank[currentIndex].getQuestionID();
        //Set the text view to show that question
        questionTextView.setText(questionTextResID);
    }

    //This compare what the user answered to the correct answer to see if the user is right or not
    private void checkAnswer(boolean userAns) {
        //Get the correct answer
        boolean correctAns = questionBank[currentIndex].getAnswer();
        //Set the message to display based on if the user is correct or not
        int messageResID = ((userAns == correctAns) ? R.string.correct_toast : R.string.incorrect_toast);
        //Make a Toast appear
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
    }
}