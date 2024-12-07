package com.example.nandini_AS_4;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextStudio, editTextRating;
    private Button buttonAdd, buttonCancel; // Define buttonCancel
    private RecipeDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        dbHelper = new RecipeDatabaseHelper(this);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextStudio = findViewById(R.id.editTextStudio);
        editTextRating = findViewById(R.id.editTextRating);
        buttonAdd = findViewById(R.id.buttonAddMovie);
        buttonCancel = findViewById(R.id.buttonCancel); // Initialize buttonCancel

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovie();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAddMovie();
            }
        });
    }

    private void addMovie() {
        String title = editTextTitle.getText().toString().trim();
        String studio = editTextStudio.getText().toString().trim();
        String ratingStr = editTextRating.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(studio) || TextUtils.isEmpty(ratingStr)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double rating;
        try {
            rating = Double.parseDouble(ratingStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid rating", Toast.LENGTH_SHORT).show();
            return;
        }

        Recipe recipe = new Recipe(title, studio, rating);
        dbHelper.addMovie(recipe);
        Toast.makeText(this, "Movie added", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void cancelAddMovie() {
        finish(); // Finish current activity and return to the previous activity (MovieListActivity)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}

