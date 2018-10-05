package com.workingtogether.workingtogether.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.workingtogether.workingtogether.R;

public class TeacherNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_notes);
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
