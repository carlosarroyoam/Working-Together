package com.workingtogether.workingtogether.parent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.ParentsActivitiesRecyclerViewAdapter;
import com.workingtogether.workingtogether.adapter.ParentsHomeworksRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.ActivityDB;
import com.workingtogether.workingtogether.db.HomeworksDB;
import com.workingtogether.workingtogether.obj.Activity;
import com.workingtogether.workingtogether.obj.Homework;

import java.util.ArrayList;

public class ParentActivities extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_activities);

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
        ArrayList<Activity> activityArrayList = loadHomeworkList();

        ViewStub stub = findViewById(R.id.activities_layout_loader);
        if (activityArrayList.size() > 0) {
            stub.setLayoutResource(R.layout.activity_parent_activities_recycler_view);
            stub.inflate();

            mRecyclerView = findViewById(R.id.recycler_view_activities);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ParentsActivitiesRecyclerViewAdapter(activityArrayList);
            mRecyclerView.setAdapter(mAdapter);

        } else {
            stub.setLayoutResource(R.layout.activity_parent_activities_empty_tray);
            stub.inflate();

        }

    }

    private ArrayList<Activity> loadHomeworkList() {
        ActivityDB activityDB = new ActivityDB(this);
        ArrayList<Activity> activityArrayList = activityDB.getAllActivities();
        return activityArrayList;
    }

}