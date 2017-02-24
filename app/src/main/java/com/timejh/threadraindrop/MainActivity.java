package com.timejh.threadraindrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout stageLayout;
    Button bt_start, bt_pause, bt_stop;

    int deviceWidth, deviceHeight;

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

        Stage stage = new Stage(this);
        stageLayout.addView(stage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                break;
            case R.id.bt_pause:
                break;
            case R.id.bt_stop:
                break;
        }
    }

    class Stage extends View {

        Paint rainColor;

        public Stage(Context context) {
            super(context);
            rainColor = new Paint();
            rainColor.setColor(Color.BLUE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(deviceWidth / 2, 0, 10, rainColor);
        }
    }
}
