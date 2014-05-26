package com.luciofm.ifican.app.ui;



import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.luciofm.ifican.app.BaseFragment;
import com.luciofm.ifican.app.R;
import com.luciofm.ifican.app.anim.XFractionProperty;
import com.luciofm.ifican.app.anim.YFractionProperty;
import com.luciofm.ifican.app.util.Dog;
import com.luciofm.ifican.app.util.ViewInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class EleventhFragment extends BaseFragment {

    @InjectView(R.id.grid)
    GridView grid;

    ArrayList<Dog> dogs;

    private int currentStep;

    public EleventhFragment() {
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
        return R.layout.fragment_eleventh;
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
        return v;
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Dog dog = (Dog) view.getTag();
            ViewInfo info = new ViewInfo(view);

            Intent intent = new Intent(getActivity(), TransitionActivity.class);
            intent.putExtra("DOG", dog);
            intent.putExtra("INFO", info);
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
        }
    };

    @OnClick(R.id.container)
    public void onClick() {
        switch (++currentStep) {
            case 2:
                break;
        }
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

            return v;
        }
    }
}
