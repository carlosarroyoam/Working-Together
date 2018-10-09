package com.workingtogether.workingtogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.obj.Notification;
import com.workingtogether.workingtogether.util.DateUtils;
import com.workingtogether.workingtogether.util.LocalParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Notification> mNotificationsDataset;

    public NotificationsRecyclerViewAdapter(ArrayList<Notification> notificationArrayList) {
        mNotificationsDataset = notificationArrayList;
    }

    @Override
    public NotificationsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notifications_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mId.setText(Integer.toString(mNotificationsDataset.get(position).getUIDNOTIFICATION()));
        holder.mIdResourse.setText(Integer.toString(mNotificationsDataset.get(position).getUIDRESOURSE()));
        holder.mTitle.setText(mNotificationsDataset.get(position).getTITLE());
        holder.mDescription.setText(mNotificationsDataset.get(position).getDESCRIPTION());
        holder.mDeliverDate.setText(mNotificationsDataset.get(position).getPUBLISHDATE());

        if (mNotificationsDataset.get(position).getPUBLISHDATE().substring(0, 9).equals(DateUtils.getDateTime().substring(0, 9)))
            holder.mDeliverDate.setText(mNotificationsDataset.get(position).getPUBLISHDATE().substring(11, 16));
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mId;
        private TextView mIdResourse;
        private ImageView mImg;
        private TextView mTitle;
        private TextView mDescription;
        private TextView mDeliverDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mId = itemView.findViewById(R.id.notifications_cardview_item_notification_id);
            mIdResourse = itemView.findViewById(R.id.notifications_cardview_item_resourse_id);
            mImg = itemView.findViewById(R.id.img);
            mTitle = itemView.findViewById(R.id.notifications_cardview_item_notification_title);
            mDescription = itemView.findViewById(R.id.notification_cardview_item_notification_description);
            mDeliverDate = itemView.findViewById(R.id.notifications_cardview_item_notification_deliverdate);
        }
    }

}