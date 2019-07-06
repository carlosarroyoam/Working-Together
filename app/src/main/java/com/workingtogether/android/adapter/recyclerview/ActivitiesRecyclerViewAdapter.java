package com.workingtogether.android.adapter.recyclerview;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workingtogether.android.R;
import com.workingtogether.android.entity.Activity;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class ActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<ActivitiesRecyclerViewAdapter.ViewHolder> {
    private ArrayList mActivitiesDataset;
    private OnItemClickListener mRecyclerViewOnItemClickListener;
    private SparseBooleanArray mSelectedItems;

    public ActivitiesRecyclerViewAdapter(OnItemClickListener onItemClickListener, ArrayList activityArrayList) {
        this.mActivitiesDataset = activityArrayList;
        this.mRecyclerViewOnItemClickListener = onItemClickListener;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ActivitiesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_activities_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        ViewHolder(View itemView) {
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

}