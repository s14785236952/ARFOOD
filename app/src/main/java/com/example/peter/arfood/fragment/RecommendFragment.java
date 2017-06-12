package com.example.peter.arfood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.peter.arfood.R;
import com.example.peter.arfood.RecommendAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {

    View v;
    private Button No1btn;
    private Button No2btn;
    private Button No3btn;
    private Button No4btn;

    private ViewPager viewPager;

    public RecommendFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_recommend,container,false);

        No1btn = (Button) v.findViewById(R.id.No1btn);
        No2btn = (Button) v.findViewById(R.id.No2btn);
        No3btn = (Button) v.findViewById(R.id.No3btn);
        No4btn = (Button) v.findViewById(R.id.No4btn);

        No1btn.setOnClickListener(No1btnOnClick);
        No2btn.setOnClickListener(No2btnOnClick);
        No3btn.setOnClickListener(No3btnOnClick);
        No4btn.setOnClickListener(No4btnOnClick);

        viewPager = (ViewPager) v.findViewById(R.id.page_pager);
        View view1 = (View) inflater.inflate(R.layout.recommend_list_item1, null);
        View view2 = (View) inflater.inflate(R.layout.recommend_list_item2, null);
        View view3 = (View) inflater.inflate(R.layout.recommend_list_item3, null);
        View view4 = (View) inflater.inflate(R.layout.recommend_list_item4, null);
        View view5 = (View) inflater.inflate(R.layout.recommend_list_item5, null);
        ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);

        viewPager.setAdapter(new RecommendAdapter(views));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                Button selectBtn = (Button) v.findViewById(R.id.select);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        viewPager.setCurrentItem(Integer.MAX_VALUE/2);//默认在中间，使用户看不到边界

        return v;
    }

    private View.OnClickListener No1btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener No2btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener No3btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener No4btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}