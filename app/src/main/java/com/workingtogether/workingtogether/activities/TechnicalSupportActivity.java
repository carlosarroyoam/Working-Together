package com.workingtogether.workingtogether.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.workingtogether.workingtogether.R;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class TechnicalSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_support);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void sendMailToSupport(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "soporte@workingtogether.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SOPORTE TECNICO WORKING TOGETHER ANDROID");
        startActivity(Intent.createChooser(emailIntent, "Selecciona una aplicación para completar la acción"));
        finish();
    }

}
