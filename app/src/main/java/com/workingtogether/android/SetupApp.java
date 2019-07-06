package com.workingtogether.android;

import android.app.Application;

import com.workingtogether.android.firebase.NotificationsBuilder;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class SetupApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (isAppFirstRunOccurred()) {
            NotificationsBuilder.createNotificationChannels(this);
            markAppAsFirstRunOccurred();
        }
    }

    private boolean isAppFirstRunOccurred() {
        // TODO: 30/06/2019 Use shared preferences to implement this variable functionality
        return getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", false);
    }

    private void markAppAsFirstRunOccurred(){
//        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
//                .putBoolean("isFirstRun", false).apply();
    }
}