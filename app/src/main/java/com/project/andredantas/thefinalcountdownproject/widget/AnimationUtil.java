package com.project.andredantas.thefinalcountdownproject.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


public class AnimationUtil {

    private View mContainer;
    private Context mContext;
    private int mDuration = 250;
    private int mDalay = 0;
    private Animation mAnimation;
    private AnimationSet mAnimationSet;
    private int mVisibility = View.VISIBLE;

    private AnimationUtil(View container) {
        mContext = container.getContext();
        mContainer = container;
        mAnimationSet = new AnimationSet(false);
    }

    public static AnimationUtil with(View view) {
        return new AnimationUtil(view);
    }

    public AnimationUtil context(Context context) {
        mContext = context;
        return this;
    }

    public AnimationUtil duration(int duration) {
        mDuration = duration;
        return this;
    }

    public AnimationUtil animation(int animationId) {
        switch (animationId) {
            case FADE_IN:
                mAnimation = new AlphaAnimation(0, 1);
                break;
            case FADE_OUT:
                mAnimation = new AlphaAnimation(1, 0);
                break;
            case INCREASE_CENTER:
                mAnimation = new ScaleAnimation(0, 1, 0, 1, mContainer.getWidth()/2, mContainer.getHeight()/2);
                break;
            case DECREASE_CENTER:
                mAnimation = new ScaleAnimation(1, 0, 1, 0, mContainer.getWidth()/2, mContainer.getHeight()/2);
                break;
            case INCREASE_UP_DOWN:
                mAnimation = new ScaleAnimation(1, 1, 0, 1, mContainer.getWidth(), 0);
                break;
            case INCREASE_DOWN_UP:
                mAnimation = new ScaleAnimation(1, 1, 0, 1, mContainer.getWidth(), mContainer.getHeight());
                break;
            case DECREASE_UP_DOWN:
                mAnimation = new ScaleAnimation(1, 1, 1, 0, mContainer.getWidth(), mContainer.getHeight());
                break;
            case DECREASE_DOWN_UP:
                mAnimation = new ScaleAnimation(1, 1, 1, 0, mContainer.getWidth(), 0);
                break;
            case INCREASE_DOWN_UP_3:
                mAnimation = new ScaleAnimation(1, 1, 0.3f, 1, mContainer.getWidth(), mContainer.getHeight());
                break;
            case DECREASE_UP_DOWN_3:
                mAnimation = new ScaleAnimation(1, 1, 1, 0.3f, mContainer.getWidth(), mContainer.getHeight());
                break;
            case MOVE_DOWN_UP_360:
                if (mContext == null) {
                    throw new IllegalArgumentException("context() can not be empty");
                }
                mAnimation = new TranslateAnimation(0, 0, 360 * mContext.getResources().getDisplayMetrics().density, 0);
                break;
            case MOVE_UP_DOWN_360:
                mAnimation = new TranslateAnimation(0, 0, 0, 360 * mContext.getResources().getDisplayMetrics().density);
                break;
            case INCREASE_RIGHT_LEFT:
                mAnimation = new ScaleAnimation(0, 1, 1, 1, mContainer.getWidth(), 0);
                break;
            case DECREASE_RIGHT_LEFT:
                mAnimation = new ScaleAnimation(1, 0, 1, 1, 0, 0);
                break;
            case RIPPLE_IN:
                mContainer.post(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
                        int x = metrics.widthPixels / 2;
                        int y = mContainer.getHeight() / 2;
                        int radius = 1000;

                        Animator anim = ViewAnimationUtils.createCircularReveal(mContainer, x, y, 0, radius);
                        anim.setStartDelay(mDalay);
                        anim.setDuration(mDuration);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                mContainer.setVisibility(View.VISIBLE);
                            }
                        });

                        anim.start();
                    }
                });
                break;
            case RIPPLE_OUT:
                mContainer.post(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
                        int x = metrics.widthPixels / 2;
                        int y = mContainer.getHeight() / 2;
                        int radius = 1000;

                        Animator anim = ViewAnimationUtils.createCircularReveal(mContainer, x, y, radius, 0);
                        anim.setDuration(mDuration);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mContainer.setVisibility(View.INVISIBLE);
                            }
                        });

                        anim.start();
                    }
                });
                break;
        }
        mAnimation.setDuration(mDuration);
        mAnimationSet.addAnimation(mAnimation);
        return this;
    }

    public AnimationUtil visibility(int visibility) {
        mVisibility = visibility;
        return this;
    }

    public void ready() {
        if (mAnimation == null) {
            throw new IllegalArgumentException("animation() can not be empty");
        }
        mAnimation.setFillAfter(true);
        mContainer.setAnimation(mAnimationSet);
        mContainer.setVisibility(mVisibility);
    }

    public void ready21() {
        mContainer.post(new Runnable() {
            @Override
            public void run() {
                if (mContext == null) {
                    throw new IllegalArgumentException("Context can not be null");
                }
            }
        });
    }

    public static void translate(View view, int position){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", position);
        animator.setDuration(250);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }


    public static final int FADE_IN = 0;
    public static final int FADE_OUT = 1;
    public static final int INCREASE_CENTER = 2;
    public static final int DECREASE_CENTER = 3;
    public static final int INCREASE_UP_DOWN = 4;
    public static final int INCREASE_DOWN_UP = 5;
    public static final int DECREASE_UP_DOWN = 6;
    public static final int DECREASE_DOWN_UP = 7;
    public static final int SHAKE = 8;
    public static final int INCREASE_DOWN_UP_3 = 9;
    public static final int DECREASE_UP_DOWN_3 = 10;
    public static final int MOVE_DOWN_UP_360 = 11;
    public static final int MOVE_UP_DOWN_360 = 12;
    public static final int INCREASE_RIGHT_LEFT = 13;
    public static final int DECREASE_RIGHT_LEFT = 14;
    public static final int RIPPLE_IN = 15;
    public static final int RIPPLE_OUT = 16;


}
