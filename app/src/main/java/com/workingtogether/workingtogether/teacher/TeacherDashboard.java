package com.workingtogether.workingtogether.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.Signin;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.obj.SessionApp;
import com.workingtogether.workingtogether.parent.ParentActivities;
import com.workingtogether.workingtogether.parent.ParentHomeworks;

public class TeacherDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout nav_drawer;
    private ActionBarDrawerToggle nav_drawer_toggle;
    private View nav_drawer_view;
    private View notification_drawer_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.layout_teacher_dashboard_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (nav_drawer == null || nav_drawer_view == null || notification_drawer_view == null || nav_drawer_toggle == null) {
            // Configure navigation drawer
            nav_drawer = findViewById(R.id.nav_drawer_layout);
            nav_drawer_view = findViewById(R.id.nav_drawer_view);
            notification_drawer_view = findViewById(R.id.notification_drawer_view);
            nav_drawer_toggle = new ActionBarDrawerToggle(this, nav_drawer, R.string.app_name, R.string.app_name) {

                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View drawerView) {
                    if (drawerView.equals(nav_drawer_view)) {
                        getSupportActionBar().setTitle(getTitle());
                        supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                        nav_drawer_toggle.syncState();
                    }
                }

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    if (drawerView.equals(nav_drawer_view)) {
                        getSupportActionBar().setTitle(getTitle());
                        supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                        nav_drawer_toggle.syncState();
                    }
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    // Avoid normal indicator glyph behaviour. This is to avoid glyph movement when opening the right drawer
                    //super.onDrawerSlide(drawerView, slideOffset);
                }
            };

            nav_drawer.addDrawerListener(nav_drawer_toggle); // Set the drawer toggle as the DrawerListener
            NavigationView navigation_nav_view = findViewById(R.id.nav_drawer_view);
            NavigationView notification_nav_view = findViewById(R.id.notification_drawer_view);
            navigation_nav_view.setNavigationItemSelectedListener(this); // Set navigation drawer on itemClickListener
            notification_nav_view.setNavigationItemSelectedListener(this); // Set navigation drawer on itemClickListener
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        nav_drawer_toggle.syncState(); // Sync the toggle state after onRestoreInstanceState has occurred.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        nav_drawer_toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(!nav_drawer.isDrawerOpen(nav_drawer_view));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                nav_drawer_toggle.onOptionsItemSelected(item);
                if (nav_drawer.isDrawerOpen(notification_drawer_view))
                    nav_drawer.closeDrawer(notification_drawer_view);
                break;
            case R.id.menu_dashboard_notifications:
                nav_drawer.openDrawer(GravityCompat.END);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                nav_drawer_toggle.syncState();
                break;
            case R.id.menu_dashboard_support:
                Toast.makeText(this, "Necesitas ayuda?", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_dashboard_about:
                Toast.makeText(this, "Working Together", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_navigation_log_off:
                showLogOffDialog();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (nav_drawer.isDrawerOpen(nav_drawer_view)) {
            nav_drawer.closeDrawer(nav_drawer_view);
        } else if (nav_drawer.isDrawerOpen(notification_drawer_view)) {
            nav_drawer.closeDrawer(notification_drawer_view);
        } else {
            super.onBackPressed();
        }
    }

    public void showLogOffDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Estas por salir de Working Together");
        alertDialogBuilder
                .setMessage("¿Estas seguro que quieres cerrar sesión?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logOff();
                        startActivity(new Intent(getApplicationContext(), Signin.class));
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

    public void logOff() {
        SessionDB sessionDB = new SessionDB(this);
        sessionDB.closeSession();
    }

    public void homeworksActivity(View view) {
        startActivity(new Intent(this, TeacherHomeworks.class));
    }

    public void activitiesActivity(View view) {
        startActivity(new Intent(this, TeacherActivities.class));
    }

}
