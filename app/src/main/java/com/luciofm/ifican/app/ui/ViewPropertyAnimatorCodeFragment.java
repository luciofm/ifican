package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.AnimUtils;
import com.luciofm.ifican.app.anim.SimpleAnimatorListener;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.anim.YFractionProperty;
import com.luciofm.ifican.app.util.IOUtils;
import com.luciofm.ifican.app.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class ViewPropertyAnimatorCodeFragment extends BaseFragment {

    @InjectView(R.id.container2)
    View container;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.text2)
    TextView text2;

    private int currentStep;

    public ViewPropertyAnimatorCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        text2.setText(Html.fromHtml(IOUtils.readFile(getActivity(), "source/vpanim.java.html")));

        currentStep = 1;
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                container.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        image.getViewTreeObserver().removeOnPreDrawListener(this);
                        image.setAlpha(0f);
                        image.setScaleX(0f);
                        image.setScaleY(0f);
                        image.setPivotX(image.getX() / 2);
                        image.setPivotY(image.getY() / 2);
                        image.animate().alpha(1f).scaleY(1f).scaleX(1f)
                                .rotation(720).setInterpolator(new AccelerateInterpolator())
                                .setDuration(1000).setListener(new SimpleAnimatorListener() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                image.setAlpha(1f);
                            }
                        });
                        return false;
                    }
                });
                break;
            case 3:
                text1.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                text2.setVisibility(View.VISIBLE);
                break;
            default:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @Override
    public void onPrevPressed() {
        if (--currentStep > 0) {
            switch (currentStep) {
                case 2:
                    image.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.GONE);
                    break;
                case 1:
                    image.animate().alpha(0f).scaleY(0f).scaleX(0f)
                            .rotation(0).setInterpolator(new AccelerateInterpolator())
                            .setDuration(1000).setListener(new SimpleAnimatorListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            container.setVisibility(View.GONE);
                        }
                    });
                    break;
            }
            return;
        }
        super.onPrevPressed();
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_view_property_animator_code;
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
