package com.luciofm.ifican.app;

import android.app.Application;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by luciofm on 5/23/14.
 */
public class IfICan extends Application {

    /* scan codes from my presenter controller */
    public static final int BUTTON_NEXT = 104;
    public static final int BUTTON_PREV = 109;

    private HashMap<String, Typeface> typefaces = new HashMap<>();

    public Typeface getTypeface(String typeface) {
        Typeface tf = typefaces.get(typeface);
        if (tf == null) {
            tf = Typeface.createFromAsset(getAssets(), "fonts/" + typeface);

            if (tf == null)
                tf = Typeface.DEFAULT;

            typefaces.put(typeface, tf);
        }

        return tf;
    }
}
