package com.project.andredantas.thefinalcountdownproject.ui;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.andredantas.thefinalcountdownproject.R;
import com.project.andredantas.thefinalcountdownproject.widget.AnimationUtil;
import com.project.andredantas.thefinalcountdownproject.widget.CircleCountDownView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpcomingCountDownFragment extends Fragment {
    @Bind(R.id.item_upcoming_day_progress)
    CircleCountDownView mProgressDay;
    @Bind(R.id.item_upcoming_hour_progress)
    CircleCountDownView mProgressHour;
    @Bind(R.id.item_upcoming_minute_progress)
    CircleCountDownView mProgressMinute;
    @Bind(R.id.item_upcoming_seconds_progress)
    CircleCountDownView mProgressSecond;
    @Bind(R.id.days)
    EditText editDay;
    @Bind(R.id.hours)
    EditText editHours;
    @Bind(R.id.minutes)
    EditText editMinutes;
    @Bind(R.id.seconds)
    EditText editSeconds;
    @Bind(R.id.item_upcoming_time_box)
    LinearLayout upcomingTimerBox;
    @Bind(R.id.startTimer)
    Button startTimerBt;
    @Bind(R.id.cancelTimer)
    Button cancelTimerBt;

    private CountDownTimer countDownTimerSec, countDownTimerMin, countDownTimerHour, countDownTimerDays;
    private int formDays = (1000 * 60 * 60 * 24);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_count_down, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void initView(View view) {
        String day = editDay.getText().toString();
        String hours = editHours.getText().toString();
        String minutes = editMinutes.getText().toString();
        String seconds = editSeconds.getText().toString();

        if (TextUtils.isEmpty(day) || TextUtils.isEmpty(hours) || TextUtils.isEmpty(minutes) || TextUtils.isEmpty(seconds)) {
            Toast.makeText(getActivity(), "Please enter the values", Toast.LENGTH_SHORT).show();
        }else{
            view.setVisibility(View.GONE); // hide button
            cancelTimerBt.setVisibility(View.VISIBLE); // show cancel button
            upcomingTimerBox.setVisibility(View.VISIBLE);
            showProgressBars();

            long daysInitTime = Long.valueOf(day);
            long hoursInitTime = Long.valueOf(hours);
            long minutesInitTime = Long.valueOf(minutes);
            long secondsInitTime = Long.valueOf(seconds);

            mProgressDay.setEndTime(30);
            mProgressDay.setInitTime(daysInitTime);
            mProgressDay.setListener(new CircleCountDownView.OnFinishCycleProgressBar() {
                @Override
                public void onFinish() {
                    if (countDownTimerDays != null) {
                        countDownTimerDays.onFinish();
                    }
                }
            });

            mProgressHour.setEndTime(24);
            mProgressHour.setInitTime(hoursInitTime);
            mProgressHour.setListener(new CircleCountDownView.OnFinishCycleProgressBar() {
                @Override
                public void onFinish() {
                    if (countDownTimerHour != null) {
                        countDownTimerHour.onFinish();
                    }
                }
            });

            mProgressMinute.setEndTime(60);
            mProgressMinute.setInitTime(minutesInitTime);
            mProgressMinute.setListener(new CircleCountDownView.OnFinishCycleProgressBar() {
                @Override
                public void onFinish() {
                    if (countDownTimerMin != null) {
                        countDownTimerMin.onFinish();
                    }
                }
            });

            mProgressSecond.setEndTime(60);
            mProgressSecond.setInitTime(secondsInitTime);
            mProgressSecond.setListener(new CircleCountDownView.OnFinishCycleProgressBar() {
                @Override
                public void onFinish() {
                    if (countDownTimerSec != null) {
                        countDownTimerSec.onFinish();
                    }
                }
            });

            startCountDownTimerDay();
            startCountDownTimerHour();
            startCountDownTimerMin();
            startCountDownTimerSecond();

            // hide softkeyboard
            View currentFocus = getActivity().getCurrentFocus();
            if (currentFocus != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }

    }

    public void showProgressBars() {
        //only for fade in
        AnimationUtil.with(mProgressSecond).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
        AnimationUtil.with(mProgressMinute).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
        AnimationUtil.with(mProgressHour).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
        AnimationUtil.with(mProgressDay).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
    }

    public void startCountDownTimerSecond() {
        //Why the final time is to long?
        //In Upcoming when a countdown depends on another countdown(the countdown minute needs to wait for the second countdown to finish to be able to count)
        // it is better to work with listener. So the final time is long only so that onFinish()
        // is called only on the listener's return, not when it actually arrives at the end of the final time

        countDownTimerSec = new CountDownTimer(mProgressSecond.getEndTime() * formDays /*final time**/, 1000 /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressSecond.onTick(mProgressSecond);
            }

            @Override
            public void onFinish() {
                if (countDownTimerMin != null) {
                    countDownTimerMin.cancel();
                }
                startCountDownTimerMin();
            }
        };
        countDownTimerSec.start();
    }


    public void startCountDownTimerMin() {
        countDownTimerMin = new CountDownTimer(mProgressMinute.getEndTime() * formDays /*final time**/, formDays /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressMinute.onTick(mProgressMinute);
            }

            @Override
            public void onFinish() {
                if (countDownTimerHour != null) {
                    countDownTimerHour.cancel();
                }
                startCountDownTimerHour();
            }
        };
        countDownTimerMin.start();
    }


    public void startCountDownTimerHour() {
        countDownTimerHour = new CountDownTimer(mProgressHour.getEndTime() * formDays /*final time**/, formDays /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressHour.onTick(mProgressHour);
            }

            @Override
            public void onFinish() {
                if (countDownTimerDays != null) {
                    countDownTimerDays.cancel();
                }
                startCountDownTimerDay();
            }
        };
        countDownTimerHour.start();
    }


    public void startCountDownTimerDay() {
        countDownTimerDays = new CountDownTimer(mProgressDay.getEndTime() * formDays /*final time**/, formDays /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressDay.onTick(mProgressDay);
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
        countDownTimerDays.start();
    }

    public void cancelCounter(CountDownTimer countdownTimer) {
        if (countdownTimer != null)
            countdownTimer.cancel();
    }

    public void stopCountDown() {
        cancelCounter(countDownTimerSec);
        cancelCounter(countDownTimerMin);
        cancelCounter(countDownTimerHour);
        cancelCounter(countDownTimerDays);

        mProgressDay.clear();
        mProgressDay.clearAnimation();
        mProgressDay.setFirstTime(true);

        mProgressHour.clear();
        mProgressHour.clearAnimation();
        mProgressHour.setFirstTime(true);

        mProgressMinute.clear();
        mProgressMinute.clearAnimation();
        mProgressMinute.setFirstTime(true);

        mProgressSecond.clear();
        mProgressSecond.clearAnimation();
        mProgressSecond.setFirstTime(true);

        cancelTimerBt.setVisibility(View.GONE);
        upcomingTimerBox.setVisibility(View.GONE);
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
        stopCountDown();
    }
}
