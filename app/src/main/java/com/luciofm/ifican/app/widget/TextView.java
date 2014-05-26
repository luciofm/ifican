package com.luciofm.ifican.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.luciofm.ifican.app.IfICan;
import com.luciofm.ifican.app.R;


public class TextView extends android.widget.TextView {

    public TextView(Context context) {
        super(context);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupFont(context, attrs);
    }

    private void setupFont(Context context, AttributeSet attrs) {
        if (isInEditMode())
            return;

        TypedArray ta = null;
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.TextView);
            String typeFace = ta.getString(R.styleable.TextView_typeFace);
            setTypeface(typeFace);
        } finally {
            ta.recycle();
        }
    }

    public void setTypeface(String typeface) {
        if (!TextUtils.isEmpty(typeface)) {
            Typeface tf = ((IfICan)getContext().getApplicationContext()).getTypeface(typeface);
            setTypeface(tf);
        }
    }
}