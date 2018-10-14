package com.workingtogether.workingtogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
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
        HomeworksDB homeworksDB = new HomeworksDB(this);
        Homework homework = homeworksDB.getHomeworkById(getIntent().getIntExtra(LocalParams.UIDHOMEWORK, 0));
        return homework;
    }
}
