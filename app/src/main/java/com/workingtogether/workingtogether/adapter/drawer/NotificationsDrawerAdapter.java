package com.workingtogether.workingtogether.adapter.drawer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.activities.ActivitiesActivity;
import com.workingtogether.workingtogether.activities.ConversationsActivity;
import com.workingtogether.workingtogether.activities.HomeworksActivity;
import com.workingtogether.workingtogether.firebase.Notification;
import com.workingtogether.workingtogether.parent.ParentNotes;
import com.workingtogether.workingtogether.util.Util;

import java.util.ArrayList;

public class NotificationsDrawerAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<com.workingtogether.workingtogether.models.Notification> mNotificationDataset;

    public NotificationsDrawerAdapter(Context context, ArrayList<com.workingtogether.workingtogether.models.Notification> notificationArrayList) {
        this.mContext = context;
        this.mNotificationDataset = notificationArrayList;
    }

    @Override
    public int getCount() {
        return mNotificationDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotificationDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mNotificationDataset.get(position).getUIDNOTIFICATION();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_notifications_cardview_item, parent, false);
        } else {
            row = convertView;
        }

        ImageView mImg = row.findViewById(R.id.img);
        TextView mTitle = row.findViewById(R.id.notifications_cardview_item_notification_title);
        TextView mDescription = row.findViewById(R.id.notification_cardview_item_notification_description);
        TextView mDeliverDate = row.findViewById(R.id.notifications_cardview_item_notification_deliverdate);

        mTitle.setText(mNotificationDataset.get(position).getTITLE());
        mDescription.setText(mNotificationDataset.get(position).getDESCRIPTION());


        if (mNotificationDataset.get(position).getPUBLISHDATE().substring(0, 9).equals(Util.Date.getDateTime().substring(0, 9)))
            mDeliverDate.setText(mNotificationDataset.get(position).getPUBLISHDATE().substring(11, 16));
        else
            mDeliverDate.setText(mNotificationDataset.get(position).getPUBLISHDATE().substring(0, 10));


        if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.HOMEWORKNOTIFICATION)) {
            mImg.setImageResource(R.drawable.ic_homeworks);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.ACTIVITYNOTIFICATION)) {
            mImg.setImageResource(R.drawable.ic_activities);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.NOTESNOTIFICATION)) {
            mImg.setImageResource(R.drawable.ic_notes);
        } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.MESSAGENOTIFICATION)) {
            mImg.setImageResource(R.drawable.ic_message);
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.HOMEWORKNOTIFICATION)) {
                    mContext.startActivity(new Intent(mContext, HomeworksActivity.class));
                } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.ACTIVITYNOTIFICATION)) {
                    mContext.startActivity(new Intent(mContext, ActivitiesActivity.class));
                } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.NOTESNOTIFICATION)) {
                    mContext.startActivity(new Intent(mContext, ParentNotes.class));
                } else if (mNotificationDataset.get(position).getNOTIFICATIONTYPE().equals(Notification.NOTESNOTIFICATION)) {
                    mContext.startActivity(new Intent(mContext, ConversationsActivity.class));
                }
            }
        });

        return row;
    }
    
}
