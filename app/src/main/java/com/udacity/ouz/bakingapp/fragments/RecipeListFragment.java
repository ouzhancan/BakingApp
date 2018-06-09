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

import com.udacity.ouz.bakingapp.MainActivity;
import com.udacity.ouz.bakingapp.R;
import com.udacity.ouz.bakingapp.RecipeStepActivity;
import com.udacity.ouz.bakingapp.adapters.RecipeItemAdapter;
import com.udacity.ouz.bakingapp.model.Recipe;

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

        Recipe selectedRecipe=null;

        if(savedInstanceState != null ){
            selectedRecipe = (Recipe)savedInstanceState.get(MainActivity.SELECTED_RECIPE_KEY);
        }

        if(selectedRecipe == null){
            selectedRecipe = RecipeStepActivity.getSelectedRecipe();
        }

        RecipeItemAdapter recipeItemAdapter = new RecipeItemAdapter(getContext(), selectedRecipe);

        rvSteps.setAdapter(recipeItemAdapter);
        rvSteps.setHasFixedSize(true);

        return rootView;
    }
}
