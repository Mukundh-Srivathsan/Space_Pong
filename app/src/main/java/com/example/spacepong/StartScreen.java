package com.example.spacepong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacepong.computerView.VsComputerMode;
import com.example.spacepong.normalView.NormalMode;

public class StartScreen extends AppCompatActivity {

    private Button easy;
    private Button hard;
    private Button compHard;
    private Button compEasy;

    private TextView title;
    private TextView normalMode;
    private TextView compMode;

    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        title = findViewById(R.id.titleView);
        normalMode = findViewById(R.id.normalTextView);
        compMode = findViewById(R.id.vsComputerTextView);

        easy = findViewById(R.id.easyBtn);
        hard = findViewById(R.id.hardBtn);
        compHard = findViewById(R.id.compHardBtn);
        compEasy = findViewById(R.id.compEasyBtn);

        easy.setText("easy");
        hard.setText("hard");
        compHard.setText("hard");
        compEasy.setText("easy");

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, NormalMode.class);
                intent.putExtra("isEasy", true);
                startActivity(intent);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, NormalMode.class);
                intent.putExtra("isEasy", false);
                startActivity(intent);
            }
        });

        compEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, VsComputerMode.class);
                intent.putExtra("isEasy", true);
                startActivity(intent);
            }
        });

        compHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, VsComputerMode.class);
                intent.putExtra("isEasy", false);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}