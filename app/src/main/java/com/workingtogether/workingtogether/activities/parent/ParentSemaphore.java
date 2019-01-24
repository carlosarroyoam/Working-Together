package com.workingtogether.workingtogether.activities.parent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.workingtogether.workingtogether.R;

public class ParentSemaphore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_semaphore);
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