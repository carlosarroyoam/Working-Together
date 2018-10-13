package com.workingtogether.workingtogether;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.workingtogether.workingtogether.adapter.ContactsRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.db.ParentDB;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.db.TeacherDB;
import com.workingtogether.workingtogether.obj.Notification;
import com.workingtogether.workingtogether.obj.SessionApp;
import com.workingtogether.workingtogether.obj.User;
import com.workingtogether.workingtogether.util.LocalParams;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity implements ContactsRecyclerViewAdapter.RecyclerViewOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ContactsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<User> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        mDataset = new ArrayList<>();
        mDataset.addAll(loadContactsList());
        ViewStub stub = findViewById(R.id.contacts_layout_loader);

        if (mDataset.size() > 0) {
            stub.setLayoutResource(R.layout.activity_contacts_recycler_view);
            stub.inflate();

            mRecyclerView = findViewById(R.id.recycler_view_contacts);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mAdapter = new ContactsRecyclerViewAdapter(this, mDataset);
            mRecyclerView.setAdapter(mAdapter);

            swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            updateContactsList();
                        }
                    });
        } else {
            stub.setLayoutResource(R.layout.activity_contacts_empty_tray);
            stub.inflate();

        }

    }

    private ArrayList<User> loadContactsList() {
        ArrayList<User> contactsArrayList = new ArrayList<>();

        SessionDB sessionDB = new SessionDB(this);
        SessionApp sessionApp = sessionDB.getUserlogged();

        if (sessionApp.getTYPEUSER().equals(LocalParams.PARENTUSER)) {
            TeacherDB teacherDB = new TeacherDB(this);
            contactsArrayList.addAll(teacherDB.getTeachers());
        } else if (sessionApp.getTYPEUSER().equals(LocalParams.TEACHERUSER)) {
            ParentDB parentDB = new ParentDB(this);
            contactsArrayList.addAll(parentDB.getParents());
        }

        return contactsArrayList;
    }

    private void updateContactsList() {
        swipeRefreshLayout.setRefreshing(true);
        mDataset.clear();
        mDataset.addAll(loadContactsList());
        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v, int position) {

    }

    @Override
    public void onRefresh() {
        updateContactsList();
    }

}
