package com.luciofm.ifican.app.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luciofm on 5/25/14.
 */
public class AnimUtils {
    private AnimUtils() {
    }

    public static void shakeView(final View view) {
        ObjectAnimator physX = ObjectAnimator.ofFloat(view, "translationX", -12f, 12f);
        physX.setDuration(50);
        physX.setRepeatCount(10);
        physX.setRepeatMode(ObjectAnimator.RESTART);
        physX.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().translationX(0f).setDuration(10);
            }
        });
        physX.start();
    }

    public static void beginDelayedTransition(ViewGroup container) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            TransitionManager.beginDelayedTransition(container);
    }

    public static Animator createXTransition(View view, boolean enter) {
        ObjectAnimator x;
        ObjectAnimator y;
        if (enter) {
            x = ObjectAnimator.ofFloat(view, "translationX", 1f, 0f);
            y = ObjectAnimator.ofFloat(view, "translationY", 1f, 0f);
        } else {
            x = ObjectAnimator.ofFloat(view, "translationX", 0f, -1f);
            y = ObjectAnimator.ofFloat(view, "translationY", 0f, -1f);
        }

        AnimatorSet set = new AnimatorSet();
        set.playTogether(x, y);
        return set;
    }
}
