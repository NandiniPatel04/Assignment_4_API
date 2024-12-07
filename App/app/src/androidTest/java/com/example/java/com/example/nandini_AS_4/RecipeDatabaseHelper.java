package com.example.nandini_AS_4;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    public static final String DATABASE_NAME = "movie";
    public static final int DATABASE_VERSION = 1;

    // Table name and columns
    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_STUDIO = "studio";
    public static final String COLUMN_RATING = "rating";

    // Constructor
    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_STUDIO + " TEXT," +
                COLUMN_RATING + " REAL" +
                ")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        // Create tables again
        onCreate(db);
    }

    // Adding a new movie
    public long addMovie(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, recipe.getTitle());
        values.put(COLUMN_STUDIO, recipe.getDifficultyLevel());
        values.put(COLUMN_RATING, recipe.getRating());

        // Inserting Row
        db.insert(TABLE_MOVIES, null, values);
        db.close(); // Closing database connection
        return 0;
    }

    // Getting All Movies
    @SuppressLint("Range")
    public List<Recipe> getAllMovies() {
        List<Recipe> recipeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))), cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)), cursor.getString(cursor.getColumnIndex(COLUMN_STUDIO)), cursor.getDouble(cursor.getColumnIndex(COLUMN_RATING)));
                recipe.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                recipe.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                recipe.setDifficultyLevel(cursor.getString(cursor.getColumnIndex(COLUMN_STUDIO)));
                recipe.setRating(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATING)));
                // Adding movie to list
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }

        // close cursor and db connection
        cursor.close();
        db.close();
        // return movie list
        return recipeList;
    }

    // Method to add movies from JSON data
    public void addMoviesFromJson(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String difficultyLevel = jsonObject.getString("difficultyLevel");
                double rating = jsonObject.getDouble("criticsRating");

                Recipe recipe = new Recipe(title, difficultyLevel, rating);
                addMovie(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public Recipe getMovieById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_STUDIO, COLUMN_RATING},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        Recipe recipe = null;
        if (cursor != null && cursor.moveToFirst()) {
            recipe = new Recipe(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STUDIO)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_RATING))
            );
            cursor.close();
        }

        db.close();
        return recipe;
    }


    public int updateMovie(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, recipe.getTitle());
        values.put(COLUMN_STUDIO, recipe.getDifficultyLevel());
        values.put(COLUMN_RATING, recipe.getRating());

        return db.update(TABLE_MOVIES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(recipe.getId())});
    }

}
