package com.luciofm.ifican.app.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.luciofm.ifican.app.BaseActivity;
import com.luciofm.ifican.app.R;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    int index = 0;

    ArrayList<Class<? extends Fragment>> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        fragments.add(FirstFragment.class);
        fragments.add(SecondFragment.class);
        fragments.add(ThirdFragment.class);
        fragments.add(FourthFragment.class);
        fragments.add(FifthFragment.class);
        fragments.add(SixthFragment.class);
        fragments.add(SeventhFragment.class);
        fragments.add(CodeFragment.class);
        fragments.add(NinthFragment.class);
        fragments.add(TenthFragment.class);
        fragments.add(EleventhFragment.class);

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
            ft.replace(R.id.fragmentContainer, f)
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
}
