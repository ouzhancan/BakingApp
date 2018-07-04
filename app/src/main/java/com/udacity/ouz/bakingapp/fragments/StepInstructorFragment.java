package com.udacity.ouz.bakingapp.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.ouz.bakingapp.R;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepInstructorFragment extends Fragment {

    private static final String TAG = "RECIPE_STEP_DETAIL_FRAGMENT";

    private String mStepId;
    String stepInstruction;

    private Context context;

    private boolean isTwoPane;

    @BindView(R.id.tv_step_instruction)
    TextView tvStepInstruction;

    public StepInstructorFragment() {
        // Required empty public constructor
    }

    public static StepInstructorFragment newInstance(String stepId,boolean isTwoPane,
                                                     String stepInstruction) {
        StepInstructorFragment fragment = new StepInstructorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ScreenUtil.SELECTED_STEP_KEY, stepId);
        bundle.putBoolean(ScreenUtil.IS_TWO_PANE_KEY, isTwoPane);
        bundle.putString(ScreenUtil.SELECTED_STEP_INSTRUCTION, stepInstruction);

        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepId = getArguments().getString(ScreenUtil.SELECTED_STEP_KEY);
            isTwoPane = getArguments().getBoolean(ScreenUtil.IS_TWO_PANE_KEY);
            stepInstruction = getArguments().getString(ScreenUtil.SELECTED_STEP_INSTRUCTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_step_instruction, container,
                false);

        ButterKnife.bind(this, rootView);

        tvStepInstruction.setText(stepInstruction);


        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(ScreenUtil.SELECTED_STEP_KEY, mStepId);
        outState.putBoolean(ScreenUtil.IS_TWO_PANE_KEY, isTwoPane);
        outState.putString(ScreenUtil.SELECTED_STEP_INSTRUCTION, stepInstruction);
    }
}
