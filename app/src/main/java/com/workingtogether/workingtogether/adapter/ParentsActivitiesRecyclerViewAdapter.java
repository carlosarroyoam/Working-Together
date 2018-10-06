package com.workingtogether.workingtogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.obj.Activity;
import java.util.ArrayList;

public class ParentsActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<ParentsActivitiesRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Activity> mActivitiesDataset;

    public ParentsActivitiesRecyclerViewAdapter(ArrayList<Activity> activityArrayList) {
        mActivitiesDataset = activityArrayList;
    }

    @Override
    public ParentsActivitiesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_activities_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mId.setText(Integer.toString(mActivitiesDataset.get(position).getUIDACTIVITY()));
        holder.mTitle.setText(mActivitiesDataset.get(position).getTITLE());
        holder.mDescription.setText(mActivitiesDataset.get(position).getDESCRIPTION());
        holder.mDeliverDate.setText(mActivitiesDataset.get(position).getDELIVERDATE());

    }

    @Override
    public int getItemCount() {
        return mActivitiesDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mId;
        private TextView mTitle;
        private TextView mDescription;
        private TextView mDeliverDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mId = itemView.findViewById(R.id.activities_cardview_item_activity_id);
            mTitle = itemView.findViewById(R.id.activities_cardview_item_activity_title);
            mDescription = itemView.findViewById(R.id.activities_cardview_item_activity_description);
            mDeliverDate = itemView.findViewById(R.id.activities_cardview_item_activity_deliverdate);
        }
    }
}