package com.workingtogether.workingtogether.teacher;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.workingtogether.workingtogether.R;

import java.util.Calendar;

public class TeacherHomeworks extends AppCompatActivity {
    TextInputLayout date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_homeworks);
    }

    public void openCalendar(View view) {
        int day, month, year;
        Calendar c = Calendar.getInstance();
        date = findViewById(R.id.activity_teacher_homeworks_deliver_date);

        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, day, month, year);

        datePickerDialog.show();
    }

    public void sendHomework(View view){

    }

}
