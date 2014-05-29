package com.luciofm.ifican.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by luciofm on 5/23/14.
 */
public abstract class BaseFragment extends Fragment {

    protected int currentStep = 1;

    public abstract int getLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentStep = 1;
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    public abstract void onNextPressed();

    public void onPrevPressed() {
        getActivity().onBackPressed();
    }
}
