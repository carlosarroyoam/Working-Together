package com.workingtogether.workingtogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.obj.Homework;
import java.util.ArrayList;

public class HomeworksRecyclerViewAdapter extends RecyclerView.Adapter<HomeworksRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Homework> mHomeworksDataset;
    private RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;
    private SparseBooleanArray mSelectedItems;

    public HomeworksRecyclerViewAdapter(RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener, ArrayList<Homework> homeworkList) {
        this.mHomeworksDataset = homeworkList;
        this.mRecyclerViewOnItemClickListener = mRecyclerViewOnItemClickListener;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public HomeworksRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_homeworks_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mId.setText(Integer.toString(mHomeworksDataset.get(position).getUIDHOMEWORK()));
        holder.mTitle.setText(mHomeworksDataset.get(position).getTITLE());
        holder.mDescription.setText(mHomeworksDataset.get(position).getDESCRIPTION());
        holder.mDeliverDate.setText("Fecha de entrega: " + mHomeworksDataset.get(position).getDELIVERDATE());
        holder.itemView.setActivated(mSelectedItems.get(position, false)); //Cambiar estado a activado en items seleccionados
    }

    @Override
    public int getItemCount() {
        return mHomeworksDataset.size();
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

    public ArrayList<Homework> getmSelectedItems() {
        ArrayList<Homework> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mHomeworksDataset.get(i));
        }
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mId;
        private TextView mTitle;
        private TextView mDescription;
        private TextView mDeliverDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mId = itemView.findViewById(R.id.homeworks_cardview_item_homework_id);
            mTitle = itemView.findViewById(R.id.homeworks_cardview_item_homework_title);
            mDescription = itemView.findViewById(R.id.homeworks_cardview_item_homework_description);
            mDeliverDate = itemView.findViewById(R.id.homeworks_cardview_item_homework_deliverdate);

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