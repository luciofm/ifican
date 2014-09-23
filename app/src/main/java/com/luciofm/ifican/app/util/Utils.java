package com.luciofm.ifican.app.util;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by luciofm on 5/25/14.
 */
public class Utils {

    private static final int ANIM_DURATION = 800;

    private Utils() {
    }

    public static void startGif(GifImageView view) {
        GifDrawable drawable = (GifDrawable) view.getDrawable();
        if (!drawable.isPlaying()) {
            drawable.reset();
            drawable.start();
        }
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
        final float x = view.getWidth() / 3;//0.0f;
        final float y = view.getHeight() / 3;//0.0f;
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

    public static long calcDuration(int position) {
        int pos = position;
        if (pos >= 4)
            pos -= 4;
        switch (pos) {
            case 1:
                return (long) (ANIM_DURATION + (ANIM_DURATION * 0.2f));
            case 2:
                return (long) (ANIM_DURATION + (ANIM_DURATION * 0.3f));
            case 3:
                return (long) (ANIM_DURATION + (ANIM_DURATION * 0.4f));
            default:
                return ANIM_DURATION;
        }
    }

    /**
     * Returns a valid DisplayMetrics object
     *
     * @param context valid context
     * @return DisplayMetrics object
     */
    public static DisplayMetrics getDisplayMetrics(final Context context) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * Get the current device physical size in inches.
     *
     * @param context valid context.
     * @return <b>double</b> device inches
     */
    public static double getDeviceDiagonalSizeInInches(final Context context) {
        final DisplayMetrics metrics = getDisplayMetrics(context);

        final double xInches = (double) metrics.widthPixels / metrics.xdpi;
        final double yInches = (double) metrics.heightPixels / metrics.ydpi;
        return Math.sqrt(Math.pow(xInches, 2) + Math.pow(yInches, 2));
    }

    /**
     * Get the current device physical display density.
     *
     * @param context valid context.
     * @return <b>int</b> with DENSITY_LOW, DENSITY_MEDIUM, DENSITY_TV, DENSITY_HIGH, DENSITY_XHIGH,
     * DENSITY_XXHIGH
     */
    public static int getDisplayDensity(final Context context) {
        // return (double)DisplayMetrics.DENSITY_DEFAULT * getDisplayMetrics(context).density;
        return getDisplayMetrics(context).densityDpi;
    }

    public static int dpToPx(final Context context, final float dp) {
        // Took from http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }

    public static int pxToDp(final Context context, final float px) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((px / displayMetrics.density) + 0.5);
    }

    public static int getScreenWidth(final Context context) {
        if (context == null)
            return 0;
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(final Context context) {
        if (context == null)
            return 0;
        return getDisplayMetrics(context).heightPixels;
    }
}
