package com.workingtogether.workingtogether;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.workingtogether.workingtogether.adapter.ContactsRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.ConversationsDB;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.db.ParentDB;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.db.TeacherDB;
import com.workingtogether.workingtogether.obj.Conversation;
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
    private RelativeLayout emptyTrayLayout;

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
        emptyTrayLayout = findViewById(R.id.activity_contacts_empty_tray);

        mDataset = new ArrayList<>();
        mDataset.addAll(loadContactsList());

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
        toogleEmptyLayout();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v, int position) {
        Conversation conversation = getConversation(mDataset.get(position).getUIDUSER());
        Intent intent = new Intent(this, ConversationDetails.class);

        if (conversation.getUIDCONVERSATION() > 0) {
            intent.putExtra(LocalParams.UIDCONVERSATION, conversation.getUIDCONVERSATION());
            intent.putExtra(LocalParams.UIDUSER, conversation.getUIDUSER());
        } else {
            intent.putExtra(LocalParams.UIDUSER, mDataset.get(position).getUIDUSER());
        }

        startActivity(intent);
        finish();
    }

    private Conversation getConversation(int USERID) {
        ConversationsDB conversationsDB = new ConversationsDB(this);
        return conversationsDB.getConversationByContactId(USERID);
    }

    @Override
    public void onRefresh() {
        updateContactsList();
    }

}
