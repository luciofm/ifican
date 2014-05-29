package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.AnimUtils;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.util.IOUtils;
import com.luciofm.ifican.app.util.Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class ObjectAnimatorCodeFragment extends BaseFragment {

    @InjectView(R.id.container2)
    View container2;
    @InjectView(R.id.container3)
    View container3;
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.text3)
    TextView text3;
    @InjectView(R.id.text4)
    TextView text4;
    @InjectView(R.id.image1)
    ImageView image1;
    @InjectView(R.id.image2)
    ImageView image2;
    @InjectView(R.id.button)
    Button button;

    @InjectView(R.id.buttonLinear)
    Button buttonLinear;
    @InjectView(R.id.buttonAccelerate)
    Button buttonAccelerate;
    @InjectView(R.id.buttonAccelerateDecelerate)
    Button buttonAccDec;
    @InjectView(R.id.buttonAnticipate)
    Button buttonAnticipate;
    @InjectView(R.id.buttonOvershoot)
    Button buttonOvershoot;
    @InjectView(R.id.buttonBounce)
    Button buttonBounce;

    @InjectViews({R.id.buttonLinear, R.id.buttonAccelerate, R.id.buttonAccelerateDecelerate,
                         R.id.buttonAnticipate, R.id.buttonOvershoot, R.id.buttonBounce})
    List<View> buttons;

    private int currentStep;

    private int margin;

    public ObjectAnimatorCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        margin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);

        text4.setText(Html.fromHtml(IOUtils.readFile(getActivity(), "source/shake.java.html")));
        currentStep = 1;
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                container2.setVisibility(View.VISIBLE);
                image1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                break;
            case 3:
                image1.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                image2.setVisibility(View.VISIBLE);
                text3.setVisibility(View.VISIBLE);
                break;
            case 4:
                image2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                setupButton(null, buttonLinear);
                break;
            case 5:
                Utils.dispatchTouch(buttonLinear);
                break;
            case 6:
                setupButton(buttonLinear, buttonAccelerate);
                break;
            case 7:
                Utils.dispatchTouch(buttonAccelerate);
                break;
            case 8:
                setupButton(buttonAccelerate, buttonAccDec);
                break;
            case 9:
                Utils.dispatchTouch(buttonAccDec);
                break;
            case 10:
                setupButton(buttonAccDec, buttonAnticipate);
                break;
            case 11:
                Utils.dispatchTouch(buttonAnticipate);
                break;
            case 12:
                setupButton(buttonAnticipate, buttonOvershoot);
                break;
            case 13:
                Utils.dispatchTouch(buttonOvershoot);
                break;
            case 14:
                setupButton(buttonOvershoot, buttonBounce);
                break;
            case 15:
                Utils.dispatchTouch(buttonBounce);
                break;
            case 16:
                buttonBounce.setVisibility(View.GONE);
                container2.setVisibility(View.GONE);
                container3.setVisibility(View.VISIBLE);
                break;
            case 17:
                Utils.dispatchTouch(button);
                break;
            case 18:
                text4.setVisibility(View.VISIBLE);
                break;
            default:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @Override
    public void onPrevPressed() {
        if (--currentStep > 0) {
            if (currentStep <= 14 && currentStep >= 3) {
                ButterKnife.apply(buttons, new ButterKnife.Action<View>() {
                    @Override
                    public void apply(View view, int i) {
                        view.setVisibility(View.GONE);
                    }
                });
                image2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                setupButton(null, buttonLinear);
                currentStep = 4;
                return;
            }
        }
        super.onPrevPressed();
    }

    private void setupButton(Button old, final Button button) {
        if (old != null)
            old.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
        button.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                button.getViewTreeObserver().removeOnPreDrawListener(this);

                int delta = (container2.getWidth() - button.getWidth() - margin - margin) * -1;
                button.setTranslationX(delta);

                return false;
            }
        });
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    @OnClick(R.id.button)
    public void onButtonClick(View v) {
        AnimUtils.shakeView(v);
    }

    @OnClick({R.id.buttonLinear, R.id.buttonAccelerate, R.id.buttonAccelerateDecelerate,
                     R.id.buttonAnticipate, R.id.buttonOvershoot, R.id.buttonBounce})
    public void onButtonAnimClick(Button button) {
        Interpolator interpolator = null;
        switch (button.getId()) {
            case R.id.buttonLinear:
                interpolator = new LinearInterpolator();
                break;
            case R.id.buttonAccelerate:
                interpolator = new AccelerateInterpolator(2);
                break;
            case R.id.buttonAccelerateDecelerate:
                interpolator = new AccelerateDecelerateInterpolator();
                break;
            case R.id.buttonAnticipate:
                interpolator = new AnticipateInterpolator(1.0f);
                break;
            case R.id.buttonOvershoot:
                interpolator = new OvershootInterpolator(1.0f);
                break;
            case R.id.buttonBounce:
                interpolator = new BounceInterpolator();
                break;
        }

        button.animate().translationX(0).setInterpolator(interpolator).setDuration(2000);
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_object_animator_code;
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
