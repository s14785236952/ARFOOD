package com.example.peter.arfood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.peter.arfood.fragment.CityFragment;
import com.example.peter.arfood.fragment.ExploreFragment;
import com.example.peter.arfood.fragment.FavoriteFragment;
import com.example.peter.arfood.fragment.RecommendFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity  {
    private ExploreFragment explore;
    private RecommendFragment recommend;
    private CityFragment city;
    private FavoriteFragment favorite;
    public static final String REGISTRATION_PROCESS = "註冊";
    public static final String MESSAGE_RECEIVED = "收到訊息";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btn_register;
    private double userLatitude;
    private double userLongitude;
    private LocationManager locMan;
    private GoogleApiClient mGoogleApiClient;
    String userEmail,userDisplayName;
    private FragmentManager fragMentmanager;
    private FragmentTransaction fragmentTransaction;
    public static final int FRAGMENT_EXPLORE=0;
    public static final int FRAGMENT_RECOMMEND=1;
    public static final int FRAGMENT_CITY=2;
    public static final int FRAGMENT_FAVORITE=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        registerReceiver();
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");
        userDisplayName = intent.getStringExtra("USER_NAME");
        if (intent != null) {
            if (intent.getAction() == MESSAGE_RECEIVED) {
                String message = intent.getStringExtra("message");
                showAlertDialog(message);
            }
        }
        if(checkPermission()) {
            startRegisterProcess();
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getBaseContext(), "Connect Fail", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


//        findViewById(R.id.show_cur_location_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getCurLocation();
//            }
//        });
        fragMentmanager = getSupportFragmentManager();

//        findViewById(R.id.List_Btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragmentTransaction = fragMentmanager.beginTransaction();
//
//                List_Fragment fragment1 = new List_Fragment();
//                fragmentTransaction.replace(R.id.List_Layout,fragment1,"123");
//                fragmentTransaction.commit();
//            }
//        });
        showFragment(FRAGMENT_EXPLORE);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_explore) {
                    showFragment(FRAGMENT_EXPLORE);
                }
                else if (tabId == R.id.tab_recommend) {
                    showFragment(FRAGMENT_RECOMMEND);
                }
                else if (tabId == R.id.tab_city) {
                    showFragment(FRAGMENT_CITY);
                }
                else if (tabId == R.id.tab_favorite) {
                    showFragment(FRAGMENT_FAVORITE);
                }
            }
        });
    }



    private void initViews() {
        //TODO:you do not need to press a button that you can register your device to recieve the message
//        btn_register = (Button) findViewById(R.id.btn_register);
//
//        btn_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkPlayServices()) {
//                    startRegisterProcess();
//                }
//            }
//        });
    }

    private void startRegisterProcess() {

        if (checkPermission()) {

            startRegisterService();

        } else {

            requestPermission();
        }

    }

    private void startRegisterService() {

        Intent intent = new Intent(MainActivity.this, RegistrationIntentService.class);
        Log.d("Name: ",userDisplayName);
        Log.d("Email: ",userEmail);
        intent.putExtra("USER_EMAIL", userEmail);
        intent.putExtra("USER_NAME",userDisplayName);
        startService(intent);
    }

    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REGISTRATION_PROCESS);
        intentFilter.addAction(MESSAGE_RECEIVED);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ARFOOD");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(REGISTRATION_PROCESS)) {

                String result = intent.getStringExtra("result");
                String message = intent.getStringExtra("message");
//                Log.d(TAG, "onReceive: " + result + message);
//                Snackbar.make(findViewById(R.id.coordinatorLayout), result + " : " + message, Snackbar.LENGTH_SHORT).show();
            } else if (intent.getAction().equals(MESSAGE_RECEIVED)) {

                String message = intent.getStringExtra("message");
                Log.d("Recieved message",message);
                showAlertDialog(message);
            }
        }
    };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startRegisterService();

                } else {

                    Snackbar.make(findViewById(R.id.coordinatorLayout), "Permission Denied, Please allow to proceed !.", Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void getCurLocation() {
        // TODO:let Current Location can always send to Server.
        locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locMan.isProviderEnabled(LocationManager.GPS_PROVIDER) || locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationServiceInitial();
        } else {
            Toast.makeText(getBaseContext(), "Please open the GPS service", Toast.LENGTH_SHORT).show();
            startActivities(new Intent[]{new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)});
        }
    }

    private void locationServiceInitial() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location curLocation = locMan.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        getLocations(curLocation);
    }

    private void getLocations(Location location) {

        if (location != null) {
            userLongitude = location.getLongitude();
            userLatitude = location.getLatitude();

            Toast.makeText(getBaseContext(), userLongitude + " " + userLatitude, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getBaseContext(), "Not get the location", Toast.LENGTH_SHORT).show();
    }


    //Fragment swap
    public void showFragment(int index){

        FragmentTransaction ft=fragMentmanager.beginTransaction();
        hideFragment(ft);

        switch (index){

            case FRAGMENT_EXPLORE:

                if (explore==null){
                    explore=new ExploreFragment();
                    ft.add(R.id.container,explore);
                }else {
                    ft.show(explore);
                }

                break;
            case FRAGMENT_RECOMMEND:

                if (recommend==null){
                    recommend=new RecommendFragment();
                    ft.add(R.id.container,recommend);
                }else {
                    ft.show(recommend);
                }

                break;
            case FRAGMENT_CITY:

                if (city==null){
                    city=new CityFragment();
                    ft.add(R.id.container,city);
                }else {
                    ft.show(city);
                }

                break;
            case FRAGMENT_FAVORITE:

                if (favorite==null){
                    favorite=new FavoriteFragment();
                    ft.add(R.id.container,favorite);
                }else {
                    ft.show(favorite);
                }

                break;
        }


        ft.commit();
    }
    //if fragment is not NULL , hide the fragment.
    public void hideFragment(FragmentTransaction ft){

        if (explore!=null){
            ft.hide(explore);
        }
        if(recommend!=null) {
            ft.hide(recommend);
        }
        if(city!=null) {
            ft.hide(city);
        }
        if(favorite!=null) {
            ft.hide(favorite);
        }
    }
    //TODO: If sign out?
//    private void googleSignOut() {
//        findViewById(R.id.sign_out_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOut();
//            }
//        });
//    }
//
//    private void signOut() {
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
//            @Override
//            public void onResult(@NonNull Status status) {
//                Toast.makeText(getBaseContext(), "Success Sign out", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this,ARFood.class);
//                startActivity(intent);
//
//            }
//        });
//    }

}