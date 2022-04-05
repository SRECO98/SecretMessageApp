package com.example.secretmessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton buttonStart = findViewById(R.id.buttonStart);
        AppCompatButton buttonReadMe = findViewById(R.id.buttonReadMe);
        AppCompatButton buttonContact = findViewById(R.id.buttonContact);
        AppCompatButton buttonExit = findViewById(R.id.buttonExit);
        AppCompatButton buttonInstructions = findViewById(R.id.buttonInstructions);

        //takodje sluzi za pravljenej trans animacija izmedju aktivnosti
        buttonInstructions.setOnClickListener(view -> { bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(new Intent(this, InstructionsActivity.class), bundle); });
        buttonStart.setOnClickListener(view -> { bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(new Intent(getApplicationContext(), TranslaterActivity.class), bundle); }); // otvara novu aktivnost (lambda izraz)
        buttonReadMe.setOnClickListener(view -> { bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(new Intent(MainActivity.this, ReadMeActivity.class), bundle); });
        buttonContact.setOnClickListener(view -> { bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(new Intent(this, ContactActivity.class), bundle); });
        buttonExit.setOnClickListener(view -> {finish(); System.exit(0);});

    }
}