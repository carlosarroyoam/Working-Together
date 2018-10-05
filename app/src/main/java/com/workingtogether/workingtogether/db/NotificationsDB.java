package com.workingtogether.workingtogether.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.obj.Homework;
import com.workingtogether.workingtogether.obj.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsDB {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public NotificationsDB(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notificationList = new ArrayList<>();

        sqLiteOpenHelper.openDatabase();
        StringBuilder query = new StringBuilder("SELECT UIDNOTIFICATION, TITLE, DESCRIPTION, PUBLISHDATE" +
                " FROM NOTIFICATIONS" +
                " ORDER BY PUBLISHDATE DESC");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Notification notification = new Notification();

            notification.setUIDNOTIFICATION(cursor.getInt(0));
            notification.setTITLE(cursor.getString(1));
            notification.setDESCRIPTION(cursor.getString(2));
            notification.setPUBLISHDATE(cursor.getString(3));

            notificationList.add(notification);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return notificationList;
    }

    public Notification getNotificationById(int UIDHOMEWORK) {
        Notification notification = new Notification();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDHOMEWORK)};
        StringBuilder query = new StringBuilder("SELECT UIDNOTIFICATION, TITLE, DESCRIPTION, PUBLISHDATE" +
                " FROM NOTIFICATION" +
                " WHERE UIDNOTIFICATION = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            notification.setUIDNOTIFICATION(cursor.getInt(0));
            notification.setTITLE(cursor.getString(1));
            notification.setDESCRIPTION(cursor.getString(2));
            notification.setPUBLISHDATE(cursor.getString(3));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return notification;
    }

    public void insertNotification(String TITLE, String DESCRIPTION, String DATE, String NOTIFICATIONTYPE) {
        sqLiteOpenHelper.openDatabase();

        try {
            mDatabase.execSQL("INSERT INTO NOTIFICATIONS (TITLE, DESCRIPTION, DATE, NOTIFICATIONTYPE) " +
                    "VALUES ('" + TITLE + "', '" + DESCRIPTION + "', '" + DATE + "', ' " + NOTIFICATIONTYPE +"')");
        } catch (SQLiteConstraintException c) {
            Log.d("Exception: ", c.getMessage());
        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());
        }
        sqLiteOpenHelper.closeDatabase();
    }

}
