package com.example.nandini_AS_4.api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://assignment-3-api-7u5w.onrender.com/";
    private static RecipeApiService api;

    // Lazy initialization of the RecipeApiService instance
    public static RecipeApiService getApi() {
        if (api == null) {
            synchronized (RetrofitInstance.class) {
                if (api == null) {
                    api = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(RecipeApiService.class);
                }
            }
        }
        return api;
    }
}
