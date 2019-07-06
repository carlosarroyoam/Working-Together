package com.workingtogether.android.activities.teacher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.workingtogether.android.R;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
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
