package com.workingtogether.workingtogether.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.models.User;
import com.workingtogether.workingtogether.models.dao.SessionDAO;
import com.workingtogether.workingtogether.models.dao.UserDAO;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void signin(View view) {
        final TextInputLayout userWrapper = findViewById(R.id.activity_signin_user_etxt);
        final TextInputLayout passwordWrapper = findViewById(R.id.activity_signin_pass_etxt);

        String email = userWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();
        String dialogMessage = "";

        if (!email.trim().equals("")) {
            if (!password.trim().equals("")) {
                UserDAO userDAO = new UserDAO(this);
                if (userDAO.verifyLogin(email, password)) {
                    User user = userDAO.getUserDetails(email);
                    addSession(user.getUIDUSER(), user.getUSERTYPE());
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();

                } else {
                    dialogMessage = "Contraseña o usuario incorrecto";
                    incorrectLoginDialog(dialogMessage);
                }
            } else {
                dialogMessage = "Ingresa tu contraseña";
                incorrectLoginDialog(dialogMessage);
            }
        } else {
            dialogMessage = "Ingresa tu correo electrónico";
            incorrectLoginDialog(dialogMessage);
        }
    }

    private void addSession(int UIDUSER, String TYPEUSER) {
        SessionDAO sessionDAO = new SessionDAO(this);
        sessionDAO.addSession(UIDUSER, TYPEUSER);
    }

    private void incorrectLoginDialog(String dialogMessage) {
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

    public void forgotPassword(View view) {
        Toast.makeText(this, "Olvidaste tu contrasena?", Toast.LENGTH_SHORT).show();
    }

    public void signup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

}
