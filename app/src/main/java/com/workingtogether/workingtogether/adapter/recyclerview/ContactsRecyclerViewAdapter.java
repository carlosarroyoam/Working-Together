package com.workingtogether.workingtogether.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.models.User;

import java.util.ArrayList;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<User> mContactsDataset;
    private RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;
    private SparseBooleanArray mSelectedItems;

    public ContactsRecyclerViewAdapter(RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener, ArrayList<User> conversationArrayList) {
        this.mContactsDataset = conversationArrayList;
        this.mRecyclerViewOnItemClickListener = mRecyclerViewOnItemClickListener;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public ContactsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contacts_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mContactName.setText(mContactsDataset.get(position).getNAME() + " " + mContactsDataset.get(position).getLASTNAME());
        holder.mContactMail.setText(mContactsDataset.get(position).getEMAIL());
        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados

        if (mContactsDataset.get(position).getUSERTYPE().equals(User.UserTypes.TEACHERUSER))
            holder.mImg.setImageResource(R.drawable.ic_teacher);
        if (mContactsDataset.get(position).getUSERTYPE().equals(User.UserTypes.PARENTUSER))
            holder.mImg.setImageResource(R.drawable.ic_student);
    }

    @Override
    public int getItemCount() {
        return mContactsDataset.size();
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

    public ArrayList<User> getmSelectedItems() {
        ArrayList<User> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mContactsDataset.get(i));
        }
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImg;
        private TextView mContactName;
        private TextView mContactMail;

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.contacts_cardview_item_contact_profile_picture);
            mContactName = itemView.findViewById(R.id.contacts_cardview_item_contact_name);
            mContactMail = itemView.findViewById(R.id.contacts_cardview_item_contact_email);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }

    }

}