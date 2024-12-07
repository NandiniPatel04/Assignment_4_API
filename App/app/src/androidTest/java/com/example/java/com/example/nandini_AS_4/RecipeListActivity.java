package com.example.nandini_AS_4;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeDatabaseHelper dbHelper;
    private FloatingActionButton fabAddMovie, fabRefreshMovie, fabLoadJson;
    private RecipeAdapter mAdapter;

    public static final int REQUEST_ADD_MOVIE = 1;
    public static final int REQUEST_EDIT_MOVIE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        dbHelper = new RecipeDatabaseHelper(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Recipe> recipes = dbHelper.getAllMovies();
        mAdapter = new RecipeAdapter(this, recipes, dbHelper);
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fabAddMovie = findViewById(R.id.fab_add_movie);
        fabAddMovie.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeListActivity.this, AddRecipeActivity.class);
            startActivityForResult(intent, REQUEST_ADD_MOVIE);
        });

        FloatingActionButton fabRefreshMovie = findViewById(R.id.fab_refresh_movie);
        fabRefreshMovie.setOnClickListener(v -> {
//            loadJsonData();
            mAdapter.updateMovies(dbHelper.getAllMovies());
        });

        fabLoadJson = findViewById(R.id.fab_load_json);

        fabLoadJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your function to load JSON data here
                loadJsonData();
            }
        });

//        loadJsonData();
    }

    private void loadJsonData() {
        StringBuilder stringBuilder = new StringBuilder();
        Toast.makeText(this, "Loading JSON data...", Toast.LENGTH_SHORT).show();
        try {
            InputStream inputStream = getAssets().open("movies.json");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();
            String jsonData = stringBuilder.toString();
            addMoviesFromJson(jsonData);
            new LoadJsonTask().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Example AsyncTask to simulate loading JSON data
    private class LoadJsonTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // Simulate loading JSON data
            try {
                Thread.sleep(500); // Simulate a delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Update RecyclerView adapter with new data if needed
            Toast.makeText(RecipeListActivity.this, "JSON data loaded", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMoviesFromJson(String jsonData) {
        try {
            List<Recipe> recipeList = RecipeJsonParser.parseMovies(jsonData);
            for (Recipe recipe : recipeList) {
                dbHelper.addMovie(recipe);
            }
            mAdapter.updateMovies(dbHelper.getAllMovies());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshMovieList() {
//        dbHelper.getAllMovies();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecipeAdapter(this, dbHelper.getAllMovies(), dbHelper);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_ADD_MOVIE || requestCode == REQUEST_EDIT_MOVIE) && resultCode == RESULT_OK) {
            mAdapter.updateMovies(dbHelper.getAllMovies());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
