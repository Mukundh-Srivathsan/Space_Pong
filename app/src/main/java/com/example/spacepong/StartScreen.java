package com.example.spacepong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartScreen extends AppCompatActivity {

    private Button easy;
    private Button hard;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        title = findViewById(R.id.titleView);
        easy = findViewById(R.id.easyBtn);
        hard = findViewById(R.id.hardBtn);

        Intent intent = new Intent(StartScreen.this, MainActivity.class);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("isEasy", true);
                startActivity(intent);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("isEasy", false);
                startActivity(intent);
            }
        });

    }
}