package com.example.tug_pc.restaurantmanagermini.ultil;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.example.tug_pc.restaurantmanagermini.R;

import java.util.Random;

@SuppressWarnings("ALL")
public class AnimItemMain {
    private static int lastPosition = -1;
    private final static int FADE_DURATION = 1500; //FADE_DURATION in milliseconds

    public static void setAnimationFlicker(View viewToAnimate, Context context, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.flicker);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.3f, 1.0f, 0.3f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(FADE_DURATION);
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    public static void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.3f, 1.0f, 0.3f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public static void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.3f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public static void clearAnimation(View v) {
        v.clearAnimation();
    }
}
