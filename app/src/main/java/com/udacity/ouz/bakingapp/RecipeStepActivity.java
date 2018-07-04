package com.udacity.ouz.bakingapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.udacity.ouz.bakingapp.adapters.RecipeItemAdapter;
import com.udacity.ouz.bakingapp.fragments.MediaPlayerFragment;
import com.udacity.ouz.bakingapp.fragments.StepInstructorFragment;
import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.model.Step;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepActivity extends AppCompatActivity
        implements RecipeItemAdapter.OnStepItemClickListener{

    @BindView(R.id.layout_frame_container)
    @Nullable
    LinearLayout frameContainer;

    private Recipe selectedRecipe;
    private Boolean isTwoPane;
    private Boolean isLandscapeMode;
    private int selectedStepId;
    private String selectedVideoURL;
    private String selectedStepInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Bundle bundle = getIntent().getExtras();

        if( bundle != null ){
            selectedRecipe = (Recipe)bundle.get(ScreenUtil.SELECTED_RECIPE_KEY);
            isLandscapeMode = (Boolean)bundle.get(ScreenUtil.SCREEN_MODE_KEY);
            isTwoPane = (Boolean)bundle.get(ScreenUtil.IS_TWO_PANE_KEY);

            ButterKnife.bind(this);

            if(selectedRecipe != null){
                if(frameContainer != null){
                    isTwoPane = true;

                    loadFragmentData();

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
        outState.putString(ScreenUtil.SELECTED_STEP_KEY,String.valueOf(selectedStepId));
        outState.putString(ScreenUtil.SELECTED_STEP_INSTRUCTION,selectedStepInstruction);
    }

    @Override
    public void onStepSelected(int id) {
        Toast.makeText(this,"gelen step id : " +id,Toast.LENGTH_SHORT).show();

        setSelectedStepId(id);
        Step step = findStepById(Integer.valueOf(selectedStepId));
        setSelectedVideoURL(step.getVideoURL());
        setSelectedStepInstruction(step.getDescription());


        if(isTwoPane){

            MediaPlayerFragment mediaPlayerFragment = MediaPlayerFragment.newInstance(
                    String.valueOf(getSelectedStepId()), getSelectedVideoURL());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.media_container, mediaPlayerFragment)
                    .commit();


            StepInstructorFragment stepDetailFragment =
                    StepInstructorFragment.newInstance(String.valueOf(getSelectedStepId()),
                                isTwoPane,selectedStepInstruction);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container,stepDetailFragment)
                    .commit();


        }else{
            Intent intent = new Intent(this,StepDetailActivity.class);
            intent.putExtra(ScreenUtil.SELECTED_STEP_KEY,String.valueOf(getSelectedStepId()));
            intent.putExtra(ScreenUtil.SELECTED_VIDEO_URL_KEY,getSelectedVideoURL());
            intent.putExtra(ScreenUtil.IS_TWO_PANE_KEY,isTwoPane);
            intent.putExtra(ScreenUtil.SCREEN_MODE_KEY,isLandscapeMode);
            intent.putExtra(ScreenUtil.SELECTED_STEP_INSTRUCTION,getSelectedStepInstruction());
            startActivity(intent);

        }

    }

    public Step findStepById(int id){

        Step step = null;

        if(id >= 0 && selectedRecipe != null){
            Step[] steps = selectedRecipe.getSteps();
            for(Step element : steps){
                if(element.getId() == selectedStepId){
                    step = element;
                    break;
                }
            }
        }

        return step;
    }

    private void loadFragmentData(){


        FragmentManager fragmentManager = getSupportFragmentManager();

        MediaPlayerFragment mediaPlayerFragment = MediaPlayerFragment.newInstance(
                String.valueOf(getSelectedStepId()), getSelectedVideoURL());

        fragmentManager.beginTransaction()
                .add(R.id.media_container, mediaPlayerFragment)
                .commit();


        StepInstructorFragment stepDetailFragment =
                StepInstructorFragment.newInstance(String.valueOf(getSelectedStepId()),
                        isTwoPane,selectedStepInstruction);

        fragmentManager.beginTransaction()
                .add(R.id.step_detail_container,stepDetailFragment)
                .commit();

    }

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    public int getSelectedStepId() {
        return this.selectedStepId;
    }

    public void setSelectedStepId(int selectedStepId) {
        this.selectedStepId = selectedStepId;
    }

    public String getSelectedVideoURL() {
        return selectedVideoURL;
    }

    public void setSelectedVideoURL(String selectedVideoURL) {
        this.selectedVideoURL = selectedVideoURL;
    }

    public String getSelectedStepInstruction() {
        return selectedStepInstruction;
    }

    public void setSelectedStepInstruction(String selectedStepInstruction) {
        this.selectedStepInstruction = selectedStepInstruction;
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
