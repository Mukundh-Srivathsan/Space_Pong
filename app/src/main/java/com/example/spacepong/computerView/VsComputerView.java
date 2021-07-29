package com.example.spacepong.computerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.spacepong.interfaces.ComputerModeComms;

import java.util.Random;

public class VsComputerView extends View {

    private static final String TAG = "CustomView";
    private final Random random = new Random();

    Rect slider = new Rect();
    Rect comp = new Rect();

    Paint paint = new Paint();

    CountDownTimer easyTimer;
    CountDownTimer hardTimer;
    CountDownTimer compTimer;

    private final int width = this.getResources().
            getDisplayMetrics().
            widthPixels;
    private final int height = this.getResources().
            getDisplayMetrics()
            .heightPixels;

    private int currX = width / 2;
    private final int currY = height;

    private int compScore = 0;

    private int compX = width / 2;
    //private final int compY = height;

    private float ballX;
    private float ballY = height / 2f;

    private float speedX = 0F;
    private float speedY = 0F;

    private final long time = 10;
    private int speedUp = 0;

    private final int handicap = random.ints(0, 15)
            .findFirst()
            .getAsInt();

    private boolean isback = false;

    ComputerModeComms sc;

    public VsComputerView(Context context) {
        super(context);
    }

    public VsComputerView(Context context, ComputerModeComms sc) {
        super(context);

        this.sc = sc;

        init(null);
    }

    public VsComputerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public VsComputerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public VsComputerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        paint.setColor(Color.WHITE);
        comp.left = width / 2 + 160;
        comp.right = width / 2 - 160;
        comp.top = 200;
        comp.bottom = 160;
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

        canvas.drawRect(comp, paint);

        canvas.drawCircle(ballX, ballY, 20, paint);

    }

    public void easyMove() {

        if (!isback) {
            if ((ballY < (height - 100) && ((ballY > 200)) && (ballX < width) && (ballX > 0))) {

                comp.left = (int) ballX - 160;
                comp.right = (int) ballX + 160;

                ballY += speedY;
                ballX += speedX;
                //stopEasyCall();
                easyCall();
            }
            if (hitsSlider()) {

                sc.setUsrScore();

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
                if (compScore < handicap) {
                    comp.left = (int) ballX - 160;
                    comp.right = (int) ballX + 160;

                    sc.playSound(2);

                    sc.setCompScore();

                    compScore++;

                    speedY *= -1.0F;
                    ballY += speedY;
                } else {
                    if(ballX>0) {
                        comp.left = (int) ballX - 350;
                        comp.right = (int) ballX - 30;
                    }else
                    {
                        comp.left = (int) ballX + 30;
                        comp.right = (int) ballX + 350;
                    }
                    sc.end();
                }
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
            if ((ballY < (height - 100) && ((ballY > 200)) && (ballX < width) && (ballX > 0))) {
                ballY += speedY;
                ballX += speedX;

                comp.left = (int) ballX - 160;
                comp.right = (int) ballX + 160;
                //stopHardCall();
                hardCall();
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
                sc.setUsrScore();
            }
            if (ballY < 200) {

                comp.left = (int) ballX - 160;
                comp.right = (int) ballX + 160;

                sc.setCompScore();

                sc.playSound(2);

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
                stopHardCall();
                sc.end();
            }
        }
    }

    boolean hitsSlider() {
        if (ballX < slider.left && ballX > slider.right) {
            return (ballY >= height - 260 && ballY <= height - 220);
        }
        return false;
    }

    void easyCall() {
        easyTimer = new CountDownTimer(time, 25) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                postInvalidate();
                easyMove();
                //stopEasyCall();
            }
        }.start();
    }

    void stopEasyCall() {
        easyTimer.cancel();
    }

    void hardCall() {
        hardTimer = new CountDownTimer(time, 5) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                postInvalidate();
                moveHard();
                //stopHardCall();
            }
        }.start();
    }

    void stopHardCall() {
        hardTimer.cancel();
    }
}