package com.workingtogether.workingtogether.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {

    public static class Dates {

        public static String getDateTime() {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormatormat = new SimpleDateFormat("dd/MM/yyyy h:mm a");
            String dateTime = dateFormatormat.format(c.getTime());
            return dateTime;
        }

    }

    public static class AleretDialogs {

        public static void informationDialog(Context context, String dialogTitle, String dialogMessage) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(dialogTitle);
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

        public static void confirmationDialog(Context context, String dialogTitle, String dialogMessage, DialogInterface.OnClickListener positiveButtonOnClick, DialogInterface.OnClickListener negativeButtonOnClick) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(dialogTitle);
            alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton("Si", positiveButtonOnClick)
                    .setNegativeButton("No", negativeButtonOnClick);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

}
