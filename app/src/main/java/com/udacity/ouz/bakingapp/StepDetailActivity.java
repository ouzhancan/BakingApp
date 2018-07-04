package com.udacity.ouz.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.ouz.bakingapp.fragments.MediaPlayerFragment;
import com.udacity.ouz.bakingapp.fragments.StepInstructorFragment;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

public class StepDetailActivity extends AppCompatActivity {

    String selectedStepId;
    String selectedVideoURL;
    String selectedStepInstruction;
    Boolean isTwoPane;
    Boolean isLandscapeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        if(getIntent().getExtras()!=null)
        {
            Bundle bundle = getIntent().getExtras();
            selectedStepId = bundle.getString(ScreenUtil.SELECTED_STEP_KEY);
            selectedVideoURL = bundle.getString(ScreenUtil.SELECTED_VIDEO_URL_KEY);
            selectedStepInstruction = bundle.getString(ScreenUtil.SELECTED_STEP_INSTRUCTION);
            isTwoPane = bundle.getBoolean(ScreenUtil.IS_TWO_PANE_KEY);
            isLandscapeMode = bundle.getBoolean(ScreenUtil.SCREEN_MODE_KEY);

        }

        if(savedInstanceState == null){

            FragmentManager fragmentManager = getSupportFragmentManager();


            MediaPlayerFragment mediaPlayerFragment = MediaPlayerFragment.newInstance(
                    selectedStepId, selectedVideoURL);

            fragmentManager.beginTransaction()
                    .add(R.id.media_container, mediaPlayerFragment)
                    .commit();

            StepInstructorFragment stepInstructorFragment =
                    StepInstructorFragment.newInstance(selectedStepId,isTwoPane,
                                selectedStepInstruction);

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container,stepInstructorFragment)
                    .commit();
        }
    }
}
