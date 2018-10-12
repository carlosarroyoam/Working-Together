package com.workingtogether.workingtogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.obj.Conversation;
import com.workingtogether.workingtogether.util.DateUtils;

import java.util.ArrayList;

public class ConversationsRecyclerViewAdapter extends RecyclerView.Adapter<ConversationsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Conversation> mConversationsDataset;
    private ConversationsRecyclerViewAdapter.RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;
    private SparseBooleanArray mSelectedItems;

    public ConversationsRecyclerViewAdapter(ConversationsRecyclerViewAdapter.RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener, ArrayList<Conversation> conversationArrayList) {
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
        holder.mId.setText(Integer.toString(mConversationsDataset.get(position).getUIDCONVERSATION()));

        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados

        if (mConversationsDataset.get(position).getLASTMESSAGEDATE().substring(0, 9).equals(DateUtils.getDateTime().substring(0, 9)))
            holder.mLastMessageDate.setText(mConversationsDataset.get(position).getLASTMESSAGEDATE().substring(11, 16));
        else
            holder.mLastMessageDate.setText(mConversationsDataset.get(position).getLASTMESSAGEDATE().substring(0, 10));

    }

    @Override
    public int getItemCount() {
        return mConversationsDataset.size();
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
        private TextView mId;
        private ImageView mImg;
        private TextView mContactName;
        private TextView mLastMessageContent;
        private TextView mLastMessageDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mId = itemView.findViewById(R.id.messages_cardview_item_conversation_id);
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

    public interface RecyclerViewOnItemClickListener {
        void onClick(View v, int position);
        void onLongClick(View v, int position);
    }

}