package com.example.spacepong.computerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacepong.GameOver;
import com.example.spacepong.R;
import com.example.spacepong.interfaces.ComputerModeComms;

public class VsComputerMode extends AppCompatActivity {

    private static final String TAG = "VsComputerMode";

    private int usrScore = 0;
    private int compScore = 0;
    private long pressedTime;
    private boolean isEasy = true;

    private VsComputerView vsComputerView;

    private TextView usrScoreView;
    private TextView compScoreView;
    private TextView timer;

    private CountDownTimer starttimer;

    private MediaPlayer wallHit;
    private MediaPlayer sliderHit;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_computer);
        Log.d(TAG, "onCreate : VsComputerMode");

        usrScore = 0;
        compScore = 0;
        RelativeLayout rL = findViewById(R.id.layout);
        TextView scoreTitle = findViewById(R.id.scoreTitle);
        usrScoreView = findViewById(R.id.userScoreNumber);
        compScoreView = findViewById(R.id.computerScoreNumber);
        timer = findViewById(R.id.timer);

        Bundle bundle = getIntent().getExtras();
        isEasy = bundle.getBoolean("isEasy");

        ComputerModeComms sc = new ComputerModeComms() {
            @Override
            public void setUsrScore() {
                usrScore += 1;
                usrScoreView.setText(("" + usrScore));
            }

            public void setCompScore() {
                compScore += 1;
                compScoreView.setText(("" + compScore));
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

                        wallHit = MediaPlayer.create(VsComputerMode.this, R.raw.wallhit);
                        wallHit.start();

                        break;

                    case 2:
                        if (sliderHit != null) {
                            sliderHit.stop();
                            sliderHit.release();
                        }
                        sliderHit = null;

                        sliderHit = MediaPlayer.create(VsComputerMode.this, R.raw.sliderhit);
                        sliderHit.start();
                        break;
                }
            }

            @Override
            public void end() {
                Intent intent = new Intent(VsComputerMode.this, GameOver.class);
                intent.putExtra("score", usrScore);
                intent.putExtra("isEasy", isEasy);
                intent.putExtra("isComp", true);
                startActivity(intent);
            }
        };

        vsComputerView = new VsComputerView(this, sc);

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                vsComputerView.setCurrX((int) event.getX());
                vsComputerView.invalidate();

                return true;
            }
        };

        vsComputerView.setOnTouchListener(onTouchListener);

        vsComputerView.setBallX();

        startTimer();

        if (isEasy)
            vsComputerView.easyMove();
        else
            vsComputerView.moveHard();

        rL.addView(vsComputerView);

    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            vsComputerView.invalidate();
            vsComputerView.setIsback(true);

            Intent intent = new Intent(VsComputerMode.this, GameOver.class);
            intent.putExtra("score", usrScore);
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

                vsComputerView.setSpeedY(20F);

                double random = Math.random();

                if (random > 0.50)
                    vsComputerView.setSpeedX(20F);
                else
                    vsComputerView.setSpeedX(-20F);


                timer.setVisibility(View.INVISIBLE);
                stopTimer();
            }
        }.start();
    }

    void stopTimer() {
        starttimer.cancel();
    }
}