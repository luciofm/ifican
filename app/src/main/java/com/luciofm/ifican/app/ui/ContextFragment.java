package com.luciofm.ifican.app.ui;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.anim.YFractionProperty;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ContextFragment extends BaseFragment {

    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.gif1)
    GifImageView gif1;
    @InjectView(R.id.container2)
    View container2;

    int currentStep = 1;

    public ContextFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);
        currentStep = 1;
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                text1.animate().scaleX(0.7f).scaleY(0.7f);
                gif1.setVisibility(View.VISIBLE);
                container2.setVisibility(View.VISIBLE);
                break;
            case 3:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    @OnClick(R.id.gif1)
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
        return enter ? ObjectAnimator.ofFloat(null, new YFractionProperty(), 1f, 0f)
                       : ObjectAnimator.ofFloat(null, new XFractionProperty(), 0f, -1f);
    }
}
