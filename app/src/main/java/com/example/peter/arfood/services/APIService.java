package com.example.peter.arfood.services;

import com.example.peter.arfood.models.Explore;
import com.example.peter.arfood.models.Recommend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @POST("recommend_results/")
    Call<Recommend> createRecommend(@Body Recommend recommend);

    @GET("recommend_results/{user}")
    Call<List<Recommend>> recommends(@Path("user") String user);

    @POST("restaurant_explore")
    Call<Explore> createExplore(@Body Explore explore);

    @GET("restaurant_explore")
    Call<List<Explore>> explores();
}