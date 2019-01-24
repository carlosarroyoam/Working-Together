package com.workingtogether.workingtogether.activities.teacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.workingtogether.workingtogether.R;

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
