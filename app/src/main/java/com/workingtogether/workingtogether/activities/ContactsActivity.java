package com.workingtogether.workingtogether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.recyclerview.ContactsRecyclerViewAdapter;
import com.workingtogether.workingtogether.adapter.recyclerview.RecyclerViewOnItemClickListener;
import com.workingtogether.workingtogether.models.Conversation;
import com.workingtogether.workingtogether.models.SessionApp;
import com.workingtogether.workingtogether.models.User;
import com.workingtogether.workingtogether.models.dao.ConversationsDAO;
import com.workingtogether.workingtogether.models.dao.ParentDAO;
import com.workingtogether.workingtogether.models.dao.SessionDAO;
import com.workingtogether.workingtogether.models.dao.TeacherDAO;
import com.workingtogether.workingtogether.util.GlobalParams;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity implements RecyclerViewOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
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

        SessionDAO sessionDAO = new SessionDAO(this);
        SessionApp sessionApp = sessionDAO.getUserlogged();

        if (sessionApp.getTYPEUSER().equals(User.UserTypes.PARENTUSER)) {
            TeacherDAO teacherDAO = new TeacherDAO(this);
            contactsArrayList.addAll(teacherDAO.getTeachers());
        } else if (sessionApp.getTYPEUSER().equals(User.UserTypes.TEACHERUSER)) {
            ParentDAO parentDAO = new ParentDAO(this);
            contactsArrayList.addAll(parentDAO.getParents());
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
        Intent intent = new Intent(this, ConversationDetailsActivity.class);

        if (conversation.getUIDCONVERSATION() > 0) {
            intent.putExtra(GlobalParams.UIDCONVERSATION, conversation.getUIDCONVERSATION());
            intent.putExtra(GlobalParams.UIDUSER, conversation.getUIDUSER());
        } else {
            intent.putExtra(GlobalParams.UIDUSER, mDataset.get(position).getUIDUSER());
        }

        startActivity(intent);
        finish();
    }

    @Override
    public void onLongClick(View v, int position) {

    }

    private Conversation getConversation(int USERID) {
        ConversationsDAO conversationsDAO = new ConversationsDAO(this);
        return conversationsDAO.getConversationByContactId(USERID);
    }

    @Override
    public void onRefresh() {
        updateContactsList();
    }

}
