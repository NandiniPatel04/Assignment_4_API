package com.example.nandini_AS_4.api;

import com.example.recipesapp.data.Recipe;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecipeApiService {

    @GET("api/list/")  // Replace with your API endpoint
    Call<List<Recipe>> getRecipes();

    @POST("api/login")
    retrofit2.Call<Response<Map<String, String>>> login(@Body Map<String, String> loginData);

    @POST("api/register")
    retrofit2.Call<Void> register(@Body Map<String, String> registerData);
}
