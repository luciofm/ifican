package com.luciofm.ifican.app.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

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
}
