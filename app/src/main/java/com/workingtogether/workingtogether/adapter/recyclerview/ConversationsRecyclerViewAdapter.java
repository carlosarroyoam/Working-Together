package com.workingtogether.workingtogether.adapter.recyclerview;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.entity.Conversation;
import com.workingtogether.workingtogether.entity.Message;
import com.workingtogether.workingtogether.entity.SessionApp;
import com.workingtogether.workingtogether.entity.User;
import com.workingtogether.workingtogether.entity.dao.MessagesDAO;
import com.workingtogether.workingtogether.entity.dao.SessionDAO;
import com.workingtogether.workingtogether.entity.dao.UserDAO;
import com.workingtogether.workingtogether.util.DatesUtils;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class ConversationsRecyclerViewAdapter extends RecyclerView.Adapter<ConversationsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Conversation> mConversationsDataset;
    private OnItemClickListener mRecyclerViewOnItemClickListener;
    private SparseBooleanArray mSelectedItems;
    private Context mContext;

    public ConversationsRecyclerViewAdapter(Context context, OnItemClickListener mRecyclerViewOnItemClickListener, ArrayList<Conversation> conversationArrayList) {
        this.mContext = context;
        this.mConversationsDataset = conversationArrayList;
        this.mRecyclerViewOnItemClickListener = mRecyclerViewOnItemClickListener;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public ConversationsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_conversations_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = getLastMessage(mConversationsDataset.get(position).getId());
        User conversationUser = getUser(mConversationsDataset.get(position).getIdUser());
        SessionDAO sessionDAO = new SessionDAO(mContext);

        SessionApp userLogged = sessionDAO.getUserlogged();

        holder.mContactName.setText(conversationUser.getFirstName() + " " + conversationUser.getLastName());
        if (userLogged.getUIDUSER() == conversationUser.getId())
            holder.mLastMessageContent.setText("Tu: " + message.getDATA());
        else
            holder.mLastMessageContent.setText(message.getDATA());

        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados

        if (message.getSENDSTATE()) {
            if (message.getSENDDATE().substring(0, 9).equals(DatesUtils.getDateTime().substring(0, 9)))
                holder.mLastMessageDate.setText(message.getSENDDATE().substring(11, 16));
            else
                holder.mLastMessageDate.setText(message.getSENDDATE().substring(0, 10));
        }


    }

    @Override
    public int getItemCount() {
        return mConversationsDataset.size();
    }

    private Message getLastMessage(int UIDCONVERSATION) {
        MessagesDAO messagesDAO = new MessagesDAO(mContext);
        return messagesDAO.getLastConversationMessage(UIDCONVERSATION);
    }

    private User getUser(int UIDUSER) {
        UserDAO userDAO = new UserDAO(mContext);
        return userDAO.getUserDetails(UIDUSER);
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

    public ArrayList<Conversation> getmSelectedItems() {
        ArrayList<Conversation> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mConversationsDataset.get(i));
        }
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView mImg;
        private TextView mContactName;
        private TextView mLastMessageContent;
        private TextView mLastMessageDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.messages_contact_profile_picture);
            mContactName = itemView.findViewById(R.id.messages_cardview_item_contactname);
            mLastMessageContent = itemView.findViewById(R.id.messages_cardview_item_last_message_content);
            mLastMessageDate = itemView.findViewById(R.id.messages_cardview_item_conversation_last_message_date);

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