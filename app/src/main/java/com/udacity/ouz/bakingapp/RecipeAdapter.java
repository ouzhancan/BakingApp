package com.udacity.ouz.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.model.RecipeContainer;
import com.udacity.ouz.bakingapp.util.RecipeTypes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    Context context;
    ArrayList<Recipe> recipes;
    Boolean isLandscapeMode;

    public RecipeAdapter(Context context,ArrayList<Recipe> list,Boolean isLandscapeMode){
        this.context = context;
        this.recipes = list;
        this.isLandscapeMode = isLandscapeMode;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view;

        view = inflater.inflate(R.layout.recipe_card,parent,false);

        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);

        holder.recipe_title.setText(recipe.getName());
        holder.recipe_people.setText("for "+recipe.getServings()+" people");

        if(recipe.getImage()!=null && recipe.getImage().length()>0){
            Picasso.with(context)
                    .load(Uri.parse(recipe.getImage()))
                    .into(holder.recipe_thumbnail);
        }else {
            Picasso.with(context)
                    .load(RecipeTypes.getRecipeImage(RecipeTypes.getRecipeTypes(recipe.getName())))
                    .into(holder.recipe_thumbnail);
        }
    }

    @Override
    public int getItemCount() {

        return recipes!=null?recipes.size():0;
    }

    @Override
    public long getItemId(int position) {
        return new Long(recipes.get(position).getId());
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.iv_recipe_thumbnail)
        ImageView recipe_thumbnail;
        @BindView(R.id.tv_recipe_title)
        TextView recipe_title;
        @BindView(R.id.tv_recipe_people)
        TextView recipe_people;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context,"Clicked recipe is : "+v.getId(),Toast.LENGTH_LONG).show();
        }
    }

    public void setData(ArrayList<Recipe> data) {

        if (data != null) {
            recipes = data;
        }

        notifyDataSetChanged();
    }

}
