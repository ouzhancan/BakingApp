package com.udacity.ouz.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.udacity.ouz.bakingapp.fragments.MediaPlayerFragment;
import com.udacity.ouz.bakingapp.fragments.StepInstructorFragment;
import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.model.Step;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity implements View.OnClickListener {

    String selectedStepId;
    String selectedVideoURL;
    String selectedStepInstruction;
    Boolean isTwoPane;
    Boolean isLandscapeMode;
    Recipe selectedRecipe;
    Step selectedStep;
    Step[] stepList;

    @BindView(R.id.btn_next)
    Button nextButton;

    @BindView(R.id.btn_previous)
    Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        getIntentExtras();

        reloadFragments(savedInstanceState);

    }

    private void getIntentExtras() {

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            selectedStepId = bundle.getString(ScreenUtil.SELECTED_STEP_KEY);
            selectedRecipe = (Recipe) bundle.get(ScreenUtil.SELECTED_RECIPE_KEY);
            selectedVideoURL = bundle.getString(ScreenUtil.SELECTED_VIDEO_URL_KEY);
            selectedStepInstruction = bundle.getString(ScreenUtil.SELECTED_STEP_INSTRUCTION);
            isTwoPane = bundle.getBoolean(ScreenUtil.IS_TWO_PANE_KEY);
            isLandscapeMode = bundle.getBoolean(ScreenUtil.SCREEN_MODE_KEY);

            if(selectedRecipe.getSteps() != null && selectedRecipe.getSteps().length > 1){
                stepList = selectedRecipe.getSteps();
            }

        }
    }

    private void reloadFragments(Bundle savedInstanceState) {

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();


            MediaPlayerFragment mediaPlayerFragment = MediaPlayerFragment.newInstance(
                    selectedStepId, selectedVideoURL);
            mediaPlayerFragment.setContext(this);

            fragmentManager.beginTransaction()
                    .add(R.id.media_container, mediaPlayerFragment)
                    .commit();

            StepInstructorFragment stepInstructorFragment =
                    StepInstructorFragment.newInstance(selectedStepId, isTwoPane,
                            selectedStepInstruction);

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepInstructorFragment)
                    .commit();

            ButterKnife.bind(this);

            nextButton.setOnClickListener(this);
            previousButton.setOnClickListener(this);
        }else{

            MediaPlayerFragment mediaPlayerFragment = MediaPlayerFragment.newInstance(
                    selectedStepId, selectedVideoURL);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.media_container, mediaPlayerFragment)
                    .commit();


            StepInstructorFragment stepInstructorFragment =
                    StepInstructorFragment.newInstance(selectedStepId,
                            isTwoPane,selectedStepInstruction);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container,stepInstructorFragment)
                    .commit();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_previous:
                selectedStepId = String.valueOf(Integer.valueOf(selectedStepId) - 1);
                break;
            case R.id.btn_next:
                selectedStepId = String.valueOf(Integer.valueOf(selectedStepId) + 1);
                break;
        }

        validateStepIndex();
        modifyToActivity();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ScreenUtil.SELECTED_STEP_KEY, selectedStepId);
        outState.putSerializable(ScreenUtil.SELECTED_RECIPE_KEY, selectedRecipe);
        outState.putString(ScreenUtil.SELECTED_VIDEO_URL_KEY, selectedVideoURL);
        outState.putString(ScreenUtil.SELECTED_STEP_INSTRUCTION, selectedStepInstruction);
        outState.putBoolean(ScreenUtil.IS_TWO_PANE_KEY, isTwoPane);
        outState.putBoolean(ScreenUtil.SCREEN_MODE_KEY, isLandscapeMode);
    }

    private void validateStepIndex() {

        previousButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);

        if (Integer.valueOf(selectedStepId) <= 0) {
            selectedStep = stepList[0];
            previousButton.setVisibility(View.INVISIBLE);

        } else if (Integer.valueOf(selectedStepId) >= stepList.length - 1) {
            selectedStep = stepList[stepList.length - 1];
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            for (Step element : stepList) {
                if (element.getId() == Integer.valueOf(selectedStepId)) {
                    selectedStep = element;
                    break;
                }
            }
        }

        selectedStepId = String.valueOf(selectedStep.getId());
        selectedVideoURL = selectedStep.getVideoURL();
        selectedStepInstruction = selectedStep.getDescription();
    }

    private void modifyToActivity(){
        Bundle bundle = new Bundle();

        bundle.putString(ScreenUtil.SELECTED_STEP_KEY, selectedStepId);
        bundle.putSerializable(ScreenUtil.SELECTED_RECIPE_KEY, selectedRecipe);
        bundle.putString(ScreenUtil.SELECTED_VIDEO_URL_KEY, selectedVideoURL);
        bundle.putString(ScreenUtil.SELECTED_STEP_INSTRUCTION, selectedStepInstruction);
        bundle.putBoolean(ScreenUtil.IS_TWO_PANE_KEY, isTwoPane);
        bundle.putBoolean(ScreenUtil.SCREEN_MODE_KEY, isLandscapeMode);

        reloadFragments(bundle);
    }
}
