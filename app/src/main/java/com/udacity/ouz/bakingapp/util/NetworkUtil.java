package com.udacity.ouz.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.model.RecipeContainer;
import com.udacity.ouz.bakingapp.retrofit.IRecipes;
import com.udacity.ouz.bakingapp.retrofit.RecipeClient;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class NetworkUtil {

    public static final String BASE_URL  =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    public static final String DIRECTORY_SUFFIX = "baking.json";

    // retrofit api service variable
    public static IRecipes apiService;
    private static ArrayList<Recipe> recipeList;


    public static ArrayList<Recipe> getAllRecipes() {

        // *** **** Retrofit service **** *** //
        apiService = RecipeClient.getClient().create(IRecipes.class);

        Call<ArrayList<Recipe>> recipeCall;

        recipeCall = apiService.getAllRecipes(NetworkUtil.DIRECTORY_SUFFIX);

        if (recipeCall != null) {

            /* Synchronous calling */
            try {
                Response<ArrayList<Recipe>> response = recipeCall.execute();


                if (response.isSuccessful()) {
                    recipeList = response.body();
                }

            } catch (IOException ex) {
                Log.d("RETROFIT_CALL", "retrofit synchronous calling was thrown an error -> " + ex.getLocalizedMessage());
            }
        }

        // *** *** Retrofit Service *** *** //

        return recipeList;
    }

    /**
     * Check internet connection
     *
     * @return
     */
    public static boolean isThereAConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
