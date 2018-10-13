package com.workingtogether.workingtogether;

import android.content.DialogInterface;
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
import com.workingtogether.workingtogether.adapter.NotificationsRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.obj.Notification;

import java.util.ArrayList;

public class NotificationsTray extends AppCompatActivity implements NotificationsRecyclerViewAdapter.RecyclerViewOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private NotificationsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NotificationsTray.ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private ArrayList<Notification> mDataset;

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
        mDataset = new ArrayList<>();
        mDataset.addAll(loadNotificationsList());
        ViewStub stub = findViewById(R.id.notificatios_layout_loader);

        if (mDataset.size() > 0) {
            stub.setLayoutResource(R.layout.activity_notifications_recycler_view);
            stub.inflate();

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

    private void updateNotificationsList() {
        swipeRefreshLayout.setRefreshing(true);
        mDataset.clear();
        mDataset.addAll(loadNotificationsList());
        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v, int position) {

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
}
