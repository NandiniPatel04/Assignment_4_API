package com.example.nandini_AS_4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MovieViewHolder> {

    private Context context;
    private List<Recipe> mRecipeList;
    private RecipeDatabaseHelper dbHelper;

    public RecipeAdapter(Context context, List<Recipe> recipeList, RecipeDatabaseHelper dbHelper) {
        this.context = context;
        this.mRecipeList = recipeList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(holder.getAdapterPosition());
        holder.titleTextView.setText(recipe.getTitle());
        holder.studioTextView.setText(recipe.getDifficultyLevel());
        holder.ratingTextView.setText(String.valueOf(recipe.getRating()));

        holder.deleteButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            dbHelper.deleteMovie(recipe.getId());
            mRecipeList.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
            notifyItemRangeChanged(adapterPosition, mRecipeList.size());
        });

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditRecipeActivity.class);
            intent.putExtra("MOVIE_ID", recipe.getId());
            ((Activity) context).startActivityForResult(intent, RecipeListActivity.REQUEST_EDIT_MOVIE);
        });
    }


    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void updateMovies(List<Recipe> recipes) {
        this.mRecipeList = recipes;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, studioTextView, ratingTextView;
        public ImageButton deleteButton, editButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            studioTextView = itemView.findViewById(R.id.textViewStudio);
            ratingTextView = itemView.findViewById(R.id.textViewRating);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            editButton = itemView.findViewById(R.id.buttonEdit);
        }
    }
}
