package com.workingtogether.workingtogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.obj.Homework;

import java.util.ArrayList;
import java.util.List;

public class ParentsHomeworksRecyclerViewAdapter extends RecyclerView.Adapter<ParentsHomeworksRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Homework> mHomeworksDataset;

    public ParentsHomeworksRecyclerViewAdapter(ArrayList<Homework> homeworkList) {
        mHomeworksDataset = homeworkList;
    }

    @Override
    public ParentsHomeworksRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_homeworks_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mId.setText(Integer.toString(mHomeworksDataset.get(position).getUIDHOMEWORK()));
        holder.mTitle.setText(mHomeworksDataset.get(position).getTITLE());
        holder.mDescription.setText(mHomeworksDataset.get(position).getDESCRIPTION());
        holder.mDeliverDate.setText("Fecha de entrega: " + mHomeworksDataset.get(position).getDELIVERDATE());
    }

    @Override
    public int getItemCount() {
        return mHomeworksDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}