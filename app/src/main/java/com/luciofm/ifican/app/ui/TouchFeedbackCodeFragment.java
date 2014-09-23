package com.luciofm.ifican.app.ui;



import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.util.IOUtils;
import com.luciofm.ifican.app.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class TouchFeedbackCodeFragment extends BaseFragment {

    @InjectView(R.id.container2)
    View container;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.text3)
    TextView text3;
    @InjectView(R.id.text4)
    TextView text4;
    @InjectView(R.id.textSwitcher)
    TextSwitcher textSwitcher;

    Spanned code1;
    Spanned code2;


    private int currentStep;

    public TouchFeedbackCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        code1 = Html.fromHtml(IOUtils.readFile(getActivity(), "source/touch_anim.xml.html"));
        code2 = Html.fromHtml(IOUtils.readFile(getActivity(), "source/ripple_press.xml.html"));

        textSwitcher.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        textSwitcher.setOutAnimation(getActivity(), android.R.anim.slide_out_right);

        currentStep = 1;
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                text1.animate().scaleX(0.7f).scaleY(0.7f);
                container.setVisibility(View.VISIBLE);
                break;
            case 3:
                Utils.dispatchTouch(text2, 300);
                break;
            case 4:
                Utils.dispatchTouch(text3, 300);
                break;
            case 5:
                Utils.dispatchTouch(text4, 300);
                break;
            case 6:
                Utils.dispatchTouch(text4, 900);
                break;
            case 7:
                container.setVisibility(View.GONE);
                textSwitcher.setVisibility(View.VISIBLE);
                textSwitcher.setText(code1);
                break;
            case 8:
                textSwitcher.setText(code2);
                break;
            default:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @Override
    public void onPrevPressed() {
        if (--currentStep > 1) {
            if (currentStep == 7) {
                textSwitcher.setText(code1);
                return;
            } else if (currentStep == 6) {
                textSwitcher.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
            }
            currentStep = 2;
            return;
        }
        super.onPrevPressed();
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    @OnClick({R.id.text2, R.id.text3, R.id.text4})
    public void onTextClick() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_touch_feedback_code;
    }

}
