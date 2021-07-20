package com.example.spacepong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spacepong.views.CustomView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    CustomView customView;

    FrameLayout rL;
    TextView scoreTitle;
    TextView scoreNo;
    TextView timer;

    CountDownTimer starttimer;

    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate : MainActivity");

        score = 0;

        rL = (FrameLayout) findViewById(R.id.layout);
        scoreTitle = (TextView) findViewById(R.id.scoreTitle);
        scoreNo = (TextView) findViewById(R.id.scoreNumber);
        timer = (TextView) findViewById(R.id.timer);

        scoreTitle.setVisibility(View.VISIBLE);
        //scoreTitle.setText("SCORE : ");
        scoreNo.setVisibility(View.VISIBLE);
        scoreNo.setText("00");
        timer.setVisibility(View.VISIBLE);

        customView = new CustomView(this);

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                customView.setCurrX((int) event.getX());
                customView.invalidate();

                return true;
            }
        };
        customView.setBallX();

        startTimer();

        customView.move();
        customView.setOnTouchListener(onTouchListener);


        rL.addView(customView);
    }

    public void setScore() {
        score += 2;
        scoreNo.setText(("" + score));
    }

    void startTimer() {
        starttimer = new CountDownTimer(3000, 1000) {
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