package com.workingtogether.workingtogether.activities.parent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.recyclerview.ParentsNotesRecyclerViewAdapter;
import com.workingtogether.workingtogether.entity.Note;
import com.workingtogether.workingtogether.entity.dao.NotesDAO;

import java.util.ArrayList;

public class ParentNotes extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RelativeLayout emptyTrayLayout;
    ArrayList<Note> mDataset;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getPackageName() + ".newNote");
        registerReceiver(myReceiver, filter);

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
        myReceiver = new MyReceiver();
        emptyTrayLayout = findViewById(R.id.activity_parent_notes_empty_tray);

        mDataset = new ArrayList<>();
        mDataset.addAll(loadNotesList());

        mRecyclerView = findViewById(R.id.recycler_view_notes);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ParentsNotesRecyclerViewAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        toogleEmptyLayout();
    }

    private void toogleEmptyLayout() {
        if (mDataset.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyTrayLayout.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            emptyTrayLayout.setVisibility(View.VISIBLE);
        }

    }

    private void updateConversationsList() {
        mDataset.clear();
        mDataset.addAll(loadNotesList());
        mAdapter.notifyDataSetChanged();
        toogleEmptyLayout();
    }

    private ArrayList<Note> loadNotesList() {
        NotesDAO notesDAO = new NotesDAO(this);
        ArrayList<Note> notesArrayList = notesDAO.getAllNotes();
        return notesArrayList;
    }

    private class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            updateConversationsList();
        }
    }

}