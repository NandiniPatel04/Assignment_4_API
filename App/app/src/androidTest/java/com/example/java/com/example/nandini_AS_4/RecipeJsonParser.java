package com.example.nandini_AS_4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeJsonParser {

    public static List<Recipe> parseMovies(String jsonData) {
        List<Recipe> recipeList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String difficultyLevel = jsonObject.getString("difficultyLevel");
                double rating = jsonObject.getDouble("criticsRating");

                Recipe recipe = new Recipe(title, difficultyLevel, rating);
                recipeList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
}
