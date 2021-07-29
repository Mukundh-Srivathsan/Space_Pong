package com.example.spacepong.normalView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.spacepong.interfaces.NormalModeComms;

import java.util.Random;


public class NormalView extends View {

    private static final String TAG = "CustomView";
    private final Random random = new Random();
    private Handler handler = new Handler();
    NormalModeComms sc;

    Rect slider = new Rect();
    Rect topbar = new Rect();

    Paint paint = new Paint();

    CountDownTimer easyTimer;
    CountDownTimer hardTimer;

    private final int width = this.getResources().
            getDisplayMetrics().
            widthPixels;
    private final int height = this.getResources().
            getDisplayMetrics()
            .heightPixels;

    private int currX = width / 2;
    private final int currY = height;

    private float ballX;
    private float ballY = height / 2f;

    private float speedX = 0F;
    private float speedY = 0F;

    private long time = 15;
    private int speedUp = 1;

    private boolean isback = false;

    public NormalView(Context context) {
        super(context);

        init(null);
    }

    public NormalView(Context context, NormalModeComms sc) {
        super(context);

        this.sc = sc;

        init(null);
    }

    public NormalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public NormalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public NormalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        this.ballX = (float) random.doubles(50, 1030)
                .findFirst()
                .getAsDouble();
    }


    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void setIsback(boolean isback) {
        this.isback = isback;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        slider.left = currX + 160;
        slider.right = currX - 160;
        slider.top = currY - 220;
        slider.bottom = currY - 260;

        canvas.drawRect(slider, paint);

        topbar.left = 0;
        topbar.right = width;
        topbar.top = 200;
        topbar.bottom = 180;

        canvas.drawRect(topbar, paint);

        canvas.drawCircle(ballX, ballY, 20, paint);

    }

    public void easyMove() {

        if (!isback) {
            if ((ballY < (height - 100) && ((ballY > 200)) && (ballX < width) && (ballX > 0))) {
                ballY += speedY;
                ballX += speedX;
                easyCall();
            }
            if (hitsSlider()) {

                sc.setScore();

                sc.playSound(2);

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
                sc.playSound(1);
                speedY *= -1.0F;
                ballY += speedY;
            }
            if (ballX <= 0 || ballX >= width) {

                sc.playSound(1);

                speedX *= -1.0;
                ballX += speedX;
            }
            if (ballY > (height - 180)) {
                ballY = height / 2F;
                speedY = 0;
                setBallX();
                speedX = 0;
                stopEasyCall();
                sc.end();
            }
        }

    }

    public void moveHard() {

        if (!isback) {
            runnable.run();
        }

    }

    boolean hitsSlider() {
        if (ballX < slider.left && ballX > slider.right) {
            return (ballY >= height - 260 && ballY <= height - 220);
        }
        return false;
    }

    void easyCall() {
        easyTimer = new CountDownTimer(10, 5) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                invalidate();
                easyMove();
            }
        }.start();
    }

    void stopEasyCall() {
        easyTimer.cancel();
    }
/*
    void hardCall() {
        hardTimer = new CountDownTimer(time, 5) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                invalidate();
                moveHard();
            }
        }.start();
    }

    void stopHardCall() {
        hardTimer.cancel();
    }
 */

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!isback) {
                if ((ballY < (height - 100) && ((ballY > 200)) && (ballX < width) && (ballX > 0))) {
                    ballY += speedY;
                    ballX += speedX;
                    if (speedUp % 10 == 0) {
                        time--;
                    }
                    handler.removeCallbacks(this);
                    handler.postDelayed(this, time);
                    //hardCall();
                }
                if (hitsSlider()) {

                    sc.playSound(2);

                    float rightDist = ballX - slider.right;
                    float centerDist = ballX - slider.centerX();

                    if (rightDist < centerDist) {
                        speedX *= -1.0;
                        ballX += speedX;
                    }
                    speedY *= -1.0;
                    ballY += speedY;
                    sc.setScore();
                    speedUp++;
                }
                if (ballY < 200) {

                    sc.playSound(1);

                    speedY *= -1.0;
                    ballY += speedY;
                }
                if (ballX <= 0 || ballX >= width) {

                    sc.playSound(1);

                    speedX *= -1.0;
                    ballX += speedX;
                }
                if (ballY > (height - 180)) {
                    ballY = height / 2F;
                    speedY = 0;
                    setBallX();
                    speedX = 0;
                    handler.removeCallbacks(this);
                    //stopHardCall();
                    sc.end();
                }
            }
            invalidate();
        }
    };
}
