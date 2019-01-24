package com.workingtogether.workingtogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.workingtogether.workingtogether.activities.DashboardActivity;
import com.workingtogether.workingtogether.activities.SigninActivity;
import com.workingtogether.workingtogether.db.DatabaseSchema;
import com.workingtogether.workingtogether.entity.dao.SessionDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createDataBase(); //First app run, database will be created.
        isUserlogged(); //Check if exist an active session.
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

    private void createDataBase() {
        String databasePath = this.getApplicationInfo().dataDir + "/databases";
        File databaseFolder = new File(databasePath);
        File database = new File(databasePath + "/" + DatabaseSchema.DATABASE_NAME);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] buffer = new byte[1024];
        int length;

        try {
            if (!databaseFolder.exists()) {
                databaseFolder.mkdir();
            }
            if (!database.exists()) {
                inputStream = this.getAssets().open(DatabaseSchema.DATABASE_NAME);
                outputStream = new FileOutputStream(databasePath + "/" + DatabaseSchema.DATABASE_NAME);

                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
