package com.workingtogether.workingtogether.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.models.Note;

import java.util.ArrayList;

public class ParentsNotesRecyclerViewAdapter extends RecyclerView.Adapter<ParentsNotesRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Note> mNotesDataset;

    public ParentsNotesRecyclerViewAdapter(ArrayList<Note> notesArrayList) {
        mNotesDataset = notesArrayList;
    }

    @Override
    public ParentsNotesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_notes_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSubjectName.setText(mNotesDataset.get(position).getSUBJECT());
        holder.mNote.setText(Double.toString(mNotesDataset.get(position).getNOTE()));
    }

    @Override
    public int getItemCount() {
        return mNotesDataset.size();
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