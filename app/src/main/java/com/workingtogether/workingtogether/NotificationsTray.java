package com.workingtogether.workingtogether;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.workingtogether.workingtogether.adapter.NotificationsRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.obj.Notification;
import com.workingtogether.workingtogether.parent.ParentNotes;
import com.workingtogether.workingtogether.util.LocalParams;

import java.util.ArrayList;

public class NotificationsTray extends AppCompatActivity implements NotificationsRecyclerViewAdapter.RecyclerViewOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private NotificationsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NotificationsTray.ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private ArrayList<Notification> mDataset;
    private RelativeLayout emptyTrayLayout;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_tray);

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
        filter.addAction(getPackageName() + ".updateNotificationsList");
        registerReceiver(myReceiver, filter);

        updateNotificationsList();
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
        emptyTrayLayout = findViewById(R.id.activity_notifications_empty_tray);

        mDataset = new ArrayList<>();
        mDataset.addAll(loadNotificationsList());

        mRecyclerView = findViewById(R.id.recycler_view_notifications);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new NotificationsRecyclerViewAdapter(this, mDataset);
        mRecyclerView.setAdapter(mAdapter);

        actionModeCallback = new NotificationsTray.ActionModeCallback();
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        updateNotificationsList();
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

    private ArrayList<Notification> loadNotificationsList() {
        NotificationsDB notificationsDB = new NotificationsDB(this);
        ArrayList<Notification> notificationArrayList = notificationsDB.getAllNotifications();
        return notificationArrayList;
    }

    private void updateNotificationsList() {
        swipeRefreshLayout.setRefreshing(true);
        mDataset.clear();
        mDataset.addAll(loadNotificationsList());
        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v, int position) {
        if (mDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.HOMEWORKNOTIFICATION)) {
            startActivity(new Intent(this, Homeworks.class));
        } else if (mDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.ACTIVITYNOTIFICATION)) {
            startActivity(new Intent(this, Activities.class));
        } else if (mDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.NOTESNOTIFICATION)) {
            startActivity(new Intent(this, ParentNotes.class));
        } else if (mDataset.get(position).getNOTIFICATIONTYPE().equals(LocalParams.NOTESNOTIFICATION)) {
            startActivity(new Intent(this, Conversations.class));
        }
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
        updateNotificationsList();
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
            switch (item.getItemId()) {
                case R.id.action_delete:
                    showDeleteDialog(mAdapter.getmSelectedItems());
                    mode.finish();
                    return true;

                default:
                    return false;
            }
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

    private void deleteNotifications(ArrayList<Notification> selectedItems) {
        NotificationsDB homeworksDB = new NotificationsDB(getApplicationContext());
        for (int i = 0; i < selectedItems.size(); i++) {
            homeworksDB.deleteNotification(selectedItems.get(i).getUIDNOTIFICATION());
            updateNotificationsList();
        }
    }

    private void showDeleteDialog(final ArrayList<Notification> selectedItems) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Eliminar");
        alertDialogBuilder
                .setMessage("¿Estas seguro que quieres eliminar los elementos seleccionados? Esta accion no se puede deshacer")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteNotifications(selectedItems);
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
            updateNotificationsList();
        }
    }

}
