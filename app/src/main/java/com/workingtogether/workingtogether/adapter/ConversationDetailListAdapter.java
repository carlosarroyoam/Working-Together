package com.workingtogether.workingtogether.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.workingtogether.Activities;
import com.workingtogether.workingtogether.Homeworks;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.obj.Message;
import com.workingtogether.workingtogether.obj.Notification;
import com.workingtogether.workingtogether.obj.SessionApp;
import com.workingtogether.workingtogether.parent.ParentNotes;
import com.workingtogether.workingtogether.util.DateUtils;
import com.workingtogether.workingtogether.util.LocalParams;

import java.util.ArrayList;

public class ConversationDetailListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Message> mMessagesDataset;

    public ConversationDetailListAdapter(Context context, ArrayList<Message> notificationArrayList) {
        this.mContext = context;
        this.mMessagesDataset = notificationArrayList;
    }

    @Override
    public int getCount() {
        return mMessagesDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessagesDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMessagesDataset.get(position).getUIDMESSAGE();
    }

    public void add(Message message) {
        this.mMessagesDataset.add(message);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = mMessagesDataset.get(position);

        SessionDB sessionDB = new SessionDB(mContext);
        SessionApp userLogged = sessionDB.getUserlogged();

        if (userLogged.getUIDUSER() == mMessagesDataset.get(position).getUIDUSERFROM()) {
            convertView = messageInflater.inflate(R.layout.layout_my_message, null);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getDATA());
        } else {
            convertView = messageInflater.inflate(R.layout.layout_their_message, null);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);

            holder.messageBody.setText(message.getDATA());

            holder.mImg = convertView.findViewById(R.id.contact_profile_picture);
            if (userLogged.getTYPEUSER().equals(LocalParams.PARENTUSER)) {
                holder.mImg.setImageResource(R.drawable.ic_teacher);
            } else if (userLogged.getTYPEUSER().equals(LocalParams.TEACHERUSER)) {
                holder.mImg.setImageResource(R.drawable.ic_student);
            }
        }

        if (mMessagesDataset.get(position).getSENDSTATE() == 1) {
            holder.mDate = convertView.findViewById(R.id.message_date);

            if (mMessagesDataset.get(position).getSENDDATE().substring(0, 9).equals(DateUtils.getDateTime().substring(0, 9)))
                holder.mDate.setText(mMessagesDataset.get(position).getSENDDATE().substring(11, 16));
            else
                holder.mDate.setText(mMessagesDataset.get(position).getSENDDATE());
        }

        return convertView;

    }

    private class MessageViewHolder {
        public ImageView mImg;
        public TextView messageBody;
        public TextView mDate;
    }

}
