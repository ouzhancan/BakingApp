package com.udacity.ouz.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.ouz.bakingapp.model.Recipe;

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.ItemViewHolder> {

    Context context;
    Recipe recipe;

    public RecipeItemAdapter(Context context, Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {



        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
