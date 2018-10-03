package com.workingtogether.workingtogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.workingtogether.workingtogether.R;

public class ParentsHomeworksRecyclerViewAdapter extends RecyclerView.Adapter<ParentsHomeworksRecyclerViewAdapter.ViewHolder> {
    private String[] mHomeworksDataset;

    public ParentsHomeworksRecyclerViewAdapter() {
        String[] fromDBDataset = { "Tarea 1", "Tarea 2", "Tarea 3"};//Cargar desde db
        mHomeworksDataset = fromDBDataset;
    }

    @Override
    public ParentsHomeworksRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_homeworks_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mHomeworksDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mHomeworksDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.homeworks_cardview_item_homework_title);
        }
    }
}