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

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WhyFragment extends BaseFragment {

    public WhyFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_why;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onNextPressed() {
        ((MainActivity) getActivity()).nextFragment();
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
        if (enter)
            return super.onCreateAnimator(transit, enter, nextAnim);
        else return ObjectAnimator.ofFloat(null, new XFractionProperty(), 0f, -1f);
    }
}
