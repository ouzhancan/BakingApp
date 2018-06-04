package com.udacity.ouz.bakingapp.retrofit;

import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.model.RecipeContainer;
import com.udacity.ouz.bakingapp.util.NetworkUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRecipes {

    @GET("{suffix}")
    Call<ArrayList<Recipe>> getAllRecipes(@Path("suffix")String suffix);
}
