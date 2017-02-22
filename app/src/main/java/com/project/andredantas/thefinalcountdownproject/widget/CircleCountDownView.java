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

    long progress;
    long endTime, initTime;
    boolean firstTime = true;

    OnFinishCycleProgressBar listener;

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

    public void setCustomProgress(long startTime, long endTime) {
        progressBarView.setMax((int) endTime);
        progressBarView.setSecondaryProgress((int) endTime);
        progressBarView.setProgress((int) startTime);
        long elapsedTime = endTime - startTime;
        progressTextView.setText(String.valueOf((int) elapsedTime));
    }

    public void setProgress(int value){
        progressBarView.setProgress(value);
    }

    public void onTick(CircleCountDownView countDownView){
        //reset progress when finish
        if (progress == endTime - 1) {
            progressTextView.setText("0");
            countDownView.setCustomProgress(progress, endTime);
            progress = progress + 1;
        } else if (progress > endTime) {
            //animation when finish
            progressTextView.setText(String.valueOf(endTime - 1));
            ProgressBarAnimation anim = new ProgressBarAnimation(countDownView, endTime, 1);
            anim.setDuration(500);
            countDownView.startAnimation(anim);
            progress = 2;

            if (listener != null)
                listener.onFinish();
        }else{
            //init with progress size
            if (firstTime){
                progress = endTime - (endTime - initTime);
                firstTime = false;
            }

            countDownView.setCustomProgress(progress, endTime);
            progress = progress + 1;
        }
    }

    public long getProgress() {
        return progress;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getInitTime() {
        return initTime;
    }

    public void setInitTime(long initTime) {
        this.initTime = endTime - initTime;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public void setListener(OnFinishCycleProgressBar listener) {
        this.listener = listener;
    }

    public void clear(){
        progress = 0;
        endTime = 0;
        initTime = 0;
        firstTime = true;
    }

    public interface OnFinishCycleProgressBar {
        void onFinish();
    }
}
