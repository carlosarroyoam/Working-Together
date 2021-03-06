package com.workingtogether.android.activities.teacher;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.workingtogether.android.R;
import com.workingtogether.android.entity.Homework;
import com.workingtogether.android.entity.dao.HomeworkDao;
import com.workingtogether.android.util.DatesUtils;
import com.workingtogether.android.util.firebaseConsoleWS;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class TeacherHomeworks extends AppCompatActivity {
    TextInputLayout titleTextInputLayout;
    TextInputLayout descTextInputLayout;
    TextInputLayout delDateTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_homeworks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleTextInputLayout = findViewById(R.id.activity_teacher_homeworks_title_etxt);
        descTextInputLayout = findViewById(R.id.activity_teacher_homeworks_description_etxt);
        delDateTextInputLayout = findViewById(R.id.activity_teacher_homeworks_deliver_date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_teacher_homework, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_dashboard_about) {
            Toast.makeText(this, "Working Together", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Aun no enviamos esta tarea");
        alertDialogBuilder
                .setMessage("¿Estas seguro que quieres cancelar?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void openCalendar(View view) {
        hideKeyboard();

        int day, month, year;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormatormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        String dateTime = dateFormatormat.format(c.getTime());
        String yearTime = yearFormat.format(c.getTime());
        String monthTime = monthFormat.format(c.getTime());
        String dayTime = dayFormat.format(c.getTime());

        year = Integer.valueOf(yearTime);
        month = Integer.valueOf(monthTime);
        day = Integer.valueOf(dayTime);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                delDateTextInputLayout.getEditText().setText(String.format("%s/%s/%s", dayOfMonth, (monthOfYear + 1), year));
            }
        }, year, month - 1, day);

        datePickerDialog.show();
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void sendHomework(View view) {
        String dateTime = DatesUtils.getDateTime();

        String title = titleTextInputLayout.getEditText().getText().toString();
        String description = descTextInputLayout.getEditText().getText().toString();
        String deliveryDate = delDateTextInputLayout.getEditText().getText().toString();


        if (!title.trim().equals("")) {

            if (!description.trim().equals("")) {

                if (!deliveryDate.trim().equals("")) {
                    Homework homework = new Homework();
                    homework.setTitle(title);
                    homework.setTitle(description);
                    homework.setDeliveryDate(deliveryDate);

					HomeworkDao.getInstance(this).create(homework);

                    //TODO reemplazar por un servicio de un servidor propio
                    StringBuilder json = new StringBuilder("{\"to\":\"/topics/NOTIFICACIONES\",\"data\":{\"TYPEUSER\":\"PARENT_USER\",\"NOTIFICATION_TYPE\":\"HOMEWORK_NOTIFICATION\",\"HOMEWORKCONTENT\":{\"TITLE\":\" " + title + "\",\"DESCRIPTION\":\"" + description + "\",\"DELIVERDATE\":\"" + deliveryDate + "\",\"PUBLISHDATE\":\"" + dateTime + "\"},\"ACTIVITYCONTENT\":{\"TITLE\":\"\",\"DESCRIPTION\":\"\",\"DELIVERDATE\":\"\",\"PUBLISHDATE\":\"\"},\"NOTESCONTENT\":{\"NOTE\":\"\"},\"MESSAGECONTENT\":{\"CONTENT\":\"\"}}}");

                    try {
                        JSONObject jsonArray = new JSONObject(String.valueOf(json));
                        firebaseConsoleWS firebaseConsoleWS =  new firebaseConsoleWS(jsonArray);

                        firebaseConsoleWS.execute();
                        succesDialog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    incorrectFormDialog(getResources().getString(R.string.select_delivery_date_msg));
                }
            } else {
                incorrectFormDialog(getResources().getString(R.string.insert_description_msg));
            }
        } else {
            incorrectFormDialog(getResources().getString(R.string.insert__title_msg));

        }
    }

    private void incorrectFormDialog(String dialogMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void succesDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.successful_published_homework_msg))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
