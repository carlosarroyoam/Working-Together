package com.workingtogether.workingtogether.parent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.ParentsNotesRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.NotesDB;
import com.workingtogether.workingtogether.obj.Note;

import java.util.ArrayList;

public class ParentNotes extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLayout();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setLayout() {
        ArrayList<Note> noteArrayList = loadNotesList();

        ViewStub stub = findViewById(R.id.notes_layout_loader);
        if (noteArrayList.size() > 0) {
            stub.setLayoutResource(R.layout.activity_parent_notes_recycler_view);
            stub.inflate();

            mRecyclerView = findViewById(R.id.recycler_view_notes);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(this, 3);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ParentsNotesRecyclerViewAdapter(noteArrayList);
            mRecyclerView.setAdapter(mAdapter);

        } else {
            stub.setLayoutResource(R.layout.activity_parent_homeworks_empty_tray);
            stub.inflate();

        }

    }

    private ArrayList<Note> loadNotesList() {
        NotesDB notesDB = new NotesDB(this);
        ArrayList<Note> notesArrayList = notesDB.getAllNotes();
        return notesArrayList;
    }

}