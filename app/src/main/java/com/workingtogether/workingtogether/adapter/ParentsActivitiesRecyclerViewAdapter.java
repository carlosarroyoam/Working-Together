package com.workingtogether.workingtogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;

public class ParentsActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<ParentsActivitiesRecyclerViewAdapter.ViewHolder> {
    private String[] mActivitiesDataset;

    public ParentsActivitiesRecyclerViewAdapter() {
        String[] fromDBDataset = { "Actividad 1", "Actividad 2", "Actividad 3"};//Cargar desde db
        mActivitiesDataset = fromDBDataset;
    }

    @Override
    public ParentsActivitiesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_activities_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mActivitiesDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mActivitiesDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.activities_cardview_item_activity_title);
        }
    }
}