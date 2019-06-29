package com.workingtogether.workingtogether.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.entity.Homework;
import com.workingtogether.workingtogether.entity.dao.HomeworksDAO;
import com.workingtogether.workingtogether.util.GlobalParams;

public class HomeworkDetailsActivity extends AppCompatActivity {
    private Homework homework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = findViewById(R.id.homeworks_title);
        TextView desc = findViewById(R.id.homeworks_description);
        TextView deliverdate = findViewById(R.id.homeworks_deliverdate);
        TextView publishdate = findViewById(R.id.homeworks_publishdate);

        homework = getHomework();

        title.setText(homework.getTITLE());
        desc.setText(homework.getDESCRIPTION());
        deliverdate.setText("Fecha de entrega: " + homework.getDELIVERDATE());
        publishdate.setText(homework.getPUBLISHDATE());
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
        HomeworksDAO homeworksDB = new HomeworksDAO(this);
        Homework homework = homeworksDB.getHomeworkById(getIntent().getIntExtra(GlobalParams.UIDHOMEWORK, 0));
        return homework;
    }
}
