
package com.workingtogether.workingtogether.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.pager.SignupPagerAdapter;

public class SignupActivity extends AppCompatActivity {
    private ViewPager view_pager;
    private LinearLayout dots_layout;
    private SignupPagerAdapter signupPagerAdapter;
    private TextView[] mdots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view_pager = findViewById(R.id.activity_signup_slide_viewPager);
        dots_layout = findViewById(R.id.activity_signup_dots);

        signupPagerAdapter = new SignupPagerAdapter(this);
        view_pager.setAdapter(signupPagerAdapter);
        view_pager.setOffscreenPageLimit(0);

        addDotsIndicator(0);
        view_pager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Aun no creamos tu cuenta");
        alertDialogBuilder
                .setMessage("¿Estas seguro que quieres cancelar?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getApplicationContext(), SigninActivity.class));
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

    public void addDotsIndicator(int position) {
        mdots = new TextView[3];

        for (int i = 0; i < mdots.length; i++) {
            mdots[i] = new TextView(this);
            mdots[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            mdots[i].setText(Html.fromHtml("&#8226"));
            mdots[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            mdots[i].setTextSize(R.dimen.txt_text_dots_size);
            mdots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            dots_layout.addView(mdots[i]);
        }

        if (mdots.length > 0) {
            mdots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    public void signup(View view) {
        Toast.makeText(this, "Pantalla registro", Toast.LENGTH_SHORT).show();
    }

    public void teacherAccountSelected(View view) {
        Toast.makeText(this, "Cuenta de profesor", Toast.LENGTH_SHORT).show();
    }

    public void parentAccountSelected(View view) {
        Toast.makeText(this, "Cuenta de familiar", Toast.LENGTH_SHORT).show();
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
