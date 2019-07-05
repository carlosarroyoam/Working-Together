package com.workingtogether.workingtogether.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.entity.Homework;
import com.workingtogether.workingtogether.entity.dao.HomeworksDAOImplementation;
import com.workingtogether.workingtogether.util.GlobalParams;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
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

        title.setText(homework.getTitle());
        desc.setText(homework.getDescription());
        deliverdate.setText("Fecha de entrega: " + homework.getDeliveryDate());
        publishdate.setText(homework.getCreatedAt());
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
        Homework homework = HomeworksDAOImplementation.getInstance(this).get(getIntent().getIntExtra(GlobalParams.UIDHOMEWORK, 0));
        return homework;
    }
}
