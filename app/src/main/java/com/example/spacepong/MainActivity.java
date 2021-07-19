package com.example.spacepong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.spacepong.views.CustomView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate : MainActivity");

        RelativeLayout rL = (RelativeLayout) findViewById(R.id.layout);

        CustomView customView = new CustomView(this);

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                customView.setCurrX((int) event.getX());
                customView.invalidate();

                return true;
            }
        };
        customView.setOnTouchListener(onTouchListener);

        Button start = (Button) findViewById(R.id.button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.setSpeedY(-20F);
                customView.setSpeedX(20F);
                customView.move();
            }
        });

        rL.addView(customView);
    }
}