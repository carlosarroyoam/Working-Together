package com.workingtogether.workingtogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;

import com.workingtogether.workingtogether.adapter.NotificationsRecyclerViewAdapter;
import com.workingtogether.workingtogether.adapter.ParentsHomeworksRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.obj.Notification;

import java.util.ArrayList;

public class NotificationsTray extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_tray);

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
        ArrayList<Notification> notificationArrayList = loadNotificationsList();

        ViewStub stub = findViewById(R.id.notificatios_layout_loader);
        if (notificationArrayList.size() > 0) {
            stub.setLayoutResource(R.layout.activity_notifications_recycler_view);
            stub.inflate();

            mRecyclerView = findViewById(R.id.recycler_view_notifications);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new NotificationsRecyclerViewAdapter(notificationArrayList);
            mRecyclerView.setAdapter(mAdapter);

        } else {
            stub.setLayoutResource(R.layout.activity_notifications_empty_tray);
            stub.inflate();

        }

    }

    private ArrayList<Notification> loadNotificationsList() {
        NotificationsDB notificationsDB = new NotificationsDB(this);
        ArrayList<Notification> notificationArrayList = notificationsDB.getAllNotifications();
        return notificationArrayList;
    }
}
