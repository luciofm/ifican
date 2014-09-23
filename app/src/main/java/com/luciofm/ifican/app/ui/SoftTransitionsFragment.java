package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.SimpleAnimatorListener;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.util.ViewInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SoftTransitionsFragment extends BaseFragment {

    @InjectView(R.id.container)
    LinearLayout container;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.text2)
    TextView text2;

    int currentStep = 1;
    int pink;
    int green;
    int soft_pink;

    ViewInfo info;
    Handler handler = new Handler();

    public SoftTransitionsFragment() {
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_soft_transitions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        pink = getResources().getColor(R.color.accent_pink);
        soft_pink = getResources().getColor(R.color.accent_blue_50);
        green = getResources().getColor(R.color.green);
        currentStep = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateColor();
            }
        }, 500);
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                text2.setVisibility(View.VISIBLE);
                break;
            case 3:
                text2.performClick();
                break;
        }
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    /* Transition background and text color */
    private void animateColor() {
        ObjectAnimator background = ObjectAnimator.ofObject(container, "backgroundColor",
                                                            new ArgbEvaluator(), pink,
                                                            soft_pink).setDuration(500);
        ObjectAnimator textColor = ObjectAnimator.ofObject(text1, "textColor",
                                                           new ArgbEvaluator(), pink,
                                                           soft_pink).setDuration(500);
        ObjectAnimator elevation = ObjectAnimator.ofFloat(text1, "elevation", 0f, 4f).setDuration(800);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(background, textColor, elevation);
        set.start();
    }

    @OnClick(R.id.text2)
    public void onTextClick(View v) {
        info = new ViewInfo(v, 0);
        text1.animate().alpha(0).setDuration(200);
        handler.postDelayed(nextFragment, 200);
    }

    Runnable nextFragment = new Runnable() {
        @Override
        public void run() {
            Bundle args = new Bundle();
            args.putParcelable(ViewInfo.VIEW_INFO, info);

            getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.accent_pink_50)));
            ((MainActivity) getActivity()).nextFragment(args);
        }
    };

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (transit == 0) {
            return null;
        }

        //Target will be filled in by the framework
        return enter ? ObjectAnimator.ofFloat(null, new XFractionProperty(), 1f, 0f)
                       : null;
    }
}
