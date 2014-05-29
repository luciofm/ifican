package com.luciofm.ifican.app.util;

import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by luciofm on 5/25/14.
 */
public class Utils {

    private Utils() {
    }

    public static void startGif(GifImageView view) {
        GifDrawable drawable = (GifDrawable) view.getDrawable();
        if (!drawable.isPlaying())
            drawable.start();
    }

    public static void stopGif(GifImageView view) {
        GifDrawable drawable = (GifDrawable) view.getDrawable();
        if (drawable.isPlaying())
            drawable.stop();
    }

    public static void dispatchTouch(View view) {
        dispatchTouch(view, 200);
    }

    public static void dispatchTouch(final View view, final long duration) {
        final long downTime = SystemClock.uptimeMillis();
        final long eventTime = SystemClock.uptimeMillis();
        final float x = 0.0f;
        final float y = 0.0f;
        // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        final int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(downTime,
                                                     eventTime,
                                                     MotionEvent.ACTION_DOWN,
                                                     x,
                                                     y,
                                                     metaState);

        // Dispatch touch event to view
        view.dispatchTouchEvent(motionEvent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MotionEvent motionEvent = MotionEvent.obtain(downTime + duration,
                                                             eventTime + duration,
                                                             MotionEvent.ACTION_UP,
                                                             x,
                                                             y,
                                                             metaState);
                view.dispatchTouchEvent(motionEvent);
            }
        }, duration);
    }
}
