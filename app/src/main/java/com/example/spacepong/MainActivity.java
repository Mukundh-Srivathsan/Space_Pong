package com.example.spacepong;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacepong.views.CustomView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MediaPlayer mediaPlayer;
    private int score = 0;
    private boolean isEasy = true;

    private CustomView customView;

    private TextView scoreNo;
    private TextView timer;

    private CountDownTimer starttimer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate : MainActivity");

        score = 0;

        FrameLayout rL = findViewById(R.id.layout);
        TextView scoreTitle = findViewById(R.id.scoreTitle);
        scoreNo = findViewById(R.id.scoreNumber);
        timer = findViewById(R.id.timer);

        scoreTitle.setVisibility(View.VISIBLE);
        //scoreTitle.setText("SCORE : ");
        scoreNo.setVisibility(View.VISIBLE);
        scoreNo.setText(("00"));
        timer.setVisibility(View.VISIBLE);

        ScoreChange sc = new ScoreChange() {
            @Override
            public void setScore() {
                score += 2;
                scoreNo.setText(("" + score));
            }

            @Override
            public void playSound(int action) {
                if(mediaPlayer!=null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = null;

                switch (action) {
                    case 0:
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.gameover);
                        mediaPlayer.start();
                        break;

                    case 1:
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.wallhit);
                        mediaPlayer.start();
                        break;

                    case 2:
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sliderhit);
                        mediaPlayer.start();
                        break;
                }
            }
        };

        customView = new CustomView(this, sc);

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                customView.setCurrX((int) event.getX());
                customView.invalidate();

                return true;
            }
        };

        customView.setOnTouchListener(onTouchListener);

        customView.setBallX();

        Intent intent = getIntent();

        Bundle bundle = getIntent().getExtras();
        isEasy = bundle.getBoolean("isEasy");

        startTimer();

        if(isEasy)
            customView.easyMove();
        else
            customView.moveHard();

        rL.addView(customView);
    }


    void startTimer() {
        starttimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;
                timer.setText(("" + seconds));
            }

            @Override
            public void onFinish() {

                customView.setSpeedY(20F);

                double random = Math.random();

                if (random > 0.50)
                    customView.setSpeedX(20F);
                else
                    customView.setSpeedX(-20F);

                timer.setVisibility(View.INVISIBLE);
                stopTimer();
            }
        }.start();
    }

    void stopTimer() {
        starttimer.cancel();
    }

}