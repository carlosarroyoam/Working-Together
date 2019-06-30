package com.workingtogether.workingtogether.adapter.recyclerview;

import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.entity.Activity;

import java.util.ArrayList;

public class ActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<ActivitiesRecyclerViewAdapter.ViewHolder> {
    private ArrayList mActivitiesDataset;
    private OnItemClickListenerInterface mRecyclerViewOnItemClickListenerInterface;
    private SparseBooleanArray mSelectedItems;

    public ActivitiesRecyclerViewAdapter(OnItemClickListenerInterface onItemClickListenerInterface, ArrayList activityArrayList) {
        this.mActivitiesDataset = activityArrayList;
        this.mRecyclerViewOnItemClickListenerInterface = onItemClickListenerInterface;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public ActivitiesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_activities_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText(((Activity) mActivitiesDataset.get(position)).getTitle());
        holder.mDescription.setText(((Activity) mActivitiesDataset.get(position)).getDescription());
        holder.mDeliverDate.setText(((Activity) mActivitiesDataset.get(position)).getDeliveryDate());
        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados
    }

    @Override
    public int getItemCount() {
        return mActivitiesDataset.size();
    }

    public void clearItemSelection() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleItemSelectionState(int pos) {
        if (mSelectedItems.get(pos, false))
            mSelectedItems.delete(pos);
        else
            mSelectedItems.put(pos, true);

        notifyItemChanged(pos);
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    public ArrayList getSelectedItems() {
        ArrayList items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mActivitiesDataset.get(i));
        }
        return items;
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
            mRecyclerViewOnItemClickListenerInterface.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mRecyclerViewOnItemClickListenerInterface.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

}