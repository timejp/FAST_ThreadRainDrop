package com.timejh.threadraindrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout stageLayout;
    Button bt_start, bt_pause, bt_stop;

    int deviceWidth, deviceHeight;

    boolean running = true;

    Stage stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stageLayout = (FrameLayout) findViewById(R.id.stage);

        bt_start = (Button) findViewById(R.id.bt_start);
        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_stop = (Button) findViewById(R.id.bt_stop);

        bt_start.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_stop.setOnClickListener(this);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;

        stage = new Stage(this);
        stageLayout.addView(stage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                DrawStage drawStage = new DrawStage(stage);
                drawStage.start();
                MakeRain rain = new MakeRain(stage);
                rain.start();
                break;
            case R.id.bt_pause:
                break;
            case R.id.bt_stop:
                break;
        }
    }

    class DrawStage extends Thread {

        Stage stage;

        public DrawStage(Stage stage) {
            this.stage = stage;
        }

        public void run() {
            while (running) {
                stage.postInvalidate();
//                try {
//                    Thread.sleep(10);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
            }
        }
    }

    class MakeRain extends Thread {

        boolean flag = true;
        Stage stage;

        public MakeRain(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void run() {
            while (flag) {
                new Rain(stage).start();
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Rain extends Thread {

        boolean stopflag = false;
        boolean pauseflag = false;

        int x, y, radius, speed;

        Stage stage;

        public Rain(Stage stage) {
            Random random = new Random();
            x = random.nextInt(deviceWidth);
            y = 0;
            radius = random.nextInt(30) + 1;
            speed = random.nextInt(10) + 1;

            this.stage = stage;
            stage.addRain(this);
        }

        @Override
        public void run() {
            while (!stopflag) {
                if (!pauseflag) {
                    y = y + 1;
                    try {
                        Thread.sleep(speed * 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (y > deviceHeight)
                    stopflag = true;
            }
            stage.removeRain(this);
        }
    }

    class Stage extends View {

        Paint rainColor;
        List<Rain> rains;

        public Stage(Context context) {
            super(context);
            rainColor = new Paint();
            rainColor.setColor(Color.BLUE);
            rains = new CopyOnWriteArrayList<>();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Log.i("Rain Size", "==================================" + rains.size());
            for (Rain rain : rains) {
                canvas.drawCircle(rain.x, rain.y, rain.radius, rainColor);
            }
        }

        public void addRain(Rain rain) {
            rains.add(rain);
        }

        public void removeRain(Rain rain) {
            rains.remove(rain);
            rain.interrupt();
        }
    }
}
