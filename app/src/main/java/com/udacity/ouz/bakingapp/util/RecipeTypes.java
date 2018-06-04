package com.udacity.ouz.bakingapp.util;

import com.udacity.ouz.bakingapp.R;

public enum RecipeTypes {
    NUTELLA_PIE,
    BROWNIES,
    YELLOW_CAKE,
    CHEESECAKE;

    public static RecipeTypes getRecipeTypes(String name) {
        RecipeTypes type = null; // default

        switch (name) {
            case "Nutella Pie":
                type = NUTELLA_PIE;
                break;
            case "Brownies":
                type = BROWNIES;
                break;
            case "Yellow Cake":
                type = YELLOW_CAKE;
                break;
            case "Cheesecake":
                type = CHEESECAKE;
                break;
        }

        return type;
    }

    public static int getRecipeImage(RecipeTypes type) {

        int image = R.drawable.nutellapie;

        switch (type) {
            case NUTELLA_PIE:
                image = R.drawable.nutellapie;
                break;
            case BROWNIES:
                image = R.drawable.brownie;
                break;
            case YELLOW_CAKE:
                image = R.drawable.yellowcake;
                break;
            case CHEESECAKE:
                image = R.drawable.cheesecake;
                break;
        }

        return image;

    }
}
