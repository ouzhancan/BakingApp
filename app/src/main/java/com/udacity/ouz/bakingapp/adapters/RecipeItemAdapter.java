package com.udacity.ouz.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.ouz.bakingapp.R;
import com.udacity.ouz.bakingapp.RecipeStepActivity;
import com.udacity.ouz.bakingapp.model.Recipe;
import com.udacity.ouz.bakingapp.model.Step;
import com.udacity.ouz.bakingapp.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.ItemViewHolder> {

    Context context;
    Recipe recipe;
    Step[] steps;

    public RecipeItemAdapter(Context context, Step[] steps) {
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view;

        view = inflater.inflate(R.layout.step_items,parent,false);

        RecipeItemAdapter.ItemViewHolder viewHolder = new RecipeItemAdapter.ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.step_desc.setText(steps[position].getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.length;
    }

    @Override
    public long getItemId(int position) {
        return steps.length>0?new Long(steps[position].getId()):0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_step_desc)
        TextView step_desc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick
        public void onClick() {

            String clickedId = step_desc.getText().toString();

            Log.d(getClass().getName(),"Clicked recipe step desc is : "+clickedId);

        }
    }

    public void setData(Step[] steps) {

        if (steps != null && steps.length > 0) {
            this.steps = steps;
        }

        notifyDataSetChanged();
    }
}
