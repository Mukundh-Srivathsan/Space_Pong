package com.example.spacepong.normalView;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacepong.GameOver;
import com.example.spacepong.R;
import com.example.spacepong.interfaces.NormalModeComms;

public class NormalMode extends AppCompatActivity {

    private static final String TAG = "NormalMode";


    private int score = 0;
    private long pressedTime;
    private boolean isEasy = true;

    private NormalView normalView;

    private TextView scoreNo;
    private TextView timer;

    private CountDownTimer starttimer;

    private MediaPlayer wallHit;
    private MediaPlayer sliderHit;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        Log.d(TAG, "onCreate : NormalMode");

        score = 0;

        FrameLayout rL = findViewById(R.id.layout);
        TextView scoreTitle = findViewById(R.id.scoreTitle);
        scoreNo = findViewById(R.id.scoreNumber);
        timer = findViewById(R.id.timer);

        Bundle bundle = getIntent().getExtras();
        isEasy = bundle.getBoolean("isEasy");

        NormalModeComms sc = new NormalModeComms() {
            @Override
            public void setScore() {
                score += 2;
                scoreNo.setText(("" + score));
            }

            @Override
            public void playSound(int action) {

                switch (action) {
                    case 1:
                        if (wallHit != null) {
                            wallHit.stop();
                            wallHit.release();
                        }
                        wallHit = null;

                        wallHit = MediaPlayer.create(NormalMode.this, R.raw.wallhit);
                        wallHit.start();

                        break;

                    case 2:
                        if (sliderHit != null) {
                            sliderHit.stop();
                            sliderHit.release();
                        }
                        sliderHit = null;

                        sliderHit = MediaPlayer.create(NormalMode.this, R.raw.sliderhit);
                        sliderHit.start();
                        break;
                }
            }

            @Override
            public void end() {
                Intent intent = new Intent(NormalMode.this, GameOver.class);
                intent.putExtra("score", score);
                intent.putExtra("isEasy", isEasy);
                intent.putExtra("isComp", false);
                startActivity(intent);
            }
        };

        normalView = new NormalView(this, sc);

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                normalView.setCurrX((int) event.getX());
                normalView.invalidate();

                return true;
            }
        };

        normalView.setOnTouchListener(onTouchListener);

        normalView.setBallX();

        startTimer();

        if (isEasy)
            normalView.easyMove();
        else
            normalView.moveHard();

        rL.addView(normalView);

    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            normalView.invalidate();
            normalView.setIsback(true);

            Intent intent = new Intent(NormalMode.this, GameOver.class);
            intent.putExtra("score", score);
            intent.putExtra("isEasy", isEasy);
            startActivity(intent);

            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
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

                normalView.setSpeedY(15F);

                double random = Math.random();

                if (random > 0.50)
                    normalView.setSpeedX(15F);
                else
                    normalView.setSpeedX(-15F);

                timer.setVisibility(View.INVISIBLE);
                stopTimer();
            }
        }.start();
    }

    void stopTimer() {
        starttimer.cancel();
    }

}