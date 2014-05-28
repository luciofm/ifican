package com.luciofm.ifican.app.ui;



import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.AnimUtils;
import com.luciofm.ifican.app.util.IOUtils;
import com.luciofm.ifican.app.util.Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class MorphingButtonCodeFragment extends BaseFragment {

    @InjectView(R.id.container)
    ViewGroup root;
    @InjectView(R.id.container2)
    ViewGroup container2;
    @InjectView(R.id.container3)
    ViewGroup container3;

    @InjectView(R.id.reg_container)
    View reg_container;
    @InjectView(R.id.login_container)
    View login_container;
    @InjectView(R.id.buttonReg1)
    Button buttonReg;
    @InjectView(R.id.buttonLog1)
    Button buttonLog;

    @InjectViews({R.id.editReg1, R.id.editReg2, R.id.editReg3, R.id.buttonReg1})
    List<View> register;

    @InjectViews({R.id.editLog1, R.id.editLog2, R.id.buttonLog1})
    List<View> login;

    @InjectView(R.id.text2)
    TextView text2;

    private int currentStep;

    private boolean registerOpened = false;
    private boolean loginOpened = false;

    public MorphingButtonCodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        text2.setText(Html.fromHtml(IOUtils.readFile(getActivity(), "source/tm.java.html")));
        currentStep = 1;
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                AnimUtils.beginDelayedTransition(root);
                container2.setVisibility(View.VISIBLE);
                break;
            case 3:
                Utils.dispatchTouch(login_container);
                break;
            case 4:
                Utils.dispatchTouch(reg_container);
                break;
            case 5:
                Utils.dispatchTouch(buttonReg);
                break;
            case 6:
                Utils.dispatchTouch(login_container);
                break;
            case 7:
                AnimUtils.beginDelayedTransition(root);
                container3.setVisibility(View.GONE);
                text2.setVisibility(View.VISIBLE);
                break;
            default:
                ((MainActivity) getActivity()).nextFragment();
        }
    }

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.textRegister)
    public void onRegClick() {
        if (loginOpened)
            onButtonLogClick();
        registerOpened = true;
        reg_container.setClickable(false);
        AnimUtils.beginDelayedTransition(container3);
        ButterKnife.apply(register, new ButterKnife.Action<View>() {
            @Override
            public void apply(View view, int i) {
                view.setVisibility(View.VISIBLE);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.buttonReg1)
    public void onButtonRegClick() {
        registerOpened = false;
        reg_container.setClickable(true);
        AnimUtils.beginDelayedTransition(container3);
        ButterKnife.apply(register, new ButterKnife.Action<View>() {
            @Override
            public void apply(View view, int i) {
                view.setVisibility(View.GONE);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.textLogin)
    public void onLogClick() {
        if (registerOpened)
            onButtonRegClick();
        login_container.setClickable(false);
        loginOpened = true;
        AnimUtils.beginDelayedTransition(container3);
        ButterKnife.apply(login, new ButterKnife.Action<View>() {
            @Override
            public void apply(View view, int i) {
                view.setVisibility(View.VISIBLE);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.buttonLog1)
    public void onButtonLogClick() {
        loginOpened = false;
        login_container.setClickable(true);
        AnimUtils.beginDelayedTransition(container3);
        ButterKnife.apply(login, new ButterKnife.Action<View>() {
            @Override
            public void apply(View view, int i) {
                view.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_morphing_button_code;
    }

}
