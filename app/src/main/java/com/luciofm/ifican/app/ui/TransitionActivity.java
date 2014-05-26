package com.luciofm.ifican.app.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseActivity;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.SimpleAnimatorListener;
import com.luciofm.ifican.app.util.Dog;
import com.luciofm.ifican.app.util.IOUtils;
import com.luciofm.ifican.app.util.ViewInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by luciofm on 5/25/14.
 */
public class TransitionActivity extends BaseActivity {

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
    private static final int ANIM_DURATION = 500;

    @InjectView(R.id.toplevel)
    FrameLayout topLevel;
    @InjectView(R.id.thumb)
    ImageView image;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.text3)
    TextView text3;

    Dog dog;
    ViewInfo info;

    private BitmapDrawable bitmapDrawable;
    private ColorMatrix colorizerMatrix = new ColorMatrix();
    ColorDrawable background;
    int leftDelta;
    int topDelta;
    float widthScale;
    float heightScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        info = bundle.getParcelable("INFO");
        dog = bundle.getParcelable("DOG");

        background = new ColorDrawable(getResources().getColor(R.color.yellow));
        topLevel.setBackground(background);
        image.setImageResource(dog.getResource());

        text1.setText(Html.fromHtml(IOUtils.readFile(this, "source/predraw.java.html")));
        text2.setText(Html.fromHtml(IOUtils.readFile(this, "source/init.java.html")));
        text3.setText(Html.fromHtml(IOUtils.readFile(this, "source/anim1.java.html")));

        if (savedInstanceState == null){
            image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    image.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    image.getLocationOnScreen(screenLocation);
                    leftDelta = info.left - screenLocation[0];
                    topDelta = info.top - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    widthScale = (float) info.width / image.getWidth();
                    heightScale = (float) info.height / image.getHeight();

                    runEnterAnimation();

                    return true;
                }
            });
        }
    }

    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        image.setPivotX(0);
        image.setPivotY(0);
        image.setScaleX(widthScale);
        image.setScaleY(heightScale);
        image.setTranslationX(leftDelta);
        image.setTranslationY(topDelta);

        // We'll fade the text in later
        text1.setAlpha(0);

        // Animate scale and translation to go from thumbnail to full size
        image.animate().setDuration(duration)
                .scaleX(1).scaleY(1)
                .translationX(0).translationY(0)
                .setInterpolator(sDecelerator)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        text1.setTranslationY(text1.getHeight());
                        text1.animate().setDuration(duration / 2)
                                .translationY(0).alpha(1)
                                .setInterpolator(sDecelerator);
                    }
                });
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(background, "alpha", 0, 255);
        bgAnim.setDuration(duration * 2);
        bgAnim.start();
    }

    @OnClick(R.id.text1)
    public void onText1Click() {
        final long duration = (long) (ANIM_DURATION);

        text2.setAlpha(0);
        text2.setVisibility(View.VISIBLE);

        text1.animate().translationY(text1.getHeight())
                .setDuration(duration / 2).alpha(0)
                .setInterpolator(sDecelerator)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        text2.setTranslationY(text2.getHeight());
                        text2.animate().setDuration(duration / 2)
                                .translationY(0).alpha(1)
                                .setInterpolator(sDecelerator);
                        text1.setVisibility(View.GONE);
                    }
                });
    }

    @OnClick(R.id.text2)
    public void onText2Click() {
        final long duration = (long) (ANIM_DURATION);

        text3.setAlpha(0);
        text3.setVisibility(View.VISIBLE);

        text2.animate().translationY(text2.getHeight())
                .setDuration(duration / 2).alpha(0)
                .setInterpolator(sDecelerator)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        text3.setTranslationY(text3.getHeight());
                        text3.animate().setDuration(duration / 2)
                                .translationY(0).alpha(1)
                                .setInterpolator(sDecelerator);
                        text2.setVisibility(View.GONE);
                    }
                });


    }

    @OnClick(R.id.text3)
    public void onText3Click() {
        animateOut(text3);
    }

    public void animateOut(View text) {
        final long duration = (long) (ANIM_DURATION);

        // First, slide/fade text out of the way
        text.animate().translationY(text.getHeight())
                .alpha(0).setDuration(duration / 2)
                .setInterpolator(sAccelerator)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        image.animate().setDuration(duration)
                                .scaleX(widthScale)
                                .scaleY(heightScale)
                                .translationX(leftDelta)
                                .translationY(topDelta)
                                .setListener(new SimpleAnimatorListener() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        finish();
                                    }
                                });
                        // Fade out background
                        ObjectAnimator bgAnim = ObjectAnimator.ofInt(background, "alpha", 0);
                        bgAnim.setDuration(duration);
                        bgAnim.start();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (text1.getVisibility() == View.VISIBLE)
            animateOut(text1);
        else if (text2.getVisibility() == View.VISIBLE)
            animateOut(text2);
        else
            animateOut(text3);
    }

    @Override
    public void finish() {
        super.finish();

        // override transitions to skip the standard window animations
        overridePendingTransition(0, 0);
    }
}
