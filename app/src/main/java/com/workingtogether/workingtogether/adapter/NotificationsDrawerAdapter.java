package com.workingtogether.workingtogether.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.obj.Notification;
import com.workingtogether.workingtogether.util.LocalParams;

import java.util.ArrayList;

public class NotificationsDrawerAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Notification> mNotificationsDataset;

    public NotificationsDrawerAdapter(Context context, ArrayList<Notification> notificationArrayList) {
        this.mContext = context;
        mNotificationsDataset = notificationArrayList;
    }

    @Override
    public int getCount() {
        return mNotificationsDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotificationsDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mNotificationsDataset.get(position).getUIDNOTIFICATION();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_notifications_cardview_item, parent, false );
        } else {
            row = convertView;
        }

        TextView mId = row.findViewById(R.id.notifications_cardview_item_notification_id);
        TextView mIdResourse = row.findViewById(R.id.notifications_cardview_item_resourse_id);
        ImageView mImg = row.findViewById(R.id.img);
        TextView mTitle = row.findViewById(R.id.notifications_cardview_item_notification_title);
        TextView mDescription = row.findViewById(R.id.notification_cardview_item_notification_description);
        TextView mDeliverDate = row.findViewById(R.id.notifications_cardview_item_notification_deliverdate);

        mId.setText(Integer.toString(mNotificationsDataset.get(position).getUIDNOTIFICATION()));
        mIdResourse.setText(Integer.toString(mNotificationsDataset.get(position).getUIDRESOURSE()));
        mTitle.setText(mNotificationsDataset.get(position).getTITLE());
        mDescription.setText(mNotificationsDataset.get(position).getDESCRIPTION());
        mDeliverDate.setText(mNotificationsDataset.get(position).getPUBLISHDATE());

        if (mNotificationsDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.HOMEWORKNOTIFICATION)) {
            mImg.setImageResource(R.drawable.ic_homeworks);
        } else if (mNotificationsDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.ACTIVITYNOTIFICATION)) {
            mImg.setImageResource(R.drawable.ic_activities);
        } else if (mNotificationsDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.NOTESNOTIFICATION)) {
            mImg.setImageResource(R.drawable.ic_notes);
        } else if (mNotificationsDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.MESSAGENOTIFICATION)) {
            mImg.setImageResource(R.drawable.ic_message);
        }

        return row;
    }

}
