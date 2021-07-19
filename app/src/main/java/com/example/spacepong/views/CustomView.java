package com.example.spacepong.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.os.Handler;


public class CustomView extends View {

    private static final String TAG = "CustomView";

    Rect slider = new Rect();
    Rect topbar = new Rect();

    Paint paint = new Paint();

    private int width = this.getResources().getDisplayMetrics().widthPixels;
    private int height = this.getResources().getDisplayMetrics().heightPixels;

    private int currX = width / 2;
    private int currY = height;

    private float ballX = width / 2f;
    private float ballY = height / 2f;

    private float speedX = 0F;
    private float speedY = 0F;


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

    public void setCurrY(int currY) {
        this.currY = currY;
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
        slider.top = currY - 220;
        slider.bottom = currY - 240;

        canvas.drawRect(slider, paint);

        topbar.left = 0;
        topbar.right = width;
        topbar.top = 200;
        topbar.bottom = 170;

        canvas.drawRect(topbar, paint);

        canvas.drawCircle(ballX, ballY, 20, paint);

        //move();

    }

    public void move() {

        if ((ballY < (height - 100) && ((ballY > 200)) && (ballX < width) && (ballX > 0))) {
            ballY += speedY;
            ballX += speedX;
            Handler handler = new Handler();
            handler.removeCallbacksAndMessages(call);
            handler.postDelayed(call, 10);
        }
        if (ballY < 200) {
            speedY *= -1.0;
            ballY += speedY;
        }
        if (ballX < 0 || ballX > width) {
            speedX *= -1.0;
            ballX += speedX;
        }
        if (ballY > (height - 100)) {
            ballY = height / 2;
            speedY = 0;
        }
    }

    private Runnable call = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void run() {
            Log.d(TAG, "Called");
            invalidate();
            move();
        }
    };
}
