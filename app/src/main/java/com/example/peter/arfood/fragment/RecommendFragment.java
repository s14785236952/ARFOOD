package com.example.peter.arfood.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peter.arfood.R;
import com.example.peter.arfood.RecommendAdapter;
import com.example.peter.arfood.RestClient;
import com.example.peter.arfood.models.Recommend;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends Fragment {

    View v;
    private Button No1btn;
    private Button No2btn;
    private Button No3btn;
    private Button No4btn;
    View view1,view2,view3,view4,view5;
    private ViewPager viewPager;
    List<String> recommendResults_name = new ArrayList<>();
    List<String> recommendResults_address = new ArrayList<>();
    List<String> recommendResults_phone = new ArrayList<>();
    List<String> recommendResults_type = new ArrayList<>();
    List<String> recommendResults_opening_hours = new ArrayList<>();
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
        view1 = inflater.inflate(R.layout.recommend_list_item, null);
        view2 = inflater.inflate(R.layout.recommend_list_item, null);
        view3 = inflater.inflate(R.layout.recommend_list_item, null);
        view4 = inflater.inflate(R.layout.recommend_list_item, null);
        view5 = inflater.inflate(R.layout.recommend_list_item, null);

        RestClient.RecommendResultReadyCallback callback = new RestClient.RecommendResultReadyCallback() {
            @Override
            public void recommendResultReady(List<Recommend> recommends) {
                for(Recommend recommend: recommends) {
                    recommendResults_name.add(recommend.name);
                    recommendResults_address.add(recommend.address);
                    recommendResults_phone.add(recommend.phone);
                    recommendResults_type.add(recommend.type);
                    recommendResults_opening_hours.add(recommend.opening_hours);


                }
                setViewText(view1, view2, view3, view4, view5);
            }

        };

        setViewImage(view1, view2, view3, view4, view5);

        restClient.setCallback(callback);

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

    private void setViewImage(View view1, View view2, View view3, View view4, View view5){
        ImageView view1Image = (ImageView) view1.findViewById(R.id.imageView);
        view1Image.setImageResource(R.drawable.noodle);
        ImageView view2Image = (ImageView) view2.findViewById(R.id.imageView);
        view2Image.setImageResource(R.drawable.green);
        ImageView view3Image = (ImageView) view3.findViewById(R.id.imageView);
        view3Image.setImageResource(R.drawable.octopus);
        ImageView view4Image = (ImageView) view4.findViewById(R.id.imageView);
        view4Image.setImageResource(R.drawable.foodbox);
        ImageView view5Image = (ImageView) view5.findViewById(R.id.imageView);
        view5Image.setImageResource(R.drawable.ice);
    }

    private void setViewText(View view1, View view2, View view3, View view4, View view5){
        TextView name1 = (TextView) view1.findViewById(R.id.name);
        name1.setText(""+recommendResults_name.get(0));
        TextView name2 = (TextView) view2.findViewById(R.id.name);
        name2.setText(""+recommendResults_name.get(1));
        TextView name3 = (TextView) view3.findViewById(R.id.name);
        name3.setText(""+recommendResults_name.get(2));
        TextView name4 = (TextView) view4.findViewById(R.id.name);
        name4.setText(""+recommendResults_name.get(3));
        TextView name5 = (TextView) view5.findViewById(R.id.name);
        name5.setText(""+recommendResults_name.get(4));
//
        TextView address1 = (TextView) view1.findViewById(R.id.address);
        address1.setText(""+recommendResults_address.get(0));
        TextView address2 = (TextView) view2.findViewById(R.id.address);
        address2.setText(""+recommendResults_address.get(1));
        TextView address3 = (TextView) view3.findViewById(R.id.address);
        address3.setText(""+recommendResults_address.get(2));
        TextView address4 = (TextView) view4.findViewById(R.id.address);
        address4.setText(""+recommendResults_address.get(3));
        TextView address5 = (TextView) view5.findViewById(R.id.address);
        address5.setText(""+recommendResults_address.get(4));
//
        TextView phone1 = (TextView) view1.findViewById(R.id.phone);
        phone1.setText(""+recommendResults_phone.get(0));
        TextView phone2 = (TextView) view2.findViewById(R.id.phone);
        phone2.setText(""+recommendResults_phone.get(1));
        TextView phone3 = (TextView) view3.findViewById(R.id.phone);
        phone3.setText(""+recommendResults_phone.get(2));
        TextView phone4 = (TextView) view4.findViewById(R.id.phone);
        phone4.setText(""+recommendResults_phone.get(3));
        TextView phone5 = (TextView) view5.findViewById(R.id.phone);
        phone5.setText(""+recommendResults_phone.get(4));
//
        TextView type1 = (TextView) view1.findViewById(R.id.type);
        type1.setText(""+recommendResults_type.get(0));
        TextView type2 = (TextView) view2.findViewById(R.id.type);
        type2.setText(""+recommendResults_type.get(1));
        TextView type3 = (TextView) view3.findViewById(R.id.type);
        type3.setText(""+recommendResults_type.get(2));
        TextView type4 = (TextView) view4.findViewById(R.id.type);
        type4.setText(""+recommendResults_type.get(3));
        TextView type5 = (TextView) view5.findViewById(R.id.type);
        type5.setText(""+recommendResults_type.get(4));
//
        TextView open1 = (TextView) view1.findViewById(R.id.opening_hours);
        open1.setText(""+recommendResults_opening_hours.get(0));
        TextView open2 = (TextView) view2.findViewById(R.id.opening_hours);
        open2.setText(""+recommendResults_opening_hours.get(1));
        TextView open3 = (TextView) view3.findViewById(R.id.opening_hours);
        open3.setText(""+recommendResults_opening_hours.get(2));
        TextView open4 = (TextView) view4.findViewById(R.id.opening_hours);
        open4.setText(""+recommendResults_opening_hours.get(3));
        TextView open5 = (TextView) view5.findViewById(R.id.opening_hours);
        open5.setText(""+recommendResults_opening_hours.get(4));


    }
    @Override
    public void onResume() {
        super.onResume();
        restClient.getRecommends();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}