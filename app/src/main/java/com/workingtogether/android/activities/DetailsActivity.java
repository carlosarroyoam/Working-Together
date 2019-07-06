package com.workingtogether.android.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.workingtogether.android.R;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_details);
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
