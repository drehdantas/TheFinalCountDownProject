package com.project.andredantas.thefinalcountdownproject.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Andre Dantas on 2/1/17.
 */

public class ProgressBarAnimation extends Animation {
    private CircleCountDownView progressBar;
    private float from;
    private float  to;

    public ProgressBarAnimation(CircleCountDownView progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }
}
