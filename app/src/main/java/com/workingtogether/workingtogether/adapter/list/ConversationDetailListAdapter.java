package com.workingtogether.workingtogether.adapter.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.models.Message;
import com.workingtogether.workingtogether.models.SessionApp;
import com.workingtogether.workingtogether.models.User;
import com.workingtogether.workingtogether.models.dao.SessionDAO;
import com.workingtogether.workingtogether.util.Util;

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

        SessionDAO sessionDAO = new SessionDAO(mContext);
        SessionApp userLogged = sessionDAO.getUserlogged();

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
            if (userLogged.getTYPEUSER().equals(User.UserTypes.PARENTUSER)) {
                holder.mImg.setImageResource(R.drawable.ic_teacher);
            } else if (userLogged.getTYPEUSER().equals(User.UserTypes.TEACHERUSER)) {
                holder.mImg.setImageResource(R.drawable.ic_student);
            }
        }

        if (mMessagesDataset.get(position).getSENDSTATE() == 1) {
            holder.mDate = convertView.findViewById(R.id.message_date);

            if (mMessagesDataset.get(position).getSENDDATE().substring(0, 9).equals(Util.Date.getDateTime().substring(0, 9)))
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
