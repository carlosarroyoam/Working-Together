package com.workingtogether.workingtogether.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.recyclerview.HomeworksRecyclerViewAdapter;
import com.workingtogether.workingtogether.adapter.recyclerview.OnItemClickListenerInterface;
import com.workingtogether.workingtogether.entity.Homework;
import com.workingtogether.workingtogether.entity.dao.HomeworksDAO;
import com.workingtogether.workingtogether.util.GlobalParams;

import java.util.ArrayList;

public class HomeworksActivity extends AppCompatActivity implements OnItemClickListenerInterface, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private HomeworksRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private ArrayList<Homework> mDataset;
    private RelativeLayout emptyTrayLayout;
    private MyReceiver myReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeworks);

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
        filter.addAction(getPackageName() + ".newHomework");
        registerReceiver(myReceiver, filter);

        updateHomeworksList();
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
        Intent intent = new Intent(this, HomeworkDetailsActivity.class);
        intent.putExtra(GlobalParams.UIDHOMEWORK, homework.getUIDHOMEWORK());
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
        myReceiver = new MyReceiver();
        emptyTrayLayout = findViewById(R.id.activity_homeworks_empty_tray);

        mDataset = new ArrayList<>();
        mDataset.addAll(loadHomeworksList());

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

    private ArrayList<Homework> loadHomeworksList() {
        HomeworksDAO homeworksDB = new HomeworksDAO(this);
        ArrayList<Homework> homeworkList = homeworksDB.getAllHomeworks();
        return homeworkList;
    }

    private void updateHomeworksList() {
        swipeRefreshLayout.setRefreshing(true);
        mDataset.clear();
        mDataset.addAll(loadHomeworksList());
        mAdapter.notifyDataSetChanged();
        toogleEmptyLayout();
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
        HomeworksDAO homeworksDB = new HomeworksDAO(getApplicationContext());
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

    private class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            updateHomeworksList();
        }
    }

}