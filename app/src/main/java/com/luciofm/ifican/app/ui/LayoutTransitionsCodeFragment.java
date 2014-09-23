package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.anim.YFractionProperty;
import com.luciofm.ifican.app.util.IOUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LayoutTransitionsCodeFragment extends BaseFragment {

    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.textSwitcher)
    TextSwitcher textSwitcher;

    private int currentStep;

    Spanned code1;
    Spanned code2;

    public LayoutTransitionsCodeFragment() {
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_layout_transitions_code;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        currentStep = 1;
        code1 = Html.fromHtml(IOUtils.readFile(getActivity(), "source/transitions.xml.html"));
        code2 = Html.fromHtml(IOUtils.readFile(getActivity(), "source/transitions2.xml.html"));

        textSwitcher.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        textSwitcher.setOutAnimation(getActivity(), android.R.anim.slide_out_right);

        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                text1.animate().scaleY(0.7f).scaleX(0.7f);
                textSwitcher.setVisibility(View.VISIBLE);
                textSwitcher.setText(code1);
                break;
            case 3:
                textSwitcher.setText(code2);
                break;
            default:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @Override
    public void onPrevPressed() {
        if (--currentStep > 0) {
            switch (currentStep) {
                case 1:
                    text1.animate().scaleX(1f).scaleY(1f);
                    textSwitcher.setVisibility(View.GONE);
                    break;
                case 2:
                    textSwitcher.setText(code1);
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
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (transit == 0) {
            return null;
        }

        //Target will be filled in by the framework
        return enter ? ObjectAnimator.ofFloat(null, new XFractionProperty(), 1f, 0f)
                       : ObjectAnimator.ofFloat(null, new YFractionProperty(), 0f, -1f);
    }
}
