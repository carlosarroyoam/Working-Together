package com.workingtogether.workingtogether;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.workingtogether.workingtogether.adapter.ActivitiesRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.ActivityDB;
import com.workingtogether.workingtogether.db.HomeworksDB;
import com.workingtogether.workingtogether.obj.Activity;
import com.workingtogether.workingtogether.obj.Homework;

import java.util.ArrayList;

public class Activities extends AppCompatActivity implements ActivitiesRecyclerViewAdapter.RecyclerViewOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ActivitiesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Activities.ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private ArrayList<Activity> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

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

    @Override
    public void onRefresh() {
        updateActivitiesList();
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


    private void setLayout() {
        mDataset = new ArrayList<>();
        mDataset.addAll(loadActivitiesList());

        ViewStub stub = findViewById(R.id.activities_layout_loader);

        if (mDataset.size() > 0) {
            stub.setLayoutResource(R.layout.activity_activities_recycler_view);
            stub.inflate();

            mRecyclerView = findViewById(R.id.recycler_view_activities);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ActivitiesRecyclerViewAdapter(this, mDataset);
            mRecyclerView.setAdapter(mAdapter);

            actionModeCallback = new Activities.ActionModeCallback();
            swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            updateActivitiesList();
                        }
                    });
        } else {
            stub.setLayoutResource(R.layout.activity_activities_empty_tray);
            stub.inflate();

        }

    }

    private ArrayList<Activity> loadActivitiesList() {
        ActivityDB activityDB = new ActivityDB(this);
        ArrayList<Activity> activityArrayList = activityDB.getAllActivities();
        return activityArrayList;
    }

    private void updateActivitiesList() {
        swipeRefreshLayout.setRefreshing(true);
        mDataset.clear();
        mDataset.addAll(loadActivitiesList());
        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
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

    private void deleteHomeworks(ArrayList<Activity> selectedItems) {
        ActivityDB activityDB = new ActivityDB(getApplicationContext());
        for (int i = 0; i < selectedItems.size(); i++) {
            activityDB.deleteActivity(selectedItems.get(i).getUIDACTIVITY());
            updateActivitiesList();
        }
    }

    private void showDeleteDialog(final ArrayList<Activity> selectedItems) {
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

}