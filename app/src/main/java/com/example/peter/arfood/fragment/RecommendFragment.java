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
    private Button No5btn;
    List<String> recommendResults_name = new ArrayList<>();
    List<String> recommendResults_address = new ArrayList<>();
    List<String> recommendResults_phone = new ArrayList<>();
    List<String> recommendResults_type = new ArrayList<>();
    List<String> recommendResults_opening_hours = new ArrayList<>();
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
        No5btn = (Button) v.findViewById(R.id.No5btn);

        No1btn.setOnClickListener(No1btnOnClick);
        No2btn.setOnClickListener(No2btnOnClick);
        No3btn.setOnClickListener(No3btnOnClick);
        No4btn.setOnClickListener(No4btnOnClick);
        No5btn.setOnClickListener(No5btnOnClick);

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
            }
        };
        restClient.setCallback(callback);

        setViewInfo(R.drawable.noodle,  R.drawable.star1, 0);

        return v;
    }

    private View.OnClickListener No1btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setViewInfo(R.drawable.noodle,  R.drawable.star1, 0);
        }
    };
    private View.OnClickListener No2btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setViewInfo(R.drawable.octopus,  R.drawable.star2, 1);
        }
    };
    private View.OnClickListener No3btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setViewInfo(R.drawable.green,  R.drawable.star3, 2);
        }
    };
    private View.OnClickListener No4btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setViewInfo(R.drawable.foodbox,  R.drawable.star4, 3);
        }
    };

    private View.OnClickListener No5btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setViewInfo(R.drawable.ice,  R.drawable.star5, 4);
        }
    };

    private void setViewInfo(int ImageID, int StarID, int index){
        ImageView viewImage = (ImageView) v.findViewById(R.id.imageView);
        viewImage.setImageResource(ImageID);
        ImageView starImage = (ImageView) v.findViewById(R.id.stars);
        starImage.setImageResource(StarID);
        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(""+recommendResults_name.get(index));
        TextView address = (TextView) v.findViewById(R.id.address);
        address.setText(""+recommendResults_address.get(index));
        TextView phone = (TextView) v.findViewById(R.id.phone);
        phone.setText(""+recommendResults_phone.get(index));
        TextView type = (TextView) v.findViewById(R.id.type);
        type.setText(""+recommendResults_type.get(index));
        TextView open = (TextView) v.findViewById(R.id.opening_hours);
        open.setText(""+recommendResults_opening_hours.get(index));
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