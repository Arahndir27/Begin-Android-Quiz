package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CheatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This gives the activity a UI to manage, cheat_main.xml
        //This inflates the layout and puts it on the screen. On inflation,
        //each widget get instantiated
        setContentView(R.layout.activity_cheat);
    }
}