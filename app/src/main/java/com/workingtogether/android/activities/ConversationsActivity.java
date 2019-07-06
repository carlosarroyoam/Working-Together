package com.workingtogether.android.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.workingtogether.android.R;
import com.workingtogether.android.adapter.recyclerview.ConversationsRecyclerViewAdapter;
import com.workingtogether.android.adapter.recyclerview.OnItemClickListener;
import com.workingtogether.android.entity.Conversation;
import com.workingtogether.android.entity.dao.ConversationsDAO;
import com.workingtogether.android.entity.dao.MessagesDAO;
import com.workingtogether.android.util.GlobalParams;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class ConversationsActivity extends AppCompatActivity implements OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ConversationsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConversationsActivity.ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private ArrayList<Conversation> mDataset;
    private RelativeLayout emptyTrayLayout;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLayout();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
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
        filter.addAction(getPackageName() + ".newMessage");
        registerReceiver(myReceiver, filter);

        updateConversationsList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setLayout() {
        myReceiver = new ConversationsActivity.MyReceiver();
        emptyTrayLayout = findViewById(R.id.activity_conversations_empty_tray);

        mDataset = new ArrayList<>();
        mDataset.addAll(loadConversationsList());

        mRecyclerView = findViewById(R.id.recycler_view_conversations);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new ConversationsRecyclerViewAdapter(this, this, mDataset);
        mRecyclerView.setAdapter(mAdapter);

        actionModeCallback = new ConversationsActivity.ActionModeCallback();
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        updateConversationsList();
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

    private ArrayList<Conversation> loadConversationsList() {
        ConversationsDAO conversationsDAO = new ConversationsDAO(this);
        return conversationsDAO.getAllConversations();
    }

    private void updateConversationsList() {
        swipeRefreshLayout.setRefreshing(true);
        mDataset.clear();
        mDataset.addAll(loadConversationsList());
        mAdapter.notifyDataSetChanged();
        toogleEmptyLayout();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(this, ConversationDetailsActivity.class);
        intent.putExtra(GlobalParams.UIDCONVERSATION, mDataset.get(position).getId());
        intent.putExtra(GlobalParams.UIDUSER, mDataset.get(position).getIdUser());
        startActivity(intent);
    }

    @Override
    public void onLongClick(View v, int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Override
    public void onRefresh() {
        updateConversationsList();
    }

    public void newMessage(View view) {
        startActivity(new Intent(this, ContactsActivity.class));
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delete) {
                showDeleteDialog(mAdapter.getmSelectedItems());
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }


    }

    private void deleteHomeworks(ArrayList<Conversation> selectedItems) {
        ConversationsDAO conversationsDAO = new ConversationsDAO(getApplicationContext());
        for (int i = 0; i < selectedItems.size(); i++) {
            MessagesDAO messagesDAO = new MessagesDAO(this);
            messagesDAO.deleteMessages(selectedItems.get(i).getId());
            conversationsDAO.deleteConversation(selectedItems.get(i).getId());
            updateConversationsList();
        }
    }

    private void showDeleteDialog(final ArrayList<Conversation> selectedItems) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Eliminar");
        alertDialogBuilder
                .setMessage("¿Estas seguro que quieres eliminar los elementos seleccionados? Esta accion no se puede deshacer")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteHomeworks(selectedItems);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
