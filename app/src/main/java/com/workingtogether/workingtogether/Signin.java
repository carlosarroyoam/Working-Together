package com.workingtogether.workingtogether;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.db.UserDB;
import com.workingtogether.workingtogether.obj.User;

public class Signin extends AppCompatActivity {

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

        UserDB userDB = new UserDB(this);

        if (userDB.verifyLogin(email, password)) {
            User user = userDB.getUserDetails(email);
            addSession(user.getUIDUSER(), user.getUSERTYPE());
            startActivity(new Intent(this, Dashboard.class));
            finish();

        } else {
            String dialogMessage = "Contraseña o usuario incorrecto"; //TODO comprobar si usuario o contrasena son incorrectos
            incorrectLoginDialog(dialogMessage);
        }

    }

    private void addSession(int UIDUSER, String TYPEUSER) {
        SessionDB sessionDB = new SessionDB(this);
        sessionDB.addSession(UIDUSER, TYPEUSER);
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
        startActivity(new Intent(this, Signup.class));
        finish();
    }

}
