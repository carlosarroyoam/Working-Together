package com.workingtogether.workingtogether.teacher;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.NotificationsDrawerAdapter;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.obj.Notification;

import java.util.ArrayList;

public class TeacherNotes extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_notes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
