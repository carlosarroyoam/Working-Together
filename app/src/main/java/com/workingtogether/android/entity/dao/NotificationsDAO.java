package com.workingtogether.android.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.android.database.DatabaseOpenHelper;
import com.workingtogether.android.entity.Notification;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class NotificationsDAO {
	private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase mDatabase;

    public NotificationsDAO(Context context) {
		databaseOpenHelper = null;
		mDatabase = databaseOpenHelper.getWritableDatabase();
    }

    public ArrayList<Notification> getAllNotifications() {
        ArrayList<Notification> notificationList = new ArrayList<>();

        
        StringBuilder query = new StringBuilder("SELECT UIDNOTIFICATION, TITLE, DESCRIPTION, DATE, NOTIFICATION_TYPE, UIDRESOURSE" +
                " FROM NOTIFICATIONS" +
                " ORDER BY DATE DESC");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Notification notification = new Notification();

            notification.setUIDNOTIFICATION(cursor.getInt(0));
            notification.setTITLE(cursor.getString(1));
            notification.setDESCRIPTION(cursor.getString(2));
            notification.setCreatedAt(cursor.getString(3));
            notification.setNOTIFICATIONTYPE(cursor.getString(4));
            notification.setUIDRESOURSE(cursor.getInt(5));

            notificationList.add(notification);
        }
        cursor.close();
        
        return notificationList;
    }

    public ArrayList<Notification> getLastestNotifications() {
        ArrayList<Notification> notificationList = new ArrayList<>();

        
        StringBuilder query = new StringBuilder("SELECT UIDNOTIFICATION, TITLE, DESCRIPTION, DATE, NOTIFICATION_TYPE, UIDRESOURSE" +
                " FROM NOTIFICATIONS" +
                " ORDER BY DATE DESC LIMIT 10");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Notification notification = new Notification();

            notification.setUIDNOTIFICATION(cursor.getInt(0));
            notification.setTITLE(cursor.getString(1));
            notification.setDESCRIPTION(cursor.getString(2));
            notification.setCreatedAt(cursor.getString(3));
            notification.setNOTIFICATIONTYPE(cursor.getString(4));
            notification.setUIDRESOURSE(cursor.getInt(5));

            notificationList.add(notification);
        }
        cursor.close();
        
        return notificationList;
    }

    public Notification getNotificationById(int UIDHOMEWORK) {
        Notification notification = new Notification();
        
        String[] selectArgs = {Integer.toString(UIDHOMEWORK)};
        StringBuilder query = new StringBuilder("SELECT UIDNOTIFICATION, TITLE, DESCRIPTION, DATE, NOTIFICATION_TYPE, UIDRESOURSE" +
                " FROM NOTIFICATION" +
                " WHERE UIDNOTIFICATION = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            notification.setUIDNOTIFICATION(cursor.getInt(0));
            notification.setTITLE(cursor.getString(1));
            notification.setDESCRIPTION(cursor.getString(2));
            notification.setCreatedAt(cursor.getString(3));
            notification.setNOTIFICATIONTYPE(cursor.getString(4));
            notification.setUIDRESOURSE(cursor.getInt(5));
        }
        cursor.close();
        
        return notification;
    }

    public void insertNotification(String TITLE, String DESCRIPTION, String DATE, String NOTIFICATIONTYPE, int UIDRESOURSE) {
        try {
            mDatabase.execSQL("INSERT INTO NOTIFICATIONS (TITLE, DESCRIPTION, DATE, NOTIFICATIONTYPE, UIDRESOURSE) " +
                    "VALUES ('" + TITLE + "', '" + DESCRIPTION + "', '" + DATE + "', '" + NOTIFICATIONTYPE + "', " + UIDRESOURSE +")");
        } catch (SQLiteConstraintException c) {
            Log.d("Exception: ", c.getMessage());
        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());
        }
    }

    public void deleteNotification(int UIDNOTIFICATION) {
        String[] whereArgs = {Integer.toString(UIDNOTIFICATION)};
        mDatabase.delete("NOTIFICATIONS","UIDNOTIFICATION = ?", whereArgs);
    }

}
