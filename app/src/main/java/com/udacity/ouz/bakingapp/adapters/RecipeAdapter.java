package com.udacity.ouz.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.ouz.bakingapp.MainActivity;
import com.udacity.ouz.bakingapp.R;
import com.udacity.ouz.bakingapp.RecipeStepActivity;
import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.util.RecipeTypes;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    Context context;
    ArrayList<Recipe> recipes;
    Boolean isLandscapeMode;
    Boolean isTwoPane;

    public RecipeAdapter(Context context,ArrayList<Recipe> list,Boolean isLandscapeMode){
        this.context = context;
        this.recipes = list;
        this.isLandscapeMode = isLandscapeMode;
        this.isTwoPane = false;
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
        holder.recipe_id.setText(recipe.getId());

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

    public Recipe getItemById(String id){

        Recipe selectedRecipe=null;

        if(recipes.size() > 0 ){
            for(Recipe recipe : recipes){
                if(recipe.getId().equalsIgnoreCase(id)){
                    selectedRecipe = recipe;
                    break;
                }
            }
        }

        return selectedRecipe;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_recipe_thumbnail)
        ImageView recipe_thumbnail;
        @BindView(R.id.tv_recipe_title)
        TextView recipe_title;
        @BindView(R.id.tv_recipe_people)
        TextView recipe_people;
        @BindView(R.id.tv_recipe_id)
        TextView recipe_id;


        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick
        public void onClick() {

            String clickedId = recipe_id.getText().toString();

            Log.d(getClass().getName(),"Clicked recipe is : "+clickedId);

            Bundle bundle = new Bundle();
            Intent intent = new Intent(context,RecipeStepActivity.class);

            bundle.putSerializable(ScreenUtil.SELECTED_RECIPE_KEY,getItemById(clickedId));
            bundle.putBoolean(ScreenUtil.SCREEN_MODE_KEY,isLandscapeMode);
            bundle.putBoolean(ScreenUtil.IS_TWO_PANE_KEY,isTwoPane);

            intent.putExtras(bundle);

            context.startActivity(intent);
        }
    }

    public void setData(ArrayList<Recipe> data) {

        if (data != null) {
            recipes = data;
        }

        notifyDataSetChanged();
    }

}
