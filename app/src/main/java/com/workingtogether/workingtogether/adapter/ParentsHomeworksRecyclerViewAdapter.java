package com.workingtogether.workingtogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.workingtogether.workingtogether.HomeworkDetails;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.db.HomeworksDB;
import com.workingtogether.workingtogether.obj.Homework;

import java.util.ArrayList;

public class ParentsHomeworksRecyclerViewAdapter extends RecyclerView.Adapter<ParentsHomeworksRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Homework> mHomeworksDataset;
    private Context mContext;
    private RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener;

    public ParentsHomeworksRecyclerViewAdapter(Context context, RecyclerViewOnItemClickListener mRecyclerViewOnItemClickListener, ArrayList<Homework> homeworkList) {
        mHomeworksDataset = homeworkList;
        this.mContext = context;
        this.mRecyclerViewOnItemClickListener = mRecyclerViewOnItemClickListener;
    }

    @Override
    public ParentsHomeworksRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_homeworks_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mId.setText(Integer.toString(mHomeworksDataset.get(position).getUIDHOMEWORK()));
        holder.mTitle.setText(mHomeworksDataset.get(position).getTITLE());
        holder.mDescription.setText(mHomeworksDataset.get(position).getDESCRIPTION());
        holder.mDeliverDate.setText("Fecha de entrega: " + mHomeworksDataset.get(position).getDELIVERDATE());
    }

    @Override
    public int getItemCount() {
        return mHomeworksDataset.size();
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