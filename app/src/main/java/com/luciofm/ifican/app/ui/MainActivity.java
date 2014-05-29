package com.luciofm.ifican.app.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.luciofm.ifican.app.BaseActivity;
import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.IfICan;
import com.luciofm.ifican.app.R;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    int index = 0;

    ArrayList<Class<? extends BaseFragment>> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        /*fragments.add(IfICanFragment.class);
        fragments.add(WhyFragment.class);
        fragments.add(SoftTransitionsFragment.class);
        fragments.add(TransitionsExampleFragment.class);
        fragments.add(ContextFragment.class);
        fragments.add(AtentionFragment.class);
        fragments.add(FeedbackFragment.class);
        fragments.add(CodeFragment.class);
        fragments.add(LayoutTransitionsCodeFragment.class);
        fragments.add(TouchFeedbackCodeFragment.class);
        fragments.add(AnimationDrawableCodeFragment.class);*/
        fragments.add(ObjectAnimatorCodeFragment.class);
        fragments.add(ViewPropertyAnimatorCodeFragment.class);
        fragments.add(MorphingButtonCodeFragment.class);
        fragments.add(ActivityTransitionsCodeFragment.class);
        fragments.add(QuestionsFragment.class);
        fragments.add(TheEndFragment.class);

        if (savedInstanceState == null)
            nextFragment(false);

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                index = getFragmentManager().getBackStackEntryCount() + 1;
            }
        });
    }

    public void nextFragment() {
        nextFragment(null);
    }

    public void nextFragment(boolean backstack) {
        nextFragment(backstack, null);
    }

    public void nextFragment(Bundle args) {
        nextFragment(true, args);
    }

    public void nextFragment(boolean backStack, Bundle args) {
        if (index == fragments.size())
            return;
        Class<? extends Fragment> clazz = fragments.get(index++);
        try {
            Fragment f = clazz.newInstance();
            if (args != null)
                f.setArguments(args);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentContainer, f, "current")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (backStack)
                ft.addToBackStack(null);
            ft.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("INDEX", index);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        index = savedInstanceState.getInt("INDEX", 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("IFICAN", "onKeyDown: " + keyCode + " - event: " + event);
        BaseFragment fragment = (BaseFragment) getFragmentManager().findFragmentByTag("current");
        if (keyCode == 0) {
            int scanCode = event.getScanCode();
            switch (scanCode) {
                case IfICan.BUTTON_NEXT:
                    fragment.onNextPressed();
                    break;
                case IfICan.BUTTON_PREV:
                    fragment.onPrevPressed();
                    break;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
