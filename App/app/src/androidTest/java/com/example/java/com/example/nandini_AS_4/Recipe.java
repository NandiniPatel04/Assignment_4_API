package com.example.nandini_AS_4;

public class Recipe {
    private int id;
    private String title;
    private String difficultyLevel;
    private double rating;

    public Recipe(int id, String title, String difficultyLevel, double rating) {
        this.id = id;
        this.title = title;
        this.difficultyLevel = difficultyLevel;
        this.rating = rating;
    }

    public Recipe(String title, String difficultyLevel, double rating) {
        this.title = title;
        this.difficultyLevel = difficultyLevel;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
