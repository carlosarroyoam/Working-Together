package com.workingtogether.workingtogether.parent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.workingtogether.workingtogether.HomeworkDetails;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.ParentsHomeworksRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.HomeworksDB;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.obj.Homework;
import com.workingtogether.workingtogether.obj.SessionApp;
import com.workingtogether.workingtogether.util.LocalParams;

import java.util.ArrayList;
import java.util.List;

public class ParentHomeworks extends AppCompatActivity implements ParentsHomeworksRecyclerViewAdapter.RecyclerViewOnItemClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Homework> mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_homeworks);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLayout();
    }


    @Override
    public void onClick(View v, int position) {
        /*Homework homework = mDataset.get(position);
        Intent intent = new Intent(this, HomeworkDetails.class);
        intent.putExtra(LocalParams.UIDHOMEWORK, homework.getUIDHOMEWORK());
        this.startActivity(intent);*/
    }

    @Override
    public void onLongClick(View v, int position) {

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
        mDataset = loadHomeworkList();

        ViewStub stub = findViewById(R.id.homeworks_layout_loader);
        if (mDataset.size() > 0) {
            stub.setLayoutResource(R.layout.activity_parent_homeworks_recycler_view);
            stub.inflate();

            mRecyclerView = findViewById(R.id.recycler_view_homeworks);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ParentsHomeworksRecyclerViewAdapter(this, this, mDataset);
            mRecyclerView.setAdapter(mAdapter);

        } else {
            stub.setLayoutResource(R.layout.activity_parent_homeworks_empty_tray);
            stub.inflate();

        }

    }

    private ArrayList<Homework> loadHomeworkList() {
        HomeworksDB homeworksDB = new HomeworksDB(this);
        ArrayList<Homework> homeworkList = homeworksDB.getAllHomeworks();
        return homeworkList;
    }

}