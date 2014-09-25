package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Outline;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.util.IOUtils;
import com.luciofm.ifican.app.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class RevealCodeFragment extends BaseFragment {

    @InjectView(R.id.info)
    View info;
    @InjectView(R.id.text2)
    TextView text2;

    Spanned code1;

    private int currentStep;

    public RevealCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        setOutlines(info);

        code1 = Html.fromHtml(IOUtils.readFile(getActivity(), "source/reveal.java.html"));
        text2.setText(code1);

        currentStep = 1;
        return v;
    }

    private void setOutlines(View v) {
        int size = getResources().getDimensionPixelSize(R.dimen.floating_button_size);

        Outline outline = new Outline();
        outline.setOval(0, 0, size, size);

        v.setOutline(outline);
        v.animate().alpha(1.0f);
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                Utils.dispatchTouch(info, 300);
                break;
            default:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @Override
    public void onPrevPressed() {
        if (--currentStep == 1) {
            Utils.dispatchTouch(info, 300);
            return;
        }
        super.onPrevPressed();
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    @OnClick(R.id.info)
    public void onInfoClick(View v) {
        toggleCodeView(v);
    }

    private void toggleCodeView(View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        float radius = text2.getWidth();

        if (text2.getVisibility() == View.INVISIBLE) {
            text2.setVisibility(View.VISIBLE);
            ViewAnimationUtils.createCircularReveal(text2, cx, cy, 0, radius).start();
        } else {
            ValueAnimator reveal = ViewAnimationUtils.createCircularReveal(
                    text2, cx, cy, radius, 0);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    text2.setVisibility(View.INVISIBLE);
                }
            });
            reveal.start();
        }
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

    @Override
    public int getLayout() {
        return R.layout.fragment_reveal_code;
    }
}
