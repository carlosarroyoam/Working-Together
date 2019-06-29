package com.workingtogether.workingtogether.activities.teacher;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.entity.dao.ActivityDAO;
import com.workingtogether.workingtogether.util.Util;
import com.workingtogether.workingtogether.util.firebaseConsoleWS;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TeacherActivities extends AppCompatActivity {
    private static final int PICKFILE_REQUEST_CODE = 1234;
    private TextInputLayout titleTextInputLayout;
    private TextInputLayout descTextInputLayout;
    private TextInputLayout urlTextInputLayout;
    private TextInputLayout delDateTextInputLayout;
    private LinearLayout layoutAttachedFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_activities);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleTextInputLayout = findViewById(R.id.activity_teacher_activities_title_etxt);
        descTextInputLayout = findViewById(R.id.activity_teacher_activities_description_etxt);
        urlTextInputLayout = findViewById(R.id.activity_teacher_activities_url);
        delDateTextInputLayout = findViewById(R.id.activity_teacher_activities_deliver_date);
        layoutAttachedFiles = findViewById(R.id.linearlayout_attached_files);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_teacher_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_dashboard_about:
                Toast.makeText(this, "Working Together", Toast.LENGTH_SHORT).show();
                break;
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
        alertDialogBuilder.setTitle("Aun no enviamos esta actividad");
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

        day = Integer.valueOf(dayTime);
        month = Integer.valueOf(monthTime);
        year = Integer.valueOf(yearTime);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                delDateTextInputLayout.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month - 1, day);

        datePickerDialog.show();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void sendHomework(View view) {
        String dateTime = Util.Dates.getDateTime();

        String title = titleTextInputLayout.getEditText().getText().toString();
        String description = descTextInputLayout.getEditText().getText().toString();
        String url = urlTextInputLayout.getEditText().getText().toString();
        String deliverDate = delDateTextInputLayout.getEditText().getText().toString();

        String dialogMessage = "";

        if (!title.trim().equals("")) {

            if (!description.trim().equals("")) {

                if (!deliverDate.trim().equals("")) {
                    ActivityDAO activityDAO = new ActivityDAO(this);
                    activityDAO.insertActivity(title, description, url, deliverDate, dateTime);

                    //TODO reemplazar por un servicio de un servidor propio
                    StringBuilder json = new StringBuilder("{\"to\":\"/topics/NOTIFICACIONES\",\"data\":{\"TYPEUSER\":\"PARENTUSER\",\"NOTIFICATION_TYPE\":\"ACTIVITYNOTIFICATION\",\"HOMEWORKCONTENT\":{\"TITLE\":\"INVESTIGACION\",\"DESCRIPTION\":\"Aquí estará todo el contenido de la tarea\",\"DELIVERDATE\":\"4/10/2018\",\"PUBLISHDATE\":\"4/10/2018 03:23:40\"},\"ACTIVITYCONTENT\":{\"TITLE\":\"" + title + "\",\"DESCRIPTION\":\"" + title + "\",\"URL\":\"" + url + "\",\"DELIVERDATE\":\"" + deliverDate + "\",\"PUBLISHDATE\":\"" + dateTime + "\"},\"NOTESCONTENT\":{\"NOTE\":\"\"},\"MESSAGECONTENT\":{\"CONTENT\":\"\"}}}");

                    try {
                        JSONObject jsonArray = new JSONObject(String.valueOf(json));
                        firebaseConsoleWS firebaseConsoleWS = new firebaseConsoleWS(jsonArray);

                        firebaseConsoleWS.execute();
                        succesDialog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    dialogMessage = "Selecciona una fecha de entrega";
                    incorrectFormDialog(dialogMessage);
                }

            } else {
                dialogMessage = "Ingresa una descripción para la actividad";
                incorrectFormDialog(dialogMessage);
            }

        } else {
            dialogMessage = "Ingresa un titulo para la actividad";
            incorrectFormDialog(dialogMessage);
        }
    }

    private void incorrectFormDialog(String dialogMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Working Together");
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void succesDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Working Together");
        alertDialogBuilder
                .setMessage("La tarea se publico con exito")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void attachURL(View view) {
        hideKeyboard();

        final Dialog dialog = new Dialog(this);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_attach_url);

        final EditText url = dialog.findViewById(R.id.dialog_url);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String attachedURL = url.getText().toString();
                if (!attachedURL.trim().equals("")) {
                    urlTextInputLayout.getEditText().setText(attachedURL);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public void attachFile(View view) {
        hideKeyboard();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            /*Uri uri = data.getData();
            File selectedFile = new File(uri.getPath());
            String filePath = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();
            String fileExtention = "";//filePath.substring(filePath.lastIndexOf("."));

            ImageView actualFile = new ImageView(this);
            actualFile.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            actualFile.setImageResource(R.drawable.ic_notes);
            layoutAttachedFiles.addView(actualFile);
            layoutAttachedFiles.setVisibility(View.VISIBLE);*/

            RelativeLayout relativeLayout = findViewById(R.id.relativelayout);
            Snackbar.make(relativeLayout, "Se adjunto el archivo.", Snackbar.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
