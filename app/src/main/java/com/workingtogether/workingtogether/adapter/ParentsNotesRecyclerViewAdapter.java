package com.workingtogether.workingtogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;

import java.text.DecimalFormat;

public class ParentsNotesRecyclerViewAdapter extends RecyclerView.Adapter<ParentsNotesRecyclerViewAdapter.ViewHolder> {
    private String[] mNotesDataset;

    public ParentsNotesRecyclerViewAdapter() {
        String[] fromDBDataset = {"Espanol", "Matematicas", "Geografia"};//Cargar desde db
        mNotesDataset = fromDBDataset;
    }

    @Override
    public ParentsNotesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_notes_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecimalFormat gradeDecimalFormat = new DecimalFormat("##.#");
        Double grade = Math.random() * 10;

        holder.mSubjectName.setText(mNotesDataset[position]);
        holder.mNote.setText(gradeDecimalFormat.format(grade) + "");
    }

    @Override
    public int getItemCount() {
        return mNotesDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSubjectName;
        private TextView mNote;

        public ViewHolder(View itemView) {
            super(itemView);
            mSubjectName = itemView.findViewById(R.id.notes_cardview_item_activity_subject);
            mNote = itemView.findViewById(R.id.notes_cardview_item_activity_note);
        }
    }
}