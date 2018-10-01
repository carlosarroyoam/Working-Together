package com.workingtogether.workingtogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;

import java.text.DecimalFormat;

public class ParentsGradesRecyclerViewAdapter extends RecyclerView.Adapter<ParentsGradesRecyclerViewAdapter.ViewHolder> {
    private String[] mGradesDataset;

    public ParentsGradesRecyclerViewAdapter() {
        String[] fromDBDataset = {"Espanol", "Matematicas", "Geografia"};//Cargar desde db
        mGradesDataset = fromDBDataset;
    }

    @Override
    public ParentsGradesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_grades_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecimalFormat gradeDecimalFormat = new DecimalFormat("##.#");
        Double grade = Math.random() * 10;

        holder.mSubjectName.setText(mGradesDataset[position]);
        holder.mGrade.setText(gradeDecimalFormat.format(grade) + "");
    }

    @Override
    public int getItemCount() {
        return mGradesDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSubjectName;
        private TextView mGrade;

        public ViewHolder(View itemView) {
            super(itemView);
            mSubjectName = itemView.findViewById(R.id.activities_cardview_item_activity_subject);
            mGrade = itemView.findViewById(R.id.activities_cardview_item_activity_grade);
        }
    }
}