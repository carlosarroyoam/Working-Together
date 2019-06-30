package com.workingtogether.workingtogether.adapter.recyclerview;

import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.firebase.NotificationsBuilder;
import com.workingtogether.workingtogether.util.DatesUtils;

import java.util.ArrayList;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<com.workingtogether.workingtogether.entity.Notification> mNotificationDataset;
    private OnItemClickListenerInterface mOnItemClickListenerInterface;
    private SparseBooleanArray mSelectedItems;

    public NotificationsRecyclerViewAdapter(OnItemClickListenerInterface mOnItemClickListenerInterface, ArrayList<com.workingtogether.workingtogether.entity.Notification> notificationArrayList) {
        this.mNotificationDataset = notificationArrayList;
        this.mOnItemClickListenerInterface = mOnItemClickListenerInterface;
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
        holder.mDeliverDate.setText(mNotificationDataset.get(position).getCreatedAt());
        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados

        if (mNotificationDataset.get(position).getCreatedAt().substring(0, 9).equals(DatesUtils.getDateTime().substring(0, 9)))
            holder.mDeliverDate.setText(mNotificationDataset.get(position).getCreatedAt().substring(11, 16));
        else
            holder.mDeliverDate.setText(mNotificationDataset.get(position).getCreatedAt().substring(0, 10));

        if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(NotificationsBuilder.HOMEWORKNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_homeworks);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(NotificationsBuilder.ACTIVITYNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_activities);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(NotificationsBuilder.NOTESNOTIFICATION)) {
            holder.mImg.setImageResource(R.drawable.ic_notes);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(NotificationsBuilder.MESSAGENOTIFICATION)) {
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

    public ArrayList<com.workingtogether.workingtogether.entity.Notification> getmSelectedItems() {
        ArrayList<com.workingtogether.workingtogether.entity.Notification> items = new ArrayList<>(mSelectedItems.size());
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
            mOnItemClickListenerInterface.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mOnItemClickListenerInterface.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

}