package com.workingtogether.workingtogether.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.firebase.Notification;
import com.workingtogether.workingtogether.util.Util;

import java.util.ArrayList;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<com.workingtogether.workingtogether.models.Notification> mNotificationDataset;
    private RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;
    private SparseBooleanArray mSelectedItems;

    public NotificationsRecyclerViewAdapter(RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener, ArrayList<com.workingtogether.workingtogether.models.Notification> notificationArrayList) {
        this.mNotificationDataset = notificationArrayList;
        this.mRecyclerViewOnItemClickListener = mRecyclerViewOnItemClickListener;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public NotificationsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notifications_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText(mNotificationDataset.get(position).getTITLE());
        holder.mDescription.setText(mNotificationDataset.get(position).getDESCRIPTION());
        holder.mDeliverDate.setText(mNotificationDataset.get(position).getPUBLISHDATE());
        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados

        if (mNotificationDataset.get(position).getPUBLISHDATE().substring(0, 9).equals(Util.Date.getDateTime().substring(0, 9)))
            holder.mDeliverDate.setText(mNotificationDataset.get(position).getPUBLISHDATE().substring(11, 16));
        else
            holder.mDeliverDate.setText(mNotificationDataset.get(position).getPUBLISHDATE().substring(0, 10));

        if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.HOMEWORKNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_homeworks);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.ACTIVITYNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_activities);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.NOTESNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_notes);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.MESSAGENOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_message);
        }
    }

    @Override
    public int getItemCount() {
        return mNotificationDataset.size();
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

    public ArrayList<com.workingtogether.workingtogether.models.Notification> getmSelectedItems() {
        ArrayList<com.workingtogether.workingtogether.models.Notification> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mNotificationDataset.get(i));
        }
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView mImg;
        private TextView mTitle;
        private TextView mDescription;
        private TextView mDeliverDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.img);
            mTitle = itemView.findViewById(R.id.notifications_cardview_item_notification_title);
            mDescription = itemView.findViewById(R.id.notification_cardview_item_notification_description);
            mDeliverDate = itemView.findViewById(R.id.notifications_cardview_item_notification_deliverdate);

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