package com.workingtogether.workingtogether;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.workingtogether.workingtogether.activities.DashboardActivity;
import com.workingtogether.workingtogether.activities.SigninActivity;
import com.workingtogether.workingtogether.entity.dao.SessionDAO;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isUserlogged();
    }

    private void isUserlogged() {
        SessionDAO sessionDAO = new SessionDAO(this);
        if (sessionDAO.isUserlogged()) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, SigninActivity.class));
            finish();
        }
    }

}
