package com.luciofm.ifican.app.util;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by luciofm on 5/25/14.
 */
public class Utils {

    private Utils() {
    }

    public static void stopGif(GifImageView view) {
        GifDrawable drawable = (GifDrawable) view.getDrawable();
        if (drawable.isPlaying())
            drawable.stop();
    }
}
