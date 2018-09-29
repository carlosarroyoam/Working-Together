package com.workingtogether.workingtogether.parent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.workingtogether.workingtogether.R;

public class ParentDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
    }

    public void homeworksActivity(View view){
        startActivity(new Intent(this, ParentHomeworks.class));
    }

    public void semaphoreActivity(View view){
        startActivity(new Intent(this, ParentSemaphore.class));
    }

    public void activitiesActivity(View view){
        startActivity(new Intent(this, ParentActivities.class));
    }

    public void gradesActivity(View view){
        startActivity(new Intent(this, ParentGrades.class));
    }
}
