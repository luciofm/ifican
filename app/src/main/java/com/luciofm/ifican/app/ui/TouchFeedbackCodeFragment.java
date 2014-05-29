package com.luciofm.ifican.app.ui;



import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.text3)
    TextView text3;
    @InjectView(R.id.text4)
    TextView text4;

    private int currentStep;

    public TouchFeedbackCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        text4.setText(Html.fromHtml(IOUtils.readFile(getActivity(), "source/touch_anim.xml.html")));
        currentStep = 1;
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                container.setVisibility(View.VISIBLE);
                break;
            case 3:
                Utils.dispatchTouch(text2, 300);
                break;
            case 4:
                Utils.dispatchTouch(text3, 300);
                break;
            case 5:
                text2.setVisibility(View.GONE);
                text3.setVisibility(View.GONE);
                text4.setVisibility(View.VISIBLE);
                break;
            default:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @Override
    public void onPrevPressed() {
        if (--currentStep > 1) {
            if (currentStep == 4) {
                text2.setVisibility(View.VISIBLE);
                text3.setVisibility(View.VISIBLE);
                text4.setVisibility(View.GONE);
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

    @OnClick({R.id.text2, R.id.text3})
    public void onTextClick() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_touch_feedback_code;
    }

}
