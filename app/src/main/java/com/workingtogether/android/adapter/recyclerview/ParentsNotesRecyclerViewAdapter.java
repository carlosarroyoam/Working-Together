package com.workingtogether.android.adapter.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workingtogether.android.R;
import com.workingtogether.android.entity.Note;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class ParentsNotesRecyclerViewAdapter extends RecyclerView.Adapter<ParentsNotesRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Note> mNotesDataset;

    public ParentsNotesRecyclerViewAdapter(ArrayList<Note> notesArrayList) {
        mNotesDataset = notesArrayList;
    }

    @NonNull
    @Override
    public ParentsNotesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parent_notes_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mSubjectName.setText(mNotesDataset.get(position).getSUBJECT());
        holder.mNote.setText(String.valueOf((mNotesDataset.get(position).getNOTE())));
    }

    @Override
    public int getItemCount() {
        return mNotesDataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSubjectName;
        private TextView mNote;

        ViewHolder(View itemView) {
            super(itemView);
            mSubjectName = itemView.findViewById(R.id.notes_cardview_item_activity_subject);
            mNote = itemView.findViewById(R.id.notes_cardview_item_activity_note);
        }
    }
}