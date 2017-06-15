package com.example.peter.arfood;

import com.example.peter.arfood.interfaces.RequestInterface;
import com.example.peter.arfood.models.RequestBody;
import com.example.peter.arfood.models.ResponseBody;
import com.example.peter.arfood.models.UserBeen;
import com.example.peter.arfood.services.APIService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.lidong.photopicker.ImageCaptureManager;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.peter.arfood.ARFood.userEmail;


/**
 */
public class PostActivity extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener {
    public static int PLACE_PICKER_REQUEST = 1;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ImageCaptureManager captureManager; // 相机拍照处理类
    public static String name;
    public static  String address;
    public static String placeLatLng;
    public static String type;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private Button mButton,mBtn;
    private TextView mViewName, mViewAddress;
    private String depp;
    private EditText textView;
    private String TAG =MainActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_post);
        gridView = (GridView) findViewById(R.id.gridView);
        mButton = (Button) findViewById(R.id.button);
        mBtn = (Button) findViewById(R.id.btn_place);
        mViewName = (TextView) findViewById(R.id.textView);
        mViewAddress = (TextView) findViewById(R.id.textView2);
        textView= (EditText)findViewById(R.id.et_context);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Construct an intent for the place picker
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(PostActivity.this);
                    // Start the intent by requesting a result,
                    // identified by a request code.
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    // ...
                } catch (GooglePlayServicesNotAvailableException e) {
                    // ...
                }
            }
        });
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);

        // preview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                if ("000000".equals(imgs) ){
                    PhotoPickerIntent intent = new PhotoPickerIntent(PostActivity.this);
                    intent.setSelectModel(SelectModel.SINGLE);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                }else{
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(PostActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depp =textView.getText().toString().trim()!=null?textView.getText().toString().trim():"woowoeo";
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        postProcess(userEmail,name,address,placeLatLng,type);
//                        FileUploadManager.uploadMany(imagePaths, depp);
//                        FileUploadManager.upload(imagePaths,depp);
                    }
                }.start();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "list: " + "list = [" + list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);
                    break;
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(this,data);

            name = (String) place.getName();
            address = (String) place.getAddress();
            placeLatLng = String.valueOf(place.getLatLng());
            final List<Integer> types = place.getPlaceTypes();
            type = String.valueOf(types.get(0));
            mViewName.setText(name);
            mViewAddress.setText(address);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("000000")){
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        gridAdapter  = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;
        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if(listUrls.size() == 7){
                listUrls.remove(listUrls.size()-1);
            }
            inflater = LayoutInflater.from(PostActivity.this);
        }

        public int getCount(){
            return  listUrls.size();
        }
        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_image, parent,false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final String path=listUrls.get(position);
            if (path.equals("000000")){
                holder.image.setImageResource(R.drawable.ic_newpic);
            }else {
                Glide.with(PostActivity.this)
                        .load(path)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }
        class ViewHolder {
            ImageView image;
        }
    }

    private void postProcess(String userEmail,String name, String address, String placeLatLng, String type){

        UserBeen userBeen = new UserBeen();
        userBeen.setUserEmail(userEmail);
        userBeen.setName(name);
        userBeen.setAddress(address);
        userBeen.setPlaceLatLng(placeLatLng);
        userBeen.setType(type);
        Log.d("userEmail:",userEmail+name);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://food-s14785236952.c9users.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService request = retrofit.create(APIService.class);
        Call<UserBeen> call = request.createUserBeen(userBeen);
        call.enqueue(new Callback<UserBeen>() {
            @Override
            public void onResponse(Call<UserBeen> call, Response<UserBeen> response) {

            }

            @Override
            public void onFailure(Call<UserBeen> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent();
        intent.setClass(PostActivity.this, MainActivity.class);
        startActivity(intent);

    }
}