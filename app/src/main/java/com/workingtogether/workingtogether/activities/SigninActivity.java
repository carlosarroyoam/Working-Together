package com.workingtogether.workingtogether.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.entity.User;
import com.workingtogether.workingtogether.entity.dao.SessionDAO;
import com.workingtogether.workingtogether.entity.dao.UserDAO;
import com.workingtogether.workingtogether.util.AlertDialogsUtils;

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

        if (!email.trim().equals("")) {
            if (!password.trim().equals("")) {
                UserDAO userDAO = new UserDAO(this);
                if (userDAO.verifyLogin(email, password)) {
                    User user = userDAO.getUserDetails(email);
                    addSession(user.getId(), user.getUserType());
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();

                } else {
                    AlertDialogsUtils.informationDialog(this, getResources().getString(R.string.app_name), getResources().getString(R.string.wrong_user_msg), getResources().getString(R.string.positive_button));
                }
            } else {
                AlertDialogsUtils.informationDialog(this, getResources().getString(R.string.app_name), getResources().getString(R.string.insert_password_msg), getResources().getString(R.string.positive_button));
            }
        } else {
            AlertDialogsUtils.informationDialog(this, getResources().getString(R.string.app_name), getResources().getString(R.string.insert_mail_msg), getResources().getString(R.string.positive_button));
        }
    }

    private void addSession(int UIDUSER, String TYPEUSER) {
        SessionDAO sessionDAO = new SessionDAO(this);
        sessionDAO.addSession(UIDUSER, TYPEUSER);
    }

    public void forgotPassword(View view) {
        Toast.makeText(this, getResources().getString(R.string.forgot_password_msg), Toast.LENGTH_SHORT).show();
    }

    public void signup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

}
