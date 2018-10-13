package com.workingtogether.workingtogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.obj.Activity;

import java.util.ArrayList;

public class ActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<ActivitiesRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Activity> mActivitiesDataset;
    private ActivitiesRecyclerViewAdapter.RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;
    private SparseBooleanArray mSelectedItems;

    public ActivitiesRecyclerViewAdapter(ActivitiesRecyclerViewAdapter.RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener, ArrayList<Activity> activityArrayList) {
        this.mActivitiesDataset = activityArrayList;
        this.mRecyclerViewOnItemClickListener = mRecyclerViewOnItemClickListener;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public ActivitiesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_activities_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleSelection(int pos) {
        if (mSelectedItems.get(pos, false)) {
            mSelectedItems.delete(pos);
        } else {
            mSelectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    public ArrayList<Activity> getmSelectedItems() {
        ArrayList<Activity> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mActivitiesDataset.get(i));
        }
        return items;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText(mActivitiesDataset.get(position).getTITLE());
        holder.mDescription.setText(mActivitiesDataset.get(position).getDESCRIPTION());
        holder.mDeliverDate.setText(mActivitiesDataset.get(position).getDELIVERDATE());
        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados
    }

    @Override
    public int getItemCount() {
        return mActivitiesDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mTitle;
        private TextView mDescription;
        private TextView mDeliverDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.activities_cardview_item_activity_title);
            mDescription = itemView.findViewById(R.id.activities_cardview_item_activity_description);
            mDeliverDate = itemView.findViewById(R.id.activities_cardview_item_activity_deliverdate);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mRecyclerViewOnItemClickListener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

    public interface RecyclerViewOnItemClickListener {
        void onClick(View v, int position);
        void onLongClick(View v, int position);
    }

}