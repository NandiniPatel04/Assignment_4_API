package com.example.nandini_AS_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class EditRecipeActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText studioEditText;
    private EditText ratingEditText;
    private int movieId;
    private RecipeDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        titleEditText = findViewById(R.id.editTextTitle);
        studioEditText = findViewById(R.id.editTextStudio);
        ratingEditText = findViewById(R.id.editTextRating);
        dbHelper = new RecipeDatabaseHelper(this);

        // Get movie ID from intent
        Intent intent = getIntent();
        if (intent != null) {
            movieId = intent.getIntExtra("MOVIE_ID", -1);
            if (movieId != -1) {
                // Retrieve movie from database
                Recipe recipe = dbHelper.getMovieById(movieId);
                if (recipe != null) {
                    // Populate UI fields with movie data
                    titleEditText.setText(recipe.getTitle());
                    studioEditText.setText(recipe.getDifficultyLevel());
                    ratingEditText.setText(String.valueOf(recipe.getRating()));
                } else {
                    Toast.makeText(this, "Movie not found", Toast.LENGTH_SHORT).show();
                    finish(); // Finish activity if movie is not found
                }
            } else {
                Toast.makeText(this, "Invalid movie ID", Toast.LENGTH_SHORT).show();
                finish(); // Finish activity if movie ID is invalid
            }
        } else {
            Toast.makeText(this, "Error: Intent is null", Toast.LENGTH_SHORT).show();
            finish(); // Finish activity if intent is null
        }

        // Bind the update button
        Button updateButton = findViewById(R.id.buttonUpdateMovie);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMovie();
            }
        });

        // Bind the cancel button
        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEditMovie();
            }
        });
    }

    // Method to update the movie in the database
    private void updateMovie() {
        String title = titleEditText.getText().toString().trim();
        String studio = studioEditText.getText().toString().trim();
        double rating = Double.parseDouble(ratingEditText.getText().toString().trim());

        Recipe updatedRecipe = new Recipe(movieId, title, studio, rating);
        dbHelper.updateMovie(updatedRecipe);

        Toast.makeText(this, "Movie updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Method to cancel editing and return to previous activity
    private void cancelEditMovie() {
        finish(); // Finish current activity and return to the previous activity (MovieListActivity)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
