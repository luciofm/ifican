package com.luciofm.ifican.app.ui;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.AnimUtils;
import com.luciofm.ifican.app.anim.LayerEnablingAnimatorListener;
import com.luciofm.ifican.app.anim.SimpleAnimatorListener;
import com.luciofm.ifican.app.util.AlphaSpan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class QuestionsFragment extends BaseFragment {

    @InjectView(R.id.container)
    ViewGroup container;
    @InjectView(R.id.container2)
    ViewGroup container2;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.text3)
    TextView text3;
    @InjectView(R.id.text4)
    TextView text4;

    AnimatorSet fireworks;

    final private static float PROGRESS_TO_PIXELIZATION_FACTOR = 1000.0f;

    @InjectView(R.id.container3)
    ViewGroup container3;

    @InjectView(R.id.image)
    ImageView image;

    @InjectView(R.id.imageMuambator1)
    ImageView imageMuambator1;
    @InjectView(R.id.imageMuambator2)
    ImageView imageMuambator2;

    @InjectView(R.id.imageWhi1)
    ImageView imageWhi1;
    @InjectView(R.id.imageWhi2)
    ImageView imageWhi2;

    Bitmap originalBitmap;
    private int pink;
    private int yellow;
    private int green;

    private SpannableString title = new SpannableString("PERGUNTAS?");

    public QuestionsFragment() {
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_questions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        container2.setVisibility(View.GONE);

        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.me);
        pink = getResources().getColor(R.color.pink);
        yellow = getResources().getColor(R.color.yellow);
        green = getResources().getColor(R.color.green);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateTitle();
            }
        }, 800);

        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                if (fireworks != null)
                    fireworks.cancel();
                animateOut();
                break;
            case 3:
                ObjectAnimator background = ObjectAnimator.ofObject(container, "backgroundColor",
                                                                    new ArgbEvaluator(), green,
                                                                    pink).setDuration(300);
                background.start();
                container3.animate().alpha(0f).setDuration(300);
                imageMuambator2.animate().alpha(0f).setDuration(300);
                imageWhi2.animate().alpha(0f).setDuration(300);
                image.setVisibility(View.VISIBLE);
                image.animate().alpha(1f).setDuration(300);
                ObjectAnimator pixalate = ObjectAnimator.ofInt(this, "pixalateFactor", 100, 0);
                pixalate.setDuration(1600);
                pixalate.start();
                break;
            case 4:
                text2.setVisibility(View.VISIBLE);
                break;
            case 5:
                text3.setVisibility(View.VISIBLE);
                break;
            case 6:
                text4.setVisibility(View.VISIBLE);
                break;
            case 7:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    private void animateTitle() {
        buildFireworksAnimation(0, title.length() - 1);
    }

    private static final int FIREWORK_ANIM_DURATION = 1500;
    private static final int FIREWORK_ANIM_DELAY = 240;
    private int currentDelay = 0;

    private void buildFireworksAnimation(int start, int end) {
        ArrayList<AlphaSpan> spans = new ArrayList<>();
        for(int index = start ; index <= end ; index++) {
            AlphaSpan span = new AlphaSpan(0);
            spans.add(span);
            title.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        Collections.shuffle(spans);

        List<Animator> animators = new ArrayList<>();
        for (AlphaSpan span : spans) {
            ObjectAnimator anim = ObjectAnimator.ofInt(span, ALPHA_SPAN_PROPERTY, 0, 255);
            anim.setDuration(FIREWORK_ANIM_DURATION);
            anim.setStartDelay(currentDelay);
            animators.add(anim);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (isResumed())
                        text1.setText(title);
                }
            });
            currentDelay += FIREWORK_ANIM_DELAY;
        }

        fireworks = new AnimatorSet();
        fireworks.playTogether(animators);
        fireworks.start();
    }

    private static final Property<AlphaSpan, Integer> ALPHA_SPAN_PROPERTY =
            new Property<AlphaSpan, Integer>(Integer.class, "ALPHA_SPAN_PROPERTY") {

                @Override
                public void set(AlphaSpan span, Integer value) {
                    span.setAlpha(value);
                }

                @Override
                public Integer get(AlphaSpan span) {
                    return span.getAlpha();
                }
            };

    private void animateOut() {
        text1.setVisibility(View.GONE);
        container2.setVisibility(View.VISIBLE);

        container2.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                container2.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewPropertyAnimator animator = animateViewsOut();
                animator.setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        container2.setVisibility(View.GONE);
                        animateAlpha();
                    }
                });
                animator.start();

                container3.animate().alpha(1f).setDuration(300).setStartDelay(600).start();
                ObjectAnimator background = ObjectAnimator.ofObject(container, "backgroundColor",
                                                                    new ArgbEvaluator(), yellow,
                                                                    green);
                background.setDuration(300);
                background.setStartDelay(600);
                background.start();

                return false;
            }
        });
    }

    private void animateAlpha() {
        imageMuambator1.animate().alpha(0f).setDuration(1000).start();
        imageMuambator2.animate().alpha(1f).setDuration(1000).start();
        imageWhi1.animate().alpha(0f).setDuration(1000).start();
        imageWhi2.animate().alpha(1f).setDuration(1000).start();
    }

    public void setPixalateFactor(int number) {
        float factor = number / PROGRESS_TO_PIXELIZATION_FACTOR;

        PixelizeImageAsyncTask asyncPixelateTask = new PixelizeImageAsyncTask();
        asyncPixelateTask.execute(factor, originalBitmap);
    }


    /**
     * Implementation of the AsyncTask class showing how to run the
     * pixelization algorithm in the background, and retrieving the
     * pixelated image from the resulting operation.
     */
    private class PixelizeImageAsyncTask extends AsyncTask<Object, Void, BitmapDrawable> {

        @Override
        protected BitmapDrawable doInBackground(Object... params) {
            float pixelizationFactor = (Float)params[0];
            Bitmap originalBitmap = (Bitmap)params[1];
            return AnimUtils.builtInPixelization(getActivity(), pixelizationFactor, originalBitmap);
        }

        @Override
        protected void onPostExecute(BitmapDrawable result) {
            if (isResumed())
                image.setImageDrawable(result);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    public static final int ANIM_DELAY = 30;
    public static final int MULTIPLIER = 1;
    public static final int ANIM_DURATION = 900;
    AccelerateInterpolator accelerate = new AccelerateInterpolator();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public ViewPropertyAnimator animateViewsOut() {
        ArrayList<View> views = new ArrayList<>();
        ViewPropertyAnimator anim = null;
        for (int i = 0; i < container2.getChildCount(); i++) {
            View v = container2.getChildAt(i);
            views.add(v);
        }

        Collections.shuffle(views);

        for (int i = 0; i < views.size(); i++) {
            View v = views.get(i);
            v.setPivotY(v.getHeight() / 2);
            v.setPivotX(v.getWidth() / 2);
            anim = v.animate();
            anim.alpha(0f).scaleY(12f).scaleX(12f).setInterpolator(accelerate)
                    .setStartDelay(i * ANIM_DELAY * MULTIPLIER)
                    .setDuration((ANIM_DURATION - (i * ANIM_DELAY)) * MULTIPLIER)
                    .setListener(new LayerEnablingAnimatorListener(v));

            container.getOverlay().add(v);
        }
        return anim;
    }
}
