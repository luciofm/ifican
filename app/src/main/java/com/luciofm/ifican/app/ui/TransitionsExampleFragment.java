package com.luciofm.ifican.app.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
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
    ViewGroup container;
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
    int soft_blue;
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

        pink = getResources().getColor(R.color.accent_pink);
        soft_blue = getResources().getColor(R.color.accent_blue_50);
        transparent = Color.TRANSPARENT;

        text1.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                text1.getViewTreeObserver().removeOnPreDrawListener(this);

                int[] screenLocation = new int[2];
                text1.getLocationOnScreen(screenLocation);
                leftDelta = (int) (info.left - screenLocation[0]);
                topDelta = info.top - screenLocation[1];

                /* Move text1 to the last screen position */
                text1.setTranslationX(leftDelta);
                text1.setTranslationY(topDelta);

                runEnterAnimation();
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
                gif1.animate().translationX(-gif1.getWidth()).alpha(0f)
                        .setStartDelay(0).setInterpolator(new AnticipateInterpolator(1f));
                gif2.animate().translationX(gif2.getWidth()).alpha(0f)
                        .setStartDelay(0).setInterpolator(new AnticipateInterpolator(1f));

                gif3.setVisibility(View.VISIBLE);
                Utils.startGif(gif3);
                gif3.setAlpha(0f);
                gif3.setScaleX(0f);
                gif3.setScaleY(0f);
                gif3.animate().alpha(1f).scaleY(1f).scaleX(1f)
                        .setInterpolator(new OvershootInterpolator(5f))
                        .setStartDelay(200).setDuration(500);
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
                    Utils.stopGif(gif1);
                    break;
                case 2:
                    Utils.startGif(gif1);
                    Utils.stopGif(gif2);
                    gif2.setVisibility(View.GONE);
                    break;
                case 3:
                    gif1.animate().translationX(1f).alpha(1f)
                            .setStartDelay(400).setInterpolator(new OvershootInterpolator(1f));
                    gif2.animate().translationX(1f).alpha(1f)
                            .setStartDelay(400).setInterpolator(new OvershootInterpolator(1f));
                    gif3.animate().alpha(0f).scaleY(0f).scaleX(0f)
                        .setInterpolator(new AnticipateInterpolator(3f))
                        .setStartDelay(0).setDuration(500);
                    Utils.stopGif(gif3);
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
                                                            new ArgbEvaluator(), soft_blue,
                                                            pink).setDuration(500);
        background.start();
        /* Translate text1 to its current (R.layout.fragment_transitions_example) position */
        text1.animate().setDuration(500)
                .scaleX(0.7f)
                .scaleY(0.7f)
                .translationX((-(text1.getWidth() * 0.15f)))
                .translationY(0)
                .setInterpolator(sDecelerator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.accent_pink)));
            }
        });
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
