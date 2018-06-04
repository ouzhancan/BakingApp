package com.udacity.ouz.bakingapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeContainer {

    private ArrayList<Recipe> recipes;

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "RecipeContainer{" +
                "recipes=" + recipes +
                '}';
    }
}
