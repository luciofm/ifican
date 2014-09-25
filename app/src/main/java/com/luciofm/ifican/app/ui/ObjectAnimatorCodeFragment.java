package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Path;
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
import android.view.animation.PathInterpolator;
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
    @InjectView(R.id.container4)
    View container4;
    @InjectView(R.id.text1)
    TextView text1;
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

    @InjectView(R.id.buttonAnimate)
    Button animate;

    /*@InjectView(R.id.buttonLinear)
    Button buttonLinear;*/
    /*@InjectView(R.id.buttonAccelerate)
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
    List<View> buttons;*/

    private int currentStep;

    private int margin;

    private int deltaX;
    private int deltaY;

    public ObjectAnimatorCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        margin = getResources().getDimensionPixelSize(R.dimen.padding_big);

        text4.setText(Html.fromHtml(IOUtils.readFile(getActivity(), "source/shake.java.html")));
        currentStep = 1;
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                text1.animate().scaleY(0.6f).scaleX(0.6f);
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
                container2.setVisibility(View.GONE);
                container3.setVisibility(View.VISIBLE);
                setupButton(animate, "Linear");
                break;
            case 5:
                Utils.dispatchTouch(animate);
                break;
            case 6:
                setupButton(animate, "Accelerate");
                break;
            case 7:
                Utils.dispatchTouch(animate);
                break;
            case 8:
                setupButton(animate, "Accelerate/Decelerate");
                break;
            case 9:
                Utils.dispatchTouch(animate);
                break;
            case 10:
                setupButton(animate, "Anticipate");
                break;
            case 11:
                Utils.dispatchTouch(animate);
                break;
            case 12:
                setupButton(animate, "Overshoot");
                break;
            case 13:
                Utils.dispatchTouch(animate);
                break;
            case 14:
                setupButton(animate, "Bounce");
                break;
            case 15:
                Utils.dispatchTouch(animate);
                break;
            /*case 16:
                setupButton(animate, "Path");
                break;
            case 17:
                Utils.dispatchTouch(animate);
                break;*/
            case 16:
                animate.setVisibility(View.GONE);
                container3.setVisibility(View.GONE);
                container4.setVisibility(View.VISIBLE);
                break;
            case 17:
                Utils.dispatchTouch(button);
                break;
            case 18:
                button.setVisibility(View.GONE);
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
                animate.setVisibility(View.GONE);
                image2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                setupButton(animate, "Linear");
                currentStep = 4;
                return;
            }
        }
        super.onPrevPressed();
    }

    private void setupButton(final Button button, String title) {
        button.setTranslationX(0f);
        button.setTranslationY(0f);
        button.setText(title);
        button.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                button.getViewTreeObserver().removeOnPreDrawListener(this);

                deltaX = (container2.getWidth() - button.getWidth() - (margin * 2));
                deltaY = (container2.getHeight() - button.getHeight() - (margin * 2)) * -1;

                return false;
            }
        });
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

    /*@OnClick({R.id.buttonLinear, R.id.buttonAccelerate, R.id.buttonAccelerateDecelerate,
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
    }*/

    @OnClick(R.id.buttonAnimate)
    public void onAnimateClick(Button view) {
        String title = (String) view.getText();
        Interpolator interpolator = null;

        if (title.contentEquals("Linear"))
            interpolator = new LinearInterpolator();
        else if (title.contentEquals("Accelerate"))
            interpolator = new AccelerateInterpolator(2);
        else if (title.contentEquals("Accelerate/Decelerate"))
            interpolator = new AccelerateDecelerateInterpolator();
        else if (title.contentEquals("Anticipate"))
            interpolator = new AnticipateInterpolator(1f);
        else if (title.contentEquals("Overshoot"))
            interpolator = new OvershootInterpolator(1.0f);
        else if (title.contentEquals("Bounce"))
            interpolator = new BounceInterpolator();
        else if (title.contentEquals("Path")) {
            Path path = new Path();
            path.lineTo(0.25f, 0.25f);
            path.moveTo(0.25f, 0.5f);
            path.lineTo(1f, 1f);
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
            animator.setDuration(800).start();
            return;
        }

        view.animate().translationX(deltaX).translationY(deltaY)
                .setInterpolator(interpolator).setDuration(800);
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
