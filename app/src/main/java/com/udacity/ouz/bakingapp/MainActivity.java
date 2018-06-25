package com.udacity.ouz.bakingapp;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.ouz.bakingapp.adapters.RecipeAdapter;
import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.util.NetworkUtil;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {

    private static final int LOADER_ID = 19;

    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_error_msg)
    TextView mErrorMsgTextView;

    @BindView(R.id.pb_loading)
    ProgressBar mProgressBar;

    @BindString(R.string.no_data_error_message)
    String no_data_error;
    @BindString(R.string.connection_error_message)
    String no_connection_error;

    @BindInt(R.integer.grid_layout_column_num)
    int verticalCardNum;

    @BindInt(R.integer.grid_horizontal_column_num)
    int horizontalCardNum;

    RecipeAdapter mRecipeAdapter;

    ArrayList<Recipe> recipeList;

    Boolean isLandscapeMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getOrientation(savedInstanceState);

        setLayoutColumns();

        // set custom adapter to recycler view
        mRecipeAdapter = new RecipeAdapter(this, recipeList, isLandscapeMode);
        mRecyclerView.setAdapter(mRecipeAdapter);

        getRecipes();
    }

    private void getOrientation(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            isLandscapeMode = savedInstanceState.getBoolean(ScreenUtil.SCREEN_MODE_KEY);
        }

        // get device current orientation
        isLandscapeMode = (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE ? true : false);

    }

    private void setLayoutColumns() {

        GridLayoutManager layoutManager = null;
        if (isLandscapeMode) {
            layoutManager = new GridLayoutManager(this, horizontalCardNum);
        } else {
            layoutManager = new GridLayoutManager(this, verticalCardNum);
        }

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);
    }

    private void getRecipes() {
        getRecipesThroughNetwork();
    }

    private void getRecipesThroughNetwork() {
        if (NetworkUtil.isThereAConnection(this)) {
            if (!loaderWasStarted()) {
                getSupportLoaderManager().initLoader(LOADER_ID, null, this);
            } else {
                getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            }
        } else {
            showErrorMessage(no_connection_error);
        }
    }

    /**
     * if the loader was started, it doesn't need to start again.
     *
     * @return
     */
    private boolean loaderWasStarted() {

        if (getSupportLoaderManager().getLoader(LOADER_ID) != null &&
                getSupportLoaderManager().getLoader(LOADER_ID).isStarted()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ScreenUtil.SCREEN_MODE_KEY, isLandscapeMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        isLandscapeMode = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ?
                true : false);

        setLayoutColumns();

        mRecipeAdapter = new RecipeAdapter(this, recipeList, isLandscapeMode);
        mRecyclerView.setAdapter(mRecipeAdapter);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {

            ArrayList<Recipe> recipeList;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if (recipeList == null) {
                    showLoadingOnView();
                    forceLoad();
                } else {
                    deliverResult(recipeList);
                }
            }

            @Nullable
            @Override
            public ArrayList<Recipe> loadInBackground() {
                return NetworkUtil.getAllRecipes();
            }

            @Override
            public void deliverResult(ArrayList<Recipe> data) {
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        if (data != null) {
            showDataOnView();
            recipeList = data;
            mRecipeAdapter.setData(data);
        } else {

            showErrorMessage(no_data_error);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Recipe>> loader) {

    }


    private void showDataOnView() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMsgTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoadingOnView() {
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorMsgTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage(String errorMsg) {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMsgTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        mErrorMsgTextView.setText(errorMsg);
    }

}
