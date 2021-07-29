package com.example.spacepong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacepong.computerView.VsComputerMode;
import com.example.spacepong.normalView.NormalMode;
import com.example.spacepong.normalView.NormalView;

public class GameOver extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        RelativeLayout rl = findViewById(R.id.rl);

        Button retry = findViewById(R.id.retryBtn);
        Button menu = findViewById(R.id.menuBtn);
        TextView recordDisp = findViewById(R.id.highscoreView);

        NormalView normalView = new NormalView(this);
        rl.addView(normalView);

        int score = getIntent().getIntExtra("score", 0);

        SharedPreferences highscore = getSharedPreferences("Highscore", MODE_PRIVATE);
        int record = 0;
        boolean isEasy = getIntent().getBooleanExtra("isEasy", false);
        boolean isComp = getIntent().getBooleanExtra("isComp", false);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = null;


        if (isComp) {
            if (isEasy) {


                    mediaPlayer = MediaPlayer.create(GameOver.this, R.raw.newhighscore);
                    mediaPlayer.start();
                    recordDisp.setText("GAME OVER");

            } else {
                record = highscore.getInt("recordHC", 0);

                if (score > record) {
                    SharedPreferences.Editor editor = highscore.edit();
                    editor.putInt("recordHC", score);
                    editor.commit();

                    mediaPlayer = MediaPlayer.create(GameOver.this, R.raw.newhighscore);
                    mediaPlayer.start();
                } else {
                    mediaPlayer = MediaPlayer.create(GameOver.this, R.raw.gameover);
                    mediaPlayer.start();
                }

                record = highscore.getInt("recordHC", 0);
                recordDisp.setText("your record is: " + record);
            }
        }else {
            if (isEasy) {

                record = highscore.getInt("recordE", 0);

                if (score > record) {
                    SharedPreferences.Editor editor = highscore.edit();
                    editor.putInt("recordE", score);
                    editor.commit();
                    mediaPlayer = MediaPlayer.create(GameOver.this, R.raw.newhighscore);
                    mediaPlayer.start();
                } else {
                    mediaPlayer = MediaPlayer.create(GameOver.this, R.raw.gameover);
                    mediaPlayer.start();
                }

                record = highscore.getInt("recordE", 0);
                recordDisp.setText("your record is: " + record);
            } else {
                record = highscore.getInt("recordH", 0);

                if (score > record) {
                    SharedPreferences.Editor editor = highscore.edit();
                    editor.putInt("recordH", score);
                    editor.commit();

                    mediaPlayer = MediaPlayer.create(GameOver.this, R.raw.newhighscore);
                    mediaPlayer.start();
                } else {
                    mediaPlayer = MediaPlayer.create(GameOver.this, R.raw.gameover);
                    mediaPlayer.start();
                }

                record = highscore.getInt("recordH", 0);
                recordDisp.setText("your record is: " + record);
            }
        }



        if(isComp) {
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameOver.this, VsComputerMode.class);
                    intent.putExtra("isEasy", isEasy);
                    startActivity(intent);
                }
            });

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameOver.this, StartScreen.class);
                    startActivity(intent);
                }
            });
        }else{
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameOver.this, NormalMode.class);
                    intent.putExtra("isEasy", isEasy);
                    startActivity(intent);
                }
            });

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameOver.this, StartScreen.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(GameOver.this, StartScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}