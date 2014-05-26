package com.luciofm.ifican.app.ui;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.AnimUtils;
import com.luciofm.ifican.app.anim.XFractionProperty;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class SixthFragment extends BaseFragment {

    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.gif1)
    GifImageView gif1;
    @InjectView(R.id.gif2)
    GifImageView gif2;
    @InjectView(R.id.gif3)
    GifImageView gif3;
    @InjectView(R.id.container2)
    View container2;

    int currentStep = 1;

    public SixthFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_sixth;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);
        currentStep = 1;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimUtils.shakeView(text1);
            }
        }, 300);
        return v;
    }

    @OnClick(R.id.container)
    public void onClick() {
        switch (++currentStep) {
            case 2:
                gif1.setVisibility(View.VISIBLE);
                container2.setVisibility(View.VISIBLE);
                break;
            case 3:
                gif1.performClick();
                gif2.setVisibility(View.VISIBLE);
                break;
            case 4:
                gif2.performClick();
                gif3.setVisibility(View.VISIBLE);
                break;
            case 5:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @OnClick({R.id.gif1, R.id.gif2, R.id.gif3})
    public void onGifClick(GifImageView view) {
        GifDrawable drawable = (GifDrawable) view.getDrawable();
        if (drawable.isPlaying())
            drawable.stop();
        else
            drawable.start();
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (transit == 0) {
            return null;
        }

        //Target will be filled in by the framework
        return enter ? ObjectAnimator.ofFloat(null, new XFractionProperty(), 1f, 0f)
                       : ObjectAnimator.ofFloat(null, new XFractionProperty(), 0f, -1f);
    }
}
