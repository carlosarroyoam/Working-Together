package com.workingtogether.workingtogether;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void signin(View view){
        final TextInputLayout userWrapper = findViewById(R.id.activity_signin_user_etxt);
        final TextInputLayout passwordWrapper = findViewById(R.id.activity_signin_pass_etxt);

        String user = userWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();

        Toast.makeText(this, "Hola " + user + " pass: " + password, Toast.LENGTH_SHORT).show();
    }

    public void forgotPassword(View view){
        Toast.makeText(this, "Olvidaste tu contrasena?", Toast.LENGTH_SHORT).show();
    }

    public void signup(View view){
        startActivity(new Intent(this, Signup.class));
        finish();
    }

}
