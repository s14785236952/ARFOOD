package com.example.peter.arfood;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Arrays;

public class RestaurantActivity extends AppCompatActivity {

    private RestaurantAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String[] resturant1 = new String[]{"煦悅便當\n★★★★☆","06 200 5838","11:00 - 14:00，17:00 - 19:00","701台南市東區育樂街52號台灣 46 巷","https://www.google.com.tw/maps/place/%E7%85%A6%E6%82%85%E4%BE%BF%E7%95%B6%E5%B0%88%E8%B3%A3%E5%BA%97/@22.9956805,120.2170608,19z/data=!4m5!3m4!1s0x0:0x1c4cb54feda34ee3!8m2!3d22.9957181!4d120.216943"};
    private String[] resturant2 = new String[]{"大碗公成大店\n★★★★☆","06 208 5560","11:00 -23:00","701台南市東區長榮路三段56號","https://www.google.com.tw/search?q=%E5%8F%B0%E5%8D%97+%E5%A4%A7%E7%A2%97%E5%85%AC&npsic=0&rflfq=1&rlha=0&rllag=23030526,120245369,7334&tbm=lcl&ved=0ahUKEwj1nZX68b_UAhUGk5QKHfHCALsQtgMIJw&tbs=lrf:!2m1!1e2!2m1!1e3!3sEAE,lf:1,lf_ui:2&rldoc=1#rlfi=hd:;si:1247048579765505578;mv:!1m3!1d53088.338543954334!2d120.23443240000002!3d23.030526099999996!2m3!1f0!2f0!3f0!3m2!1i771!2i694!4f13.1;tbs:lrf:!2m1!1e2!2m1!1e3!3sEAE,lf:1,lf_ui:2"};
    private String[] resturant3 = new String[]{"成大綠豆湯\n★★★★☆","06 213 7868","10:00 - 20:30","700台南市中西區慶中街16號","https://www.google.com.tw/search?tbm=lcl&q=%E5%8F%B0%E5%8D%97+%E7%B6%A0%E8%B1%86%E6%B9%AF&oq=%E5%8F%B0%E5%8D%97+%E7%B6%A0%E8%B1%86%E6%B9%AF&gs_l=serp.3..0l3j0i30k1j0i8i30k1l4j0i5i30k1j0i8i30k1.3113.6110.0.7031.8.7.1.0.0.0.663.1743.4-1j2.3.0....0...1.1j4.64.serp..4.2.1080...35i39k1.ZqTV99Qj0Aw#rlfi=hd:;si:10404317143240317116;mv:!1m3!1d269.62591358621523!2d120.20558520000002!3d22.982244!2m3!1f0!2f0!3f0!3m2!1i1446!2i902!4f13.1"};
    private String[] resturant4 = new String[]{"日船章魚燒\n★★★★☆","無","無","701台南市東區育樂街41號","https://www.google.com.tw/search?tbm=lcl&q=%E5%8F%B0%E5%8D%97+%E8%82%B2%E6%A8%82%E8%A1%97+%E6%97%A5%E8%88%B9&oq=%E5%8F%B0%E5%8D%97+%E8%82%B2%E6%A8%82%E8%A1%97+%E6%97%A5%E8%88%B9&gs_l=serp.3...110134.116235.0.116867.22.18.0.0.0.0.387.2666.2-3j5.8.0....0...1.1j4.64.serp..15.4.1291...0j35i39k1j0i131k1.HJD1cZDfgUk#rlfi=hd:;si:16776230419762411189;mv:!1m3!1d269.5995622948794!2d120.21703190000002!3d22.9954438!2m3!1f0!2f0!3f0!3m2!1i1446!2i902!4f13.1"};
    private String[] resturant5 = new String[]{"小茂屋\n★★★★☆","06 235 8162","11:00 - 00:00","701台南市東區長榮路三段40號","https://www.google.com.tw/maps/place/%E5%B0%8F%E8%8C%82%E5%B1%8B/@22.9930718,120.2217418,15z/data=!4m5!3m4!1s0x0:0xa631300163fadfbc!8m2!3d22.9930718!4d120.2217418"};


    private int Choice_Resturant = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        Intent intent = this.getIntent();
        int r = Integer.parseInt(intent.getStringExtra("restaurant"));
        // 這裡只要傳 0 ~ 4 就可以改變五家店的資訊
        showResturant_Info(r);
    }
    private void showResturant_Info(int Choice_Value)
    {
        String[] Choice = null;

        // 透過這裡更改餐廳資訊及留言
        if(Choice_Value == 0)
            Choice = resturant1;
        else if(Choice_Value == 1)
            Choice = resturant2;
        else if(Choice_Value == 2)
            Choice = resturant3;
        else if(Choice_Value == 3)
            Choice = resturant4;
        else if(Choice_Value == 4)
            Choice = resturant5;

        TextView resturantnametv = (TextView)findViewById(R.id.resturant_name_score);
        resturantnametv.setTextSize(20);
        resturantnametv.setText(Choice[0]);

        TextView phonetv = (TextView)findViewById(R.id.phone_textview);
        phonetv.setText(Choice[1]);

        TextView timetv = (TextView)findViewById(R.id.time_textview);
        timetv.setText(Choice[2]);

        TextView gpstv = (TextView)findViewById(R.id.gps_textview);
        gpstv.setText(Choice[3]);

        TextView webtv = (TextView)findViewById(R.id.web_textview);
        webtv.setText(Choice[4]);

        showResturant_Image(Choice_Value);
    }
    private void showResturant_Image(int Choice_Value)
    {
        ArrayList<String> Restaurant_List = new ArrayList<String>(Arrays.asList("convenientfood","bigbowl","greensoup","maruko","xiaomao"));
        ImageView Image;

        //===================== 設置圖片=========================
        Image = (ImageView)findViewById(R.id.imageView);
        String uri = "@drawable/" + Restaurant_List.get(Choice_Value);
        int imageResource = getResources().getIdentifier(uri,null,getPackageName());
        Image.setImageResource(imageResource);
        //===================== 設置圖片=========================

        setAll_Info();
    }
    private void setAll_Info()
    {
        ArrayList<String> myDataset = new ArrayList<>();
        myDataset.add("");

        mAdapter = new RestaurantAdapter(myDataset);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(RestaurantActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //layoutManager.setSmoothScrollbarEnabled(true);
        //layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}

