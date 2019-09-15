package com.workingtogether.android;

import android.app.Application;

import com.workingtogether.android.firebase.NotificationsBuilder;

/**
 * This class contains methods to prepare app on first run
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class SetupApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

		if (!hasFirstRunBeenOccurred()) {
            NotificationsBuilder.createNotificationChannels(this);
			checkAppsFirstRun();
        }
    }

	/**
	 * Returns true if app's first run has been occurred, or false
	 * in case it doesn't happened yet
	 *
	 * @return boolean the app first run status
	 */
	private boolean hasFirstRunBeenOccurred() {
        // TODO: 30/06/2019 Use shared preferences to implement this variable functionality
        return getSharedPreferences("PREFERENCE", MODE_PRIVATE)
				.getBoolean("hasFirstRunBeenOccurred", false);
    }

	/**
	 * After app's first run has been occurred, this method will save
	 * that information in a shared preference
	 */
	private void checkAppsFirstRun() {
//        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
//                .putBoolean("isFirstRunOccurred", true).apply();
	}

}
