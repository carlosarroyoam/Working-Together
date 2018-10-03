package com.workingtogether.workingtogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.workingtogether.workingtogether.db.SQLiteOpenHelper;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.obj.SessionApp;
import com.workingtogether.workingtogether.parent.ParentDashboard;
import com.workingtogether.workingtogether.teacher.TeacherDashboard;
import com.workingtogether.workingtogether.util.LocalParams;

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

    private void isUserlogged(){
        SessionDB sessionDB = new SessionDB(this);
        if(sessionDB.isUserlogged()){
            SessionApp sessionApp = sessionDB.getUserlogged();

            if(sessionApp.getTYPEUSER().equals(LocalParams.TEACHERUSER)){
                startActivity(new Intent(this, TeacherDashboard.class));
                finish();
            }else if(sessionApp.getTYPEUSER().equals(LocalParams.PARENTUSER)){
                startActivity(new Intent(this, ParentDashboard.class));
                finish();
            } else {
                //TODO handle USERTYPE error
            }
        }else{
            startActivity(new Intent(this, Signin.class));
            finish();
        }
    }

    private void createDataBase() {
        String DB_PATH = this.getApplicationInfo().dataDir + "/databases";
        File dbFolder = new File(DB_PATH);
        File db = new File(DB_PATH + "/" + LocalParams.DB_NAME);

        InputStream myInput = null;
        OutputStream myOutput = null;

        byte[] buffer = new byte[1024];
        int length;

        try {
            if (!dbFolder.exists()) {
                dbFolder.mkdir();
            }
            if (!db.exists()) {
                myInput = this.getAssets().open(LocalParams.DB_NAME);
                myOutput = new FileOutputStream(DB_PATH + "/" + LocalParams.DB_NAME);

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
