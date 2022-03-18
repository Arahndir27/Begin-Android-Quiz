package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    //This is a key for the extra that will be passed via an intent to this activity. I keep it here
    //because an activity could be started from several different places, so I should define keys
    //for extras on the activities that will retrieve and use them.
    //Using my package name as a qualifier for the key prevents collisions with extras from other apps.
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_IS_SHOWN = "com.example.geoquiz.answer_shown";
    private boolean answerIsTrue = false;
    private TextView answerTextView;
    private Button showAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This gives the activity a UI to manage, cheat_main.xml
        //This inflates the layout and puts it on the screen. On inflation,
        //each widget get instantiated
        setContentView(R.layout.activity_cheat);

        //This searches the intent for the given key (param 1) because intents are also like maps.
        //If it finds it, answerIsTrue will be set to the value there. Otherwise, the defaultValue is false (param 2).
        //getIntent() always returns the Intent used to create this activity
        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        //Get the TextView and Button
        //Remember that a view is a widget
        answerTextView = findViewById(R.id.answer_text_view);
        showAnswerButton = findViewById(R.id.show_answer_button);

        //Give showAnswerButton an OnClick listener
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Figure out what to display
                int answerText = (answerIsTrue ? R.string.true_button : R.string.false_button);
                //Set that text to appear in the text view
                answerTextView.setText(answerText);
                setAnswerShownResult(true);
            }
        });
    }

    //This function allows me to create Intents configured with exactly the extras that CheatActivity expects.
    //Since this function is static, I can access it without having an instance of CheatActivity
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    //This function makes an intent with data in it that shows that the user saw the correct answer
    //It then sets the Result which is sent back to its parent activity to OK and also includes
    //the Intent to be sent back too. This way MainActivity can get information back from here.
    private void setAnswerShownResult(boolean answerIsShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, answerIsShown);
        setResult(Activity.RESULT_OK, data);
    }
}