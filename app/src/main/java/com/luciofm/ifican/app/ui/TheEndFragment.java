package com.luciofm.ifican.app.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class TheEndFragment extends BaseFragment {

    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.text2)
    TextView text2;

    @Override
    public int getLayout() {
        return R.layout.fragment_the_end;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                text1.setVisibility(View.GONE);
                text2.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }
}
