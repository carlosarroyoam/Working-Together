package com.workingtogether.workingtogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.workingtogether.workingtogether.db.HomeworksDB;
import com.workingtogether.workingtogether.obj.Homework;
import com.workingtogether.workingtogether.util.LocalParams;

public class HomeworkDetails extends AppCompatActivity {
    private Homework homework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        homework = getHomework();
        Toast.makeText(this, homework.getTITLE(), Toast.LENGTH_SHORT).show();
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

    private Homework getHomework() {
        HomeworksDB homeworksDB = new HomeworksDB(this);
        Homework homework = homeworksDB.getHomeworkById(getIntent().getIntExtra(LocalParams.UIDHOMEWORK, 0));
        return homework;
    }
}
