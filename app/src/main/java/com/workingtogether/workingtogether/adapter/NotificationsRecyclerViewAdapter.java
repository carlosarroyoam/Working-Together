package com.workingtogether.workingtogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.obj.Homework;
import com.workingtogether.workingtogether.obj.Notification;
import com.workingtogether.workingtogether.util.DateUtils;
import com.workingtogether.workingtogether.util.LocalParams;

import java.util.ArrayList;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Notification> mNotificationsDataset;
    private NotificationsRecyclerViewAdapter.RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;
    private SparseBooleanArray mSelectedItems;

    public NotificationsRecyclerViewAdapter(NotificationsRecyclerViewAdapter.RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener, ArrayList<Notification> notificationArrayList) {
        this.mNotificationsDataset = notificationArrayList;
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
        holder.mTitle.setText(mNotificationsDataset.get(position).getTITLE());
        holder.mDescription.setText(mNotificationsDataset.get(position).getDESCRIPTION());
        holder.mDeliverDate.setText(mNotificationsDataset.get(position).getPUBLISHDATE());
        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados

        if (mNotificationsDataset.get(position).getPUBLISHDATE().substring(0, 9).equals(DateUtils.getDateTime().substring(0, 9)))
            holder.mDeliverDate.setText(mNotificationsDataset.get(position).getPUBLISHDATE().substring(11, 21));
        else
            holder.mDeliverDate.setText(mNotificationsDataset.get(position).getPUBLISHDATE().substring(0, 10));

        if (mNotificationsDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.HOMEWORKNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_homeworks);
        } else if (mNotificationsDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.ACTIVITYNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_activities);
        } else if (mNotificationsDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.NOTESNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_notes);
        } else if (mNotificationsDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.MESSAGENOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_message);
        }
    }

    @Override
    public int getItemCount() {
        return mNotificationsDataset.size();
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

    public ArrayList<Notification> getmSelectedItems() {
        ArrayList<Notification> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mNotificationsDataset.get(i));
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

    public interface RecyclerViewOnItemClickListener {
        void onClick(View v, int position);
        void onLongClick(View v, int position);
    }

}