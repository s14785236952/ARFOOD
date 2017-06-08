package com.example.peter.arfood.services;

import com.example.peter.arfood.models.Explore;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("restaurant_explore")
    Call<Explore> createExplore(@Body Explore explore);

    @GET("restaurant_explore")
    Call<List<Explore>> explores();
}