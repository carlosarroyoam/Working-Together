package com.workingtogether.android.adapter.drawer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.android.R;
import com.workingtogether.android.activities.ActivitiesActivity;
import com.workingtogether.android.activities.ConversationsActivity;
import com.workingtogether.android.activities.HomeworksActivity;
import com.workingtogether.android.activities.parent.ParentNotes;
import com.workingtogether.android.firebase.NotificationsBuilder;
import com.workingtogether.android.util.DatesUtils;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class NotificationsDrawerAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<com.workingtogether.android.entity.Notification> mNotificationDataset;

    public NotificationsDrawerAdapter(Context context, ArrayList<com.workingtogether.android.entity.Notification> notificationArrayList) {
        this.mContext = context.getApplicationContext();
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


        if (mNotificationDataset.get(position).getCreatedAt().substring(0, 9).equals(DatesUtils.getDateTime().substring(0, 9)))
            mDeliverDate.setText(mNotificationDataset.get(position).getCreatedAt().substring(11, 16));
        else
            mDeliverDate.setText(mNotificationDataset.get(position).getCreatedAt().substring(0, 10));


        switch (mNotificationDataset.get(position).getNOTIFICATIONTYPE()) {
            case NotificationsBuilder.HOMEWORK_NOTIFICATION:
                mImg.setImageResource(R.drawable.ic_homeworks);
                break;
            case NotificationsBuilder.ACTIVITY_NOTIFICATION:
                mImg.setImageResource(R.drawable.ic_activities);
                break;
            case NotificationsBuilder.NOTES_NOTIFICATION:
                mImg.setImageResource(R.drawable.ic_notes);
                break;
            case NotificationsBuilder.MESSAGE_NOTIFICATION:
                mImg.setImageResource(R.drawable.ic_message);
                break;
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mNotificationDataset.get(position).getNOTIFICATIONTYPE()) {
                    case NotificationsBuilder.HOMEWORK_NOTIFICATION:
                        mContext.startActivity(new Intent(mContext, HomeworksActivity.class));
                        break;
                    case NotificationsBuilder.ACTIVITY_NOTIFICATION:
                        mContext.startActivity(new Intent(mContext, ActivitiesActivity.class));
                        break;
                    case NotificationsBuilder.NOTES_NOTIFICATION:
                        mContext.startActivity(new Intent(mContext, ParentNotes.class));
                        break;
                    case NotificationsBuilder.MESSAGE_NOTIFICATION:
                        mContext.startActivity(new Intent(mContext, ConversationsActivity.class));
                        break;
                }
            }
        });

        return row;
    }
    
}
