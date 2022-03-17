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
                //Do something in response to click here
                Toast toast = Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Takes a View as a parameter. A VIEW IS A WIDGET AND VISA VERSA!!!
            public void onClick(View view) {
                //Do something in response to click here
                Toast.makeText(
                        MainActivity.this,
                        R.string.incorrect_toast,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //Get the ID of the current question
        int questionTextResID = questionBank[currentIndex].getQuestionID();
        //Set the text view to show that question
        questionTextView.setText(questionTextResID);
    }
}