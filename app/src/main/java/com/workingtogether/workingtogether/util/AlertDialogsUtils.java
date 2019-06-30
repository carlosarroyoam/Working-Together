package com.workingtogether.workingtogether.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

/**
 * @author Carlos Alberto Arroyo Martinez <carlosarroyoam@gmail.com>
 */
public class AlertDialogsUtils {

    public static void informationDialog(Context context, String dialogTitle, String dialogMessage, String positiveButtonMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(positiveButtonMessage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void confirmationDialog(Context context, String dialogTitle, String dialogMessage, String positiveButtonMessage, DialogInterface.OnClickListener positiveButtonOnClick, String negativeButtonMessage, DialogInterface.OnClickListener negativeButtonOnClick) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(positiveButtonMessage, positiveButtonOnClick)
                .setNegativeButton(negativeButtonMessage, negativeButtonOnClick);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
