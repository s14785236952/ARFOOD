package com.example.peter.arfood.fragment;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import android.content.Context;

import com.example.peter.arfood.MainActivity;
import com.example.peter.arfood.PostActivity;
import com.example.peter.arfood.R;
import com.example.peter.arfood.RestClient;
import com.example.peter.arfood.models.Recommend;
import com.example.peter.arfood.models.UserBeen;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;


public class CityFragment extends Fragment implements OnMapReadyCallback {
    RestClient restClient = RestClient.getInstance();
    private MapView mapView;
    private FloatingActionButton mbtn;
    public static List<String> userBeenResults_name = new ArrayList<>();
    public static List<String> userBeenResults_address = new ArrayList<>();
    public static List<String> userBeenResults_LatLng = new ArrayList<>();
    public static List<String> userBeenResults_type = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_city, container, false);
        RestClient.UserBeenResultReadyCallback callback = new RestClient.UserBeenResultReadyCallback() {
            @Override
            public void userBeenResultReady(List<UserBeen> userBeen) {
                for(UserBeen u: userBeen) {
                    userBeenResults_name.add(u.name);
                    userBeenResults_address.add(u.address);
                    userBeenResults_LatLng.add(u.placeLatLng);
                    userBeenResults_type.add(u.type);
                }
                Log.d("userBeen: ", String.valueOf(userBeenResults_name));
                Log.d("userBeen: ", String.valueOf(userBeenResults_address));
                Log.d("userBeen: ", String.valueOf(userBeenResults_LatLng));
                Log.d("userBeen: ", String.valueOf(userBeenResults_type));
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(CityFragment.this);
            }

        };
        restClient.setCallback(callback);


        mbtn = (FloatingActionButton) v.findViewById(R.id.fab);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), PostActivity.class);
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style_json);
        map.setMapStyle(style);
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setTiltGesturesEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(22.9920689,120.2224226))
                .zoom(17)
                .tilt(0f)
                .bearing(380)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LatLng Tainan = new LatLng(22.9920689,120.2224226);

        for (int i = 0; i<userBeenResults_name.size();i++){
            String place = userBeenResults_LatLng.get(i).replaceAll("[a-z]","");
            place = place.replaceAll("/","");
            place = place.replaceAll(":","");
            place = place.replaceAll(" ","");
            place = place.replaceAll("[()]","");
            String[] LatLng = place.split(",");
            Double Lat = Double.valueOf(LatLng[0]);
            Double Lng = Double.valueOf(LatLng[1]);
            Log.d("place",place);
            if(Integer.valueOf(userBeenResults_type.get(i)) == 15) {
                map.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.cafe))
                        .position(new LatLng(Lat, Lng), 50f)
                        .bearing(20));
            } else if(Integer.valueOf(userBeenResults_type.get(i)) == 7) {
                map.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.bakery))
                        .position(new LatLng(Lat, Lng), 50f)
                        .bearing(20));
            } else if(Integer.valueOf(userBeenResults_type.get(i)) == 9) {
                map.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.beer))
                        .position(new LatLng(Lat, Lng), 50f)
                        .bearing(20));
            } else if(Integer.valueOf(userBeenResults_type.get(i)) == 38) {
                map.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.meat))
                        .position(new LatLng(Lat, Lng), 50f)
                        .bearing(20));
            } else if(Integer.valueOf(userBeenResults_type.get(i)) == 79) {
                map.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.sushi))
                        .position(new LatLng(Lat, Lng), 50f)
                        .bearing(20));
            } else {
                map.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.burger))
                        .position(new LatLng(Lat, Lng), 50f)
                        .bearing(20));
            }
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        restClient.getUserBeens();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
    }


