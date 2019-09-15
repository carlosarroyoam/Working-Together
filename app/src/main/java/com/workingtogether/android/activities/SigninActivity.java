package com.workingtogether.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.workingtogether.android.R;
import com.workingtogether.android.entity.User;
import com.workingtogether.android.entity.dao.SessionDAO;
import com.workingtogether.android.entity.dao.UserDAO;
import com.workingtogether.android.util.AlertDialogsUtils;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void signin(View view) {
		final TextInputLayout userWrapper = findViewById(R.id.email_textinputlayout);
		final TextInputLayout passwordWrapper = findViewById(R.id.password_textinputlayout);

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
