package com.luciofm.ifican.app.ui;

import android.os.Bundle;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MinSdkFragment extends BaseFragment {

    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.textSwitcher)
    TextSwitcher textSwitcher;

    Spanned code1;
    Spanned code2;

    @Override
    public int getLayout() {
        return R.layout.fragment_minsdk;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        code1 = Html.fromHtml(IOUtils.readFile(getActivity(), "source/build.gradle.html"));
        code2 = Html.fromHtml(IOUtils.readFile(getActivity(), "source/build2.gradle.html"));

        textSwitcher.setText(code1);
        textSwitcher.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        textSwitcher.setOutAnimation(getActivity(), android.R.anim.slide_out_right);


        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                textSwitcher.setText(code2);
                break;
            case 3:
                getActivity().finish();
                break;
        }
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }
}
