package com.workingtogether.workingtogether.database;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class provides method to manage databases files
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class DatabaseUtils {

    /**
     * Copies the database file from app/assets to application's private folder in device storage.
     * Database will only be copied only if it doesn't exists.
     *
     * @param context Context from Activity where method is called
     */
    public static void copyDatabaseToDevice(Context context) {
        String databasePath = context.getApplicationContext().getApplicationInfo().dataDir + "/databases";
        File databaseFolder = new File(databasePath);
        File databaseFile = new File(databasePath + "/" + DatabaseSchema.DATABASE_NAME);

        byte[] buffer = new byte[1024];
        int length;

        if (!databaseFolder.exists()) {
            databaseFolder.mkdir();
        }

        if (!databaseFile.exists()) {
            try (InputStream inputStream = context.getApplicationContext().getAssets().open(DatabaseSchema.DATABASE_NAME);
                 OutputStream outputStream = new FileOutputStream(databasePath + "/" + DatabaseSchema.DATABASE_NAME)) {

                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
