package com.example.peter.arfood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.peter.arfood.R;
import com.example.peter.arfood.RecommendAdapter;
import com.example.peter.arfood.RestClient;
import com.example.peter.arfood.models.Recommend;

import java.util.ArrayList;
import java.util.List;

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
    List<Recommend> recommendResults = new ArrayList<>();
    boolean isDownloadDone = false;
    RestClient restClient = RestClient.getInstance();

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

        RestClient.RecommendResultReadyCallback callback = new RestClient.RecommendResultReadyCallback() {
            @Override
            public void recommendResultReady(List<Recommend> recommends) {
                for(Recommend recommend: recommends) {
                    recommendResults.add(recommend);
                }
                Log.d("recommend: ", String.valueOf(recommendResults));
                isDownloadDone = true;
            }

        };
        restClient.setCallback(callback);

        setViewText(view1, view2, view3, view4, view5);

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





//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("position", String.valueOf(position));
//            }
//
//        });

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

    private void setViewText(View view1, View view2, View view3, View view4, View view5){
        TextView name1 = (TextView) view1.findViewById(R.id.name);
        name1.setText(recommendResults.get(0).getName());
        TextView name2 = (TextView) view2.findViewById(R.id.name);
        name2.setText(recommendResults.get(1).getName());
        TextView name3 = (TextView) view3.findViewById(R.id.name);
        name3.setText(recommendResults.get(2).getName());
        TextView name4 = (TextView) view4.findViewById(R.id.name);
        name4.setText(recommendResults.get(3).getName());
        TextView name5 = (TextView) view5.findViewById(R.id.name);
        name5.setText(recommendResults.get(4).getName());

        TextView address1 = (TextView) view1.findViewById(R.id.address);
        address1.setText(recommendResults.get(0).getAddress());
        TextView address2 = (TextView) view2.findViewById(R.id.address);
        address2.setText(recommendResults.get(1).getAddress());
        TextView address3 = (TextView) view3.findViewById(R.id.address);
        address3.setText(recommendResults.get(2).getAddress());
        TextView address4 = (TextView) view4.findViewById(R.id.address);
        address4.setText(recommendResults.get(3).getAddress());
        TextView address5 = (TextView) view5.findViewById(R.id.address);
        address5.setText(recommendResults.get(4).getAddress());

        TextView phone1 = (TextView) view1.findViewById(R.id.phone);
        phone1.setText(recommendResults.get(0).getPhone());
        TextView phone2 = (TextView) view2.findViewById(R.id.phone);
        phone2.setText(recommendResults.get(1).getPhone());
        TextView phone3 = (TextView) view3.findViewById(R.id.phone);
        phone3.setText(recommendResults.get(2).getPhone());
        TextView phone4 = (TextView) view4.findViewById(R.id.phone);
        phone4.setText(recommendResults.get(3).getPhone());
        TextView phone5 = (TextView) view5.findViewById(R.id.phone);
        phone5.setText(recommendResults.get(4).getPhone());

        TextView type1 = (TextView) view1.findViewById(R.id.type);
        type1.setText(recommendResults.get(0).getType());
        TextView type2 = (TextView) view2.findViewById(R.id.type);
        type2.setText(recommendResults.get(1).getType());
        TextView type3 = (TextView) view3.findViewById(R.id.type);
        type3.setText(recommendResults.get(2).getType());
        TextView type4 = (TextView) view4.findViewById(R.id.type);
        type4.setText(recommendResults.get(3).getType());
        TextView type5 = (TextView) view5.findViewById(R.id.type);
        type5.setText(recommendResults.get(4).getType());

        TextView open1 = (TextView) view1.findViewById(R.id.opening_hours);
        open1.setText(recommendResults.get(0).getOpening_hours());
        TextView open2 = (TextView) view2.findViewById(R.id.opening_hours);
        open2.setText(recommendResults.get(1).getOpening_hours());
        TextView open3 = (TextView) view3.findViewById(R.id.opening_hours);
        open3.setText(recommendResults.get(2).getOpening_hours());
        TextView open4 = (TextView) view4.findViewById(R.id.opening_hours);
        open4.setText(recommendResults.get(3).getOpening_hours());
        TextView open5 = (TextView) view5.findViewById(R.id.opening_hours);
        open5.setText(recommendResults.get(4).getOpening_hours());


    }

}