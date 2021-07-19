package com.example.spacepong.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.os.Handler;

import java.util.Random;


public class CustomView extends View {

    private static final String TAG = "CustomView";
    private Random random = new Random();

    Rect slider = new Rect();
    Rect topbar = new Rect();

    Paint paint = new Paint();

    CountDownTimer countDownTimer;

    private int width = this.getResources().getDisplayMetrics().widthPixels;
    private int height = this.getResources().getDisplayMetrics().heightPixels;

    private int currX = width / 2;
    private int currY = height;

    private float ballX;
    private float ballY = height / 2f;

    private float speedX = 0F;
    private float speedY = 0F;

    private long time = 10;


    public CustomView(Context context) {
        super(context);

        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        paint.setColor(Color.WHITE);
    }

    public void setCurrX(int currX) {
        this.currX = currX;
    }

    public void setBallX() {
        this.ballX = (float) random.doubles(50, 1030).findFirst().getAsDouble();
        ;
    }


    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

       /* if (currX + 160 < width && currX - 160 > 0) {
            rect.left = currX + 160;
            rect.right = currX - 160;
        }else if(currX + 160 >= width)
        {
            rect.left = width;
            rect.right = width - 160;
        }else{
            rect.left = 160;
            rect.right = 0;
        }*/

        slider.left = currX + 160;
        slider.right = currX - 160;
        slider.top = currY - 180;
        slider.bottom = currY - 220;

        canvas.drawRect(slider, paint);

        topbar.left = 0;
        topbar.right = width;
        topbar.top = 200;
        topbar.bottom = 180;

        canvas.drawRect(topbar, paint);

        canvas.drawCircle(ballX, ballY, 20, paint);

    }

    public void move() {

        if ((ballY < (height - 100) && ((ballY > 200)) && (ballX < width) && (ballX > 0))) {
            ballY += speedY;
            ballX += speedX;
            reCall();
        }
        if (hitsSlider()) {

            float rightDist = ballX - slider.right;
            float centerDist = ballX - slider.centerX();

            if (rightDist < centerDist) {
                speedX *= -1.0;
                ballX += speedX;
            }
            speedY *= -1.0;
            ballY += speedY;
        }
        if (ballY < 200) {
            speedY *= -1.0;
            ballY += speedY;
        }
        if (ballX <= 0 || ballX >= width) {
            speedX *= -1.0;
            ballX += speedX;
        }
        if (ballY > (height - 180)) {
            ballY = height / 2;
            speedY = 0;
            setBallX();
            speedX = 0;
            stopCall();
        }
    }

    boolean hitsSlider() {
        if (ballX < slider.left && ballX > slider.right) {
            if (ballY >= height - 220)
                return true;
            return false;
        }
        return false;
    }

    void reCall() {
        countDownTimer = new CountDownTimer(time, 5) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.d(TAG, "Called");
                invalidate();
                move();
            }
        }.start();
    }

    void stopCall() {
        countDownTimer.cancel();
    }
}
