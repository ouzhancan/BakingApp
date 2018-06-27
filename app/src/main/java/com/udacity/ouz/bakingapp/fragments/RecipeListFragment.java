package com.udacity.ouz.bakingapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.ouz.bakingapp.R;
import com.udacity.ouz.bakingapp.RecipeStepActivity;
import com.udacity.ouz.bakingapp.adapters.RecipeItemAdapter;
import com.udacity.ouz.bakingapp.model.Ingredient;
import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.model.Step;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    OnRecipeItemClickListener mCallback;

    @BindView(R.id.rv_recipe_step)
    RecyclerView rvSteps;

    @BindView(R.id.tv_recipe_ingredients)
    TextView tvIngredients;


    public interface OnRecipeItemClickListener {
        void onRecipeSelected(int id);
    }

    public RecipeListFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnRecipeItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container,
                false);

        ButterKnife.bind(this, rootView);

        RecipeStepActivity context = (RecipeStepActivity)getContext();

        Recipe selectedRecipe=null;

        if(savedInstanceState != null ){
            selectedRecipe = (Recipe)savedInstanceState.get(ScreenUtil.SELECTED_RECIPE_KEY);
        }else{

            Recipe recipe = (Recipe)context.getIntent().getSerializableExtra(ScreenUtil.SELECTED_RECIPE_KEY);
            selectedRecipe = recipe;

            context.getIntent().getBooleanExtra(ScreenUtil.IS_TWO_PANE_KEY,false);
            context.getIntent().getBooleanExtra(ScreenUtil.SCREEN_MODE_KEY,false);
        }

        if(selectedRecipe != null){

            StringBuilder ingredientBuilder = new StringBuilder();

            for(Ingredient ingredient : selectedRecipe.getIngredients()){
                ingredientBuilder.append(" - "+ingredient.getQuantity()+" ");
                ingredientBuilder.append(ingredient.getMeasure() +" ");
                ingredientBuilder.append(ingredient.getIngredient());
                ingredientBuilder.append(System.lineSeparator());
            }

            tvIngredients.setText(ingredientBuilder.toString());


            ArrayList<Step> stepList = new ArrayList<Step>();
            for(Step element : selectedRecipe.getSteps() ){
                stepList.add(element);
            }

            RecipeItemAdapter recipeItemAdapter = new RecipeItemAdapter(context,
                    stepList);

            rvSteps.setAdapter(recipeItemAdapter);
            //rvSteps.setHasFixedSize(true);
            recipeItemAdapter.setData(stepList);
        }


        return rootView;
    }
}
