package com.project.andredantas.thefinalcountdownproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.andredantas.thefinalcountdownproject.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Andre Dantas on 8/2/17.
 */

public class CircleCountDownView extends FrameLayout {
    private ProgressBar progressBarView;
    private TextView progressTextView;

    int progress;
    int endTime, initTime;
    boolean firstTime = true;

    public CircleCountDownView(Context context) {
        super(context);
        init(context);
    }

    public CircleCountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleCountDownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {
        View rootView = inflate(ctx, R.layout.layout_count_down_view, this);
        progressBarView = (ProgressBar) rootView.findViewById(R.id.view_progress_bar);
        progressTextView = (TextView) rootView.findViewById(R.id.view_progress_text);
        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
    }

    public void setCustomProgress(int startTime, int endTime) {
        progressBarView.setMax(endTime);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(startTime);
        int elapsedTime = endTime - startTime;
        progressTextView.setText(String.valueOf(elapsedTime));
    }

    public void setProgress(int value){
        progressBarView.setProgress(value);
    }

    public void onTick(CircleCountDownView countDownView){
        //reset progress when finish
        if (progress > endTime){
            progress = 1;
        }else if (progress == endTime){
            //animation when finish
            ProgressBarAnimation anim = new ProgressBarAnimation(countDownView, endTime, 0);
            anim.setDuration(500);
            countDownView.startAnimation(anim);
        }

        //init with progress size
        if (firstTime){
            progress = endTime - (endTime - initTime);
            firstTime = false;
        }

        countDownView.setCustomProgress(progress, endTime);
        progress = progress + 1;
    }

    public int getProgress() {
        return progress;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getInitTime() {
        return initTime;
    }

    public void setInitTime(int initTime) {
        this.initTime = initTime;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}
