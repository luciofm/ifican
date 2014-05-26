package com.luciofm.ifican.app.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.XFractionProperty;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by luciofm on 5/23/14.
 */
public class FirstFragment extends BaseFragment {

    @InjectView(R.id.text2)
    TextView text2;

    int currentStep;

    public FirstFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_first;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);
        currentStep = 1;
        return v;
    }

    @OnClick(R.id.container)
    public void onClick() {
        // Simple animateLayoutChanges transition...
        if (++currentStep == 2)
            text2.setVisibility(View.VISIBLE);
        else {
            ((MainActivity) getActivity()).nextFragment();
        }
    }
}
