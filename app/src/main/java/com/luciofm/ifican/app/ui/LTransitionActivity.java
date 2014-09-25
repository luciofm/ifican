package com.luciofm.ifican.app.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.transition.Transition;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseActivity;
import com.luciofm.ifican.app.IfICan;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.SimpleAnimatorListener;
import com.luciofm.ifican.app.anim.TransitionAdapter;
import com.luciofm.ifican.app.util.Dog;
import com.luciofm.ifican.app.util.IOUtils;
import com.luciofm.ifican.app.util.Utils;
import com.luciofm.ifican.app.util.ViewInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

public class LTransitionActivity extends Activity {

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
    private static final TimeInterpolator interpolator = new AnticipateOvershootInterpolator();
    private static final int ANIM_DURATION = 800;

    @InjectView(R.id.toplevel)
    ViewGroup topLevel;
    @InjectView(R.id.thumb)
    ImageView image;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.text3)
    TextView text3;
    @Optional
    @InjectView(R.id.text4)
    TextView text4;

    Dog dog;

    int currentStep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_transition);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        dog = bundle.getParcelable("DOG");

        image.setImageResource(dog.getResource());

        text1.setText(Html.fromHtml(IOUtils.readFile(this, "source/ltransition1.java.html")));
        text2.setText(Html.fromHtml(IOUtils.readFile(this, "source/ltransition2.java.html")));
        text3.setText(Html.fromHtml(IOUtils.readFile(this, "source/ltransition3.java.html")));
        if (text4 != null)
            text4.setText(Html.fromHtml(IOUtils.readFile(this, "source/ltransition4.java.html")));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateTextIn();
            }
        }, 600);

        getWindow().getEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                getWindow().getEnterTransition().removeListener(this);
            }
        });
    }

    public void animateTextIn() {
        final long duration = Utils.ANIM_DURATION;
        text1.setVisibility(View.VISIBLE);
        text1.setAlpha(0);
        text1.setTranslationY(text1.getHeight());
        text1.animate().setDuration(duration / 2)
                .translationY(0).alpha(1)
                .setInterpolator(sDecelerator);
    }

    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                onText1Click();
                break;
            case 3:
                onText2Click();
                break;
            case 4:
                onText3Click();
                break;
            /*case 5:
                onText4Click();
                break;*/
        }
    }

    @OnClick(R.id.text1)
    public void onText1Click() {
        final long duration = (long) (ANIM_DURATION);

        animateText(text1, text2);
    }

    @OnClick(R.id.text2)
    public void onText2Click() {
        final long duration = (long) (ANIM_DURATION);

        animateText(text2, text3);
    }

    @OnClick(R.id.text3)
    public void onText3Click() {
        //animateText(text3, text4);
        setResult(RESULT_OK);
        finishAfterTransition();
    }

    private void animateText(final View oldView, final View newView) {
        final long duration = (long) (ANIM_DURATION);

        newView.setAlpha(0);
        newView.setVisibility(View.VISIBLE);

        oldView.animate().translationY(oldView.getHeight())
                .setDuration(duration / 2).alpha(0)
                .setInterpolator(sDecelerator)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        newView.setTranslationY(newView.getHeight());
                        newView.animate().setDuration(duration / 2)
                                .translationY(0).alpha(1)
                                .setInterpolator(sDecelerator);
                        oldView.setVisibility(View.GONE);
                    }
                });
    }

    @Optional @OnClick(R.id.text4)
    public void onText4Click() {
        setResult(RESULT_OK);
        finishAfterTransition();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("IFICAN", "onKeyDown: " + keyCode + " - event: " + event);
        int scanCode = event.getScanCode();
        switch (scanCode) {
            case IfICan.BUTTON_NEXT:
            case 28:
            case 229:
            case 0x74:
                onNextPressed();
                return true;
            case IfICan.BUTTON_PREV:
            case 0x79:
            case 57:
                onBackPressed();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
