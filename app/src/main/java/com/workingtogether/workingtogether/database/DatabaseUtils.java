package com.workingtogether.workingtogether.database;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseUtils {

    /**
     * Copy database file from app/assets to application's directory in device storage.
     * Database will only be copied if it doesn't exists.
     *
     * @param context
     */
    public static void copyDatabaseToDevice(Context context) {
        String databasePath = context.getApplicationInfo().dataDir + "/databases";
        File databaseFolder = new File(databasePath);
        File databaseFile = new File(databasePath + "/" + DatabaseSchema.DATABASE_NAME);

        byte[] buffer = new byte[1024];
        int length;

        if (!databaseFolder.exists()) {
            databaseFolder.mkdir();
        }

        if (!databaseFile.exists()) {
            try (InputStream inputStream = context.getAssets().open(DatabaseSchema.DATABASE_NAME);
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
