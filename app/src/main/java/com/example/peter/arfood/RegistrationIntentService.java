package com.example.peter.arfood;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.example.peter.arfood.models.RequestBody;
import com.example.peter.arfood.models.ResponseBody;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationIntentService extends IntentService{


    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String userEmail = intent.getStringExtra("USER_EMAIL");
        String userDisplayName = intent.getStringExtra("USER_NAME");

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String registrationId = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            registerDeviceProcess(userEmail,userDisplayName,registrationId);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void registerDeviceProcess(String userEmail, String userDisplayName, String registrationId){

        RequestBody requestBody = new RequestBody();
        requestBody.setUserEmail(userEmail);
        requestBody.setUsername(userDisplayName);
        requestBody.setRegistrationId(registrationId);
        Log.d("username:",userDisplayName);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("www.food-s14785236952.c9users.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = request.registerDevice(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                Intent intent = new Intent(MainActivity.REGISTRATION_PROCESS);
                intent.putExtra("result", responseBody.getResult());
                intent.putExtra("message",responseBody.getMessage());
                LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(intent);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
