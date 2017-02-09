package com.project.andredantas.thefinalcountdownproject;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.andredantas.thefinalcountdownproject.widget.AnimationUtil;
import com.project.andredantas.thefinalcountdownproject.widget.CircleCountDownView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static int INTERVAL_TIME = 1000;

    protected EditText etTime, inTime;
    protected CircleCountDownView countDownView;
    protected Button startTimerBt, cancelTimerBt;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTime = (EditText) findViewById(R.id.et_total_time);
        inTime = (EditText) findViewById(R.id.et_init_time);
        countDownView = (CircleCountDownView) findViewById(R.id.circle_count_down_view);
        startTimerBt = (Button) findViewById(R.id.startTimer);
        cancelTimerBt = (Button) findViewById(R.id.cancleTimer);

        // set click listeners
        startTimerBt.setOnClickListener(this);
        cancelTimerBt.setOnClickListener(this);
    }

    protected void startCountDown(final View view) {

        String timeInterval = etTime.getText().toString();
        String initInterval = inTime.getText().toString();
        if (TextUtils.isEmpty(timeInterval) || TextUtils.isEmpty(initInterval)) {
            Toast.makeText(this, "Please enter end time in Edit text.", Toast.LENGTH_SHORT).show();
        } else {
            etTime.getText().clear();
            inTime.getText().clear();
            view.setVisibility(View.GONE); // hide button
            countDownView.setVisibility(View.VISIBLE); // show progress view
            cancelTimerBt.setVisibility(View.VISIBLE); // show cancel button


            countDownView.setProgress(1);
            countDownView.setEndTime(Integer.parseInt(timeInterval)); // up to finish time
            countDownView.setInitTime(Integer.parseInt(initInterval));

            // start timer
            startCountDownTimer();

            // hide softkeyboard
            View currentFocus = this.getCurrentFocus();
            if (currentFocus != null) {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
    }

    public void startCountDownTimer() {
        //only for fade in
        AnimationUtil.with(countDownView).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
        countDownTimer = new CountDownTimer(countDownView.getEndTime() * INTERVAL_TIME /*final time**/, INTERVAL_TIME /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownView.onTick(countDownView);
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
        countDownTimer.start();
    }

    public void stopCountDown(View view) {
        countDownView.clearAnimation();
        countDownView.setVisibility(View.GONE);
        countDownTimer.cancel();
        countDownView.setFirstTime(true);

        cancelTimerBt.setVisibility(View.GONE);
        startTimerBt.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startTimer:
                startCountDown(view);
                break;
            case R.id.cancleTimer:
                stopCountDown(view);
                break;
        }
    }
}
