package com.workingtogether.workingtogether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.recyclerview.ContactsRecyclerViewAdapter;
import com.workingtogether.workingtogether.adapter.recyclerview.OnItemClickListener;
import com.workingtogether.workingtogether.entity.Conversation;
import com.workingtogether.workingtogether.entity.SessionApp;
import com.workingtogether.workingtogether.entity.User;
import com.workingtogether.workingtogether.entity.dao.ConversationsDAO;
import com.workingtogether.workingtogether.entity.dao.ParentDAO;
import com.workingtogether.workingtogether.entity.dao.SessionDAO;
import com.workingtogether.workingtogether.entity.dao.TeacherDAO;
import com.workingtogether.workingtogether.util.GlobalParams;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class ContactsActivity extends AppCompatActivity implements OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
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

        if (sessionApp.getTYPEUSER().equals(User.UserTypes.PARENT_USER)) {
            TeacherDAO teacherDAO = new TeacherDAO(this);
            contactsArrayList.addAll(teacherDAO.getTeachers());
        } else if (sessionApp.getTYPEUSER().equals(User.UserTypes.TEACHER_USER)) {
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
        Conversation conversation = getConversation(mDataset.get(position).getId());
        Intent intent = new Intent(this, ConversationDetailsActivity.class);

        if (conversation.getId() > 0) {
            intent.putExtra(GlobalParams.UIDCONVERSATION, conversation.getId());
            intent.putExtra(GlobalParams.UIDUSER, conversation.getIdUser());
        } else {
            intent.putExtra(GlobalParams.UIDUSER, mDataset.get(position).getId());
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
