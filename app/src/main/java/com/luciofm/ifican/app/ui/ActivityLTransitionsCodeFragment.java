package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.IfICan;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.anim.YFractionProperty;
import com.luciofm.ifican.app.util.ActivityFinishEvent;
import com.luciofm.ifican.app.util.Dog;
import com.luciofm.ifican.app.util.Utils;
import com.luciofm.ifican.app.util.ViewInfo;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class ActivityLTransitionsCodeFragment extends BaseFragment {

    private static final int REQUEST_CODE = 666;
    @InjectView(R.id.container)
    ViewGroup container;
    @InjectView(R.id.grid)
    GridView grid;
    @InjectView(R.id.text1)
    TextView text1;

    ArrayList<Dog> dogs;

    private int currentStep;

    boolean override;

    public ActivityLTransitionsCodeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dogs = new ArrayList<>();
        dogs.add(new Dog(R.drawable.dog1, "dog1"));
        dogs.add(new Dog(R.drawable.dog2, "dog2"));
        dogs.add(new Dog(R.drawable.dog3, "dog3"));
        dogs.add(new Dog(R.drawable.dog6, "dog6"));
        dogs.add(new Dog(R.drawable.dog7, "dog7"));
        dogs.add(new Dog(R.drawable.dog8, "dog8"));
        dogs.add(new Dog(R.drawable.dog9, "dog9"));
        dogs.add(new Dog(R.drawable.dog10, "dog10"));
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_activity_transitions_code;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, v);

        grid.setAdapter(new DogsAdapter(getActivity(), dogs));
        grid.setSelector(android.R.color.transparent);
        grid.setOnItemClickListener(clickListener);

        currentStep = 1;

        text1.setText("Activity Transitions - L");
        return v;
    }

    @Override
    public void onNextPressed() {
        switch (++currentStep) {
            case 2:
                int position = new Random().nextInt(grid.getChildCount());
                clickListener.onItemClick(grid, grid.getChildAt(position), position, 0);
                break;
            case 3:
                override = false;
                ((MainActivity) getActivity()).nextFragment();
                break;
        }
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (override == true) {
                override = false;
                ((MainActivity) getActivity()).nextFragment();
                return;
            }

            if (currentStep == 1)
                currentStep = 2;

            Dog dog = (Dog) view.getTag();

            Intent intent = new Intent(getActivity(), LTransitionActivity.class);
            intent.putExtra("DOG", dog);

            ImageView hero = (ImageView) ((ViewGroup) view).getChildAt(0);
            //grid.setTransitionGroup(false);
            container.setTransitionGroup(false);

            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(getActivity(), hero, "photo_hero");
            startActivityForResult(intent, REQUEST_CODE, options.toBundle());
        }
    };

    @OnClick(R.id.container)
    public void onClick() {
        onNextPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Ok, we didn' went through all the steps in the TransitionActivity */
        if (requestCode == REQUEST_CODE) {
            override = false;
            if (resultCode != Activity.RESULT_OK)
                currentStep--;
            else if (resultCode == Activity.RESULT_OK)
                override = true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        if (transit == 0) {
            return null;
        }

        //Target will be filled in by the framework
        return enter ? ObjectAnimator.ofFloat(null, new XFractionProperty(), 1f, 0f)
                       : ObjectAnimator.ofFloat(null, new YFractionProperty(), 0f, -1f);
    }

    public class DogsAdapter extends ArrayAdapter<Dog> {

        LayoutInflater inflater;

        public DogsAdapter(Context context, List<Dog> objects) {
            super(context, 0, objects);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                v = inflater.inflate(R.layout.adapter_image, parent, false);
            }

            Dog dog = getItem(position);
            ImageView image = ButterKnife.findById(v, R.id.image);
            image.setImageResource(dog.getResource());
            v.setTag(dog);

            image.setViewName(dog.getName());

            return v;
        }
    }
}
