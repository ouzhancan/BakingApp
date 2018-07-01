package com.udacity.ouz.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.udacity.ouz.bakingapp.adapters.RecipeItemAdapter;
import com.udacity.ouz.bakingapp.fragments.MediaPlayerFragment;
import com.udacity.ouz.bakingapp.fragments.RecipeListFragment;
import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.model.Step;
import com.udacity.ouz.bakingapp.util.RecipeTypes;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepActivity extends AppCompatActivity
        implements RecipeItemAdapter.OnStepItemClickListener{

    @BindView(R.id.layout_frame_container)
    LinearLayout frameContainer;

    private static Recipe selectedRecipe;
    private Boolean isTwoPane;
    private Boolean isLandscapeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Bundle bundle = getIntent().getExtras();

        if( bundle != null ){
            selectedRecipe = (Recipe)bundle.get(ScreenUtil.SELECTED_RECIPE_KEY);
            isLandscapeMode = (Boolean)bundle.get(ScreenUtil.SCREEN_MODE_KEY);
            isTwoPane = (Boolean)bundle.get(ScreenUtil.IS_TWO_PANE_KEY);

            if(selectedRecipe != null){
                if(frameContainer != null){
                    isTwoPane = true;

                    ButterKnife.bind(this);

                }else{
                    isTwoPane = false;
                }
            }else{
                Toast.makeText(this, getString(R.string.recipe_step_error), Toast.LENGTH_SHORT)
                        .show();
            }
        }
        // getScreenSize();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ScreenUtil.IS_TWO_PANE_KEY,isTwoPane);
        outState.putBoolean(ScreenUtil.SCREEN_MODE_KEY,isLandscapeMode);
        outState.putSerializable(ScreenUtil.SELECTED_RECIPE_KEY,selectedRecipe);
    }

    @Override
    public void onStepSelected(int id) {
        Toast.makeText(this,"gelen id : " +id,Toast.LENGTH_SHORT).show();

        String videoURL="";

        if(id >= 0){
            Step[] steps = selectedRecipe.getSteps();
            for(Step element : steps){
                if(element.getId() == id){
                    videoURL = element.getVideoURL();
                }
            }
        }

        if(isTwoPane){

            MediaPlayerFragment mediaPlayerFragment = MediaPlayerFragment.newInstance(
                    String.valueOf(id),videoURL );

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.media_container, mediaPlayerFragment)
                    .commit();

        }else{
            Intent intent = new Intent(this,StepDetailActivity.class);
            intent.putExtra(ScreenUtil.SELECTED_STEP_KEY,String.valueOf(id));
            intent.putExtra(ScreenUtil.SELECTED_VIDEO_URL_KEY,videoURL);
            startActivity(intent);

        }

    }

    public static Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    public void getScreenSize(){

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        float scaleFactor = metrics.density;

        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;

        float smallestWidth = Math.min(widthDp, heightDp);

        if (smallestWidth > 600) {
            isTwoPane = true;
        }else{
            isTwoPane = false;
        }
    }

}
