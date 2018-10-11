package com.workingtogether.workingtogether;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.workingtogether.workingtogether.adapter.HomeworksRecyclerViewAdapter;
import com.workingtogether.workingtogether.db.HomeworksDB;
import com.workingtogether.workingtogether.obj.Homework;
import com.workingtogether.workingtogether.util.LocalParams;
import java.util.ArrayList;

public class Homeworks extends AppCompatActivity implements HomeworksRecyclerViewAdapter.RecyclerViewOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private HomeworksRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private ArrayList<Homework> mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeworks);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLayout();
    }

    @Override
    public void onRefresh() {
        updateHomeworksList();
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
    public void onClick(View v, int position) {
        Homework homework = mDataset.get(position);
        Intent intent = new Intent(this, HomeworkDetails.class);
        intent.putExtra(LocalParams.UIDHOMEWORK, homework.getUIDHOMEWORK());
        this.startActivity(intent);
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
        mDataset.addAll(loadHomeworksList());
        ViewStub stub = findViewById(R.id.homeworks_layout_loader);

        if (mDataset.size() > 0) {
            stub.setLayoutResource(R.layout.activity_homeworks_recycler_view);
            stub.inflate();

            mRecyclerView = findViewById(R.id.recycler_view_homeworks);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new HomeworksRecyclerViewAdapter(this, mDataset);
            mRecyclerView.setAdapter(mAdapter);

            actionModeCallback = new ActionModeCallback();
            swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            updateHomeworksList();
                        }
                    });

        } else {
            stub.setLayoutResource(R.layout.activity_homeworks_empty_tray);
            stub.inflate();

        }
    }

    private ArrayList<Homework> loadHomeworksList() {
        HomeworksDB homeworksDB = new HomeworksDB(this);
        ArrayList<Homework> homeworkList = homeworksDB.getAllHomeworks();
        return homeworkList;
    }

    private void updateHomeworksList() {
        swipeRefreshLayout.setRefreshing(true);
        mDataset.clear();
        mDataset.addAll(loadHomeworksList());
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

    private void deleteHomeworks(ArrayList<Homework> selectedItems) {
        HomeworksDB homeworksDB = new HomeworksDB(getApplicationContext());
        for (int i = 0; i < selectedItems.size(); i++) {
            homeworksDB.deleteHomework(selectedItems.get(i).getUIDHOMEWORK());
            updateHomeworksList();
        }
    }

    private void showDeleteDialog(final ArrayList<Homework> selectedItems) {
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