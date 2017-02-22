package com.project.andredantas.thefinalcountdownproject.ui;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.andredantas.thefinalcountdownproject.R;
import com.project.andredantas.thefinalcountdownproject.widget.AnimationUtil;
import com.project.andredantas.thefinalcountdownproject.widget.CircleCountDownView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleCountDownFragment extends Fragment {
    private static int INTERVAL_TIME = 1000;

    @Bind(R.id.et_total_time)
    EditText etTime;
    @Bind(R.id.et_init_time)
    EditText inTime;
    @Bind(R.id.circle_count_down_view)
    CircleCountDownView countDownView;
    @Bind(R.id.startTimer)
    Button startTimerBt;
    @Bind(R.id.cancelTimer)
    Button cancelTimerBt;

    CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_count_down, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public void initView(View view){
        String timeInterval = etTime.getText().toString();
        String initInterval = inTime.getText().toString();
        if (TextUtils.isEmpty(timeInterval) || TextUtils.isEmpty(initInterval)) {
            Toast.makeText(getActivity(), "Please enter end time in Edit text.", Toast.LENGTH_SHORT).show();
        } else {
            view.setVisibility(View.GONE); // hide button
            countDownView.setVisibility(View.VISIBLE); // show progress view
            cancelTimerBt.setVisibility(View.VISIBLE); // show cancel button

            countDownView.setProgress(1);
            countDownView.setEndTime(Integer.parseInt(timeInterval)); // up to finish time
            countDownView.setInitTime(Integer.parseInt(initInterval));
            countDownView.setListener(null);
            // start timer
            startCountDownTimer();

            // hide softkeyboard
            View currentFocus = getActivity().getCurrentFocus();
            if (currentFocus != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

    public void stopCountDown() {
        countDownView.clearAnimation();
        countDownView.setVisibility(View.GONE);
        countDownTimer.cancel();
        countDownView.setFirstTime(true);

        cancelTimerBt.setVisibility(View.GONE);
        startTimerBt.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.startTimer)
    public void startTimer(View view){
        initView(view);
    }

    @OnClick(R.id.cancelTimer)
    public void cancelTimer(){
        stopCountDown();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (countDownTimer != null)
            countDownTimer.cancel();

        countDownView.clear();
    }
}
