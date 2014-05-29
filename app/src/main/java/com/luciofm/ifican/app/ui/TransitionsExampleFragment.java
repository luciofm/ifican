package com.luciofm.ifican.app.ui;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.YFractionProperty;
import com.luciofm.ifican.app.util.Utils;
import com.luciofm.ifican.app.util.ViewInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class TransitionsExampleFragment extends BaseFragment {

    @InjectView(R.id.container)
    LinearLayout container;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.gif1)
    GifImageView gif1;
    @InjectView(R.id.gif2)
    GifImageView gif2;
    @InjectView(R.id.gif3)
    GifImageView gif3;

    int leftDelta;
    int topDelta;
    int pink;
    int soft_pink;
    int green;
    int transparent;

    ViewInfo info;
    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();

    public TransitionsExampleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        info = getArguments().getParcelable(ViewInfo.VIEW_INFO);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_transitions_example;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        pink = getResources().getColor(R.color.pink);
        soft_pink = getResources().getColor(R.color.soft_pink);
        green = getResources().getColor(R.color.green);
        transparent = Color.TRANSPARENT;

        text1.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                text1.getViewTreeObserver().removeOnPreDrawListener(this);

                int[] screenLocation = new int[2];
                text1.getLocationOnScreen(screenLocation);
                leftDelta = info.left - screenLocation[0];
                topDelta = info.top - screenLocation[1];

                /* Move text1 to the last screen position */
                text1.setTranslationX(leftDelta);
                text1.setTranslationY(topDelta);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runEnterAnimation();
                    }
                }, 400);
                return false;
            }
        });
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                gif1.setVisibility(View.VISIBLE);
                Utils.startGif(gif1);
                break;
            case 3:
                gif2.setVisibility(View.VISIBLE);
                Utils.stopGif(gif1);
                Utils.startGif(gif2);
                break;
            case 4:
                Utils.stopGif(gif2);
                gif1.setVisibility(View.GONE);
                gif2.setVisibility(View.GONE);
                gif3.setVisibility(View.VISIBLE);
                break;
            case 5:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @Override
    public void onPrevPressed() {
        if (--currentStep > 0) {
            switch (currentStep) {
                case 1:
                    gif1.setVisibility(View.GONE);
                    break;
                case 2:
                    Utils.startGif(gif1);
                    gif2.setVisibility(View.GONE);
                    break;
                case 3:
                    gif1.setVisibility(View.VISIBLE);
                    gif2.setVisibility(View.VISIBLE);
                    gif3.setVisibility(View.GONE);
                    Utils.startGif(gif2);
            }
            return;
        }
        super.onPrevPressed();
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    private void runEnterAnimation() {
        /* Animate color transitions */
        ObjectAnimator background = ObjectAnimator.ofObject(container, "backgroundColor",
                                                            new ArgbEvaluator(), green,
                                                            pink).setDuration(500);
        ObjectAnimator textBackground = ObjectAnimator.ofObject(text1, "backgroundColor",
                                                                new ArgbEvaluator(), soft_pink,
                                                                green).setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(background, textBackground);
        set.start();

        /* Translate text1 to its current (R.layout.fragment_transitions_example) position */
        text1.animate().setDuration(500)
                .translationX(0)
                .translationY(0)
                .setInterpolator(sDecelerator);
    }

    @OnClick({R.id.gif1, R.id.gif2})
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
        return enter ? null : ObjectAnimator.ofFloat(null, new YFractionProperty(), 0f, -1f);
    }
}
