package com.luciofm.ifican.app.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public int getLayout() {
        return R.layout.fragment_minsdk;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        text1.setText(Html.fromHtml(IOUtils.readFile(getActivity(), "source/build.gradle.html")));

        return v;
    }

    @Override
    public void onNextPressed() {
        getActivity().finish();
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }
}
