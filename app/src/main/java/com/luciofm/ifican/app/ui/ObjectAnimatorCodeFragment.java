package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.AnimUtils;
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

    private int currentStep;

    public ObjectAnimatorCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

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
                container2.setVisibility(View.GONE);
                container3.setVisibility(View.VISIBLE);
                break;
            case 5:
                Utils.dispatchTouch(button);
                break;
            case 6:
                text4.setVisibility(View.VISIBLE);
                break;
            default:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    @OnClick(R.id.button)
    public void onButtonClick(View v) {
        AnimUtils.shakeView(v);
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
