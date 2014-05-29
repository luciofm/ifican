package com.luciofm.ifican.app.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.anim.YFractionProperty;
import com.luciofm.ifican.app.util.Utils;
import com.luciofm.ifican.app.widget.SquareGridLayout;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class FeedbackFragment extends BaseFragment {

    @InjectView(R.id.container2)
    SquareGridLayout container2;
    //@InjectView(R.id.gif1)
    GifImageView gif1;
    //@InjectView(R.id.gif2)
    GifImageView gif2;
    //@InjectView(R.id.gif3)
    GifImageView gif3;
    //@InjectView(R.id.gif4)
    GifImageView gif4;
    @InjectView(R.id.gif5)
    GifImageView gif5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_feedback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);
        currentStep = 1;
        //text2.setText(Html.fromHtml(IOUtils.readFile(getActivity(), "source/touch_anim.xml.html")));
        return v;
    }

    @Override
    public void onNextPressed() {
        try {
            GifDrawable gifFromResource;
            GifImageView gif = new GifImageView(getActivity());
            gif.setOnClickListener(clickListener);
            ViewGroup.LayoutParams params = new SquareGridLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                              getResources().getDimensionPixelSize(R.dimen.grid_heiht));
            switch (++currentStep) {
                case 2:
                    container2.setVisibility(View.VISIBLE);
                    gifFromResource = new GifDrawable(getResources(), R.drawable.feedback1);
                    gif.setImageDrawable(gifFromResource);
                    gifFromResource.start();
                    gif1 = gif;
                    container2.addView(gif1, 0, params);
                    break;
                case 3:
                    gif2 = gif;
                    Utils.stopGif(gif1);
                    gifFromResource = new GifDrawable(getResources(), R.drawable.feedback2);
                    gif.setImageDrawable(gifFromResource);
                    gifFromResource.start();
                    container2.addView(gif2, 0, params);
                    break;
                case 4:
                    gif3 = gif;
                    Utils.stopGif(gif2);
                    gifFromResource = new GifDrawable(getResources(), R.drawable.feedback3);
                    gif.setImageDrawable(gifFromResource);
                    gifFromResource.start();
                    container2.addView(gif3, 0, params);
                    break;
                case 5:
                    gif4 = gif;
                    Utils.stopGif(gif3);
                    gifFromResource = new GifDrawable(getResources(), R.drawable.feedback4);
                    gif.setImageDrawable(gifFromResource);
                    gifFromResource.start();
                    container2.addView(gif4, 0, params);
                    break;
                case 6:
                    container2.setVisibility(View.GONE);
                    gif5.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    container2.removeAllViews();
                    ((MainActivity) getActivity()).nextFragment();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrevPressed() {
        if (--currentStep > 0) {
            int position = container2.getChildCount() - 1;
            GifImageView gif = null;
            if (position >= 0)
                gif = (GifImageView) container2.getChildAt(position);
            /* UGLY HACK to hide last example */
            if (currentStep == 5)
                position = 5;
            switch (position) {
                case 1:
                    Utils.stopGif(gif);
                    container2.setVisibility(View.VISIBLE);
                    container2.removeViewAt(0);
                    break;
                case 2:
                    Utils.stopGif(gif);
                    container2.removeViewAt(0);
                    Utils.startGif((GifImageView) container2.getChildAt(position - 1));
                    break;
                case 3:
                    Utils.stopGif(gif);
                    container2.removeViewAt(0);
                    Utils.startGif((GifImageView) container2.getChildAt(position - 1));
                    break;
                case 4:
                    Utils.stopGif(gif);
                    container2.removeViewAt(0);
                    Utils.startGif((GifImageView) container2.getChildAt(position - 1));
                    break;
                case 5:
                    gif5.setVisibility(View.GONE);
                    container2.setVisibility(View.VISIBLE);
                    Utils.startGif(gif);
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

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GifDrawable drawable = (GifDrawable) ((GifImageView) v).getDrawable();
            if (drawable.isPlaying())
                drawable.stop();
            else
                drawable.start();
        }
    };

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (transit == 0) {
            return null;
        }

        //Target will be filled in by the framework
        return enter ? ObjectAnimator.ofFloat(null, new XFractionProperty(), 1f, 0f)
                       : ObjectAnimator.ofFloat(null, new YFractionProperty(), 0f, -1f);
    }
}
