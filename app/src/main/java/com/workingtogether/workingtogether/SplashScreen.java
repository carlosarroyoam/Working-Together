package com.workingtogether.workingtogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.workingtogether.workingtogether.activities.DashboardActivity;
import com.workingtogether.workingtogether.activities.SigninActivity;
import com.workingtogether.workingtogether.models.dao.SessionDAO;
import com.workingtogether.workingtogether.util.GlobalParams;

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
        String DB_PATH = this.getApplicationInfo().dataDir + "/databases";
        File dbFolder = new File(DB_PATH);
        File db = new File(DB_PATH + "/" + GlobalParams.DB_NAME);

        InputStream myInput = null;
        OutputStream myOutput = null;

        byte[] buffer = new byte[1024];
        int length;

        try {
            if (!dbFolder.exists()) {
                dbFolder.mkdir();
            }
            if (!db.exists()) {
                myInput = this.getAssets().open(GlobalParams.DB_NAME);
                myOutput = new FileOutputStream(DB_PATH + "/" + GlobalParams.DB_NAME);

                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.close();
                myOutput.flush();
                myInput.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
