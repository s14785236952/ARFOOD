package com.example.peter.arfood;

import com.example.peter.arfood.models.RequestBody;
import com.example.peter.arfood.models.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("devices")
    Call<ResponseBody> registerDevice(@Body RequestBody body);
}
