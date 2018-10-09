package com.workingtogether.workingtogether.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.obj.Activity;
import java.util.ArrayList;
import java.util.List;

public class ActivityDB {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public ActivityDB(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Activity> getAllActivities() {
        ArrayList<Activity> activitiesList = new ArrayList<>();

        sqLiteOpenHelper.openDatabase();
        StringBuilder query = new StringBuilder("SELECT UIDACTIVITY, TITLE, DESCRIPTION, DELIVERDATE, PUBLISHDATE" +
                " FROM ACTIVITIES" +
                " ORDER BY PUBLISHDATE DESC");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Activity activity = new Activity();

            activity.setUIDACTIVITY(cursor.getInt(0));
            activity.setTITLE(cursor.getString(1));
            activity.setDESCRIPTION(cursor.getString(2));
            activity.setDELIVERDATE(cursor.getString(3));
            activity.setPUBLISHDATE(cursor.getString(4));

            activitiesList.add(activity);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return activitiesList;
    }

    public Activity getActivityById(int UIDACTIVITY) {
        Activity activity = new Activity();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDACTIVITY)};
        StringBuilder query = new StringBuilder("SELECT UIDACTIVITY, TITLE, DESCRIPTION, DELIVERDATE, PUBLISHDATE" +
                " FROM ACTIVITIES" +
                " WHERE UIDACTIVITY = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            activity.setUIDACTIVITY(cursor.getInt(0));
            activity.setTITLE(cursor.getString(1));
            activity.setDESCRIPTION(cursor.getString(2));
            activity.setDELIVERDATE(cursor.getString(3));
            activity.setPUBLISHDATE(cursor.getString(4));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return activity;
    }

    public void insertActivity(String TITLE, String DESCRIPTION, String URL, String DELIVERDATE, String PUBISHDATE) {
        sqLiteOpenHelper.openDatabase();

        try {
            mDatabase.execSQL("INSERT INTO ACTIVITIES (TITLE, DESCRIPTION, URL, DELIVERDATE, PUBLISHDATE) " +
                    "VALUES ('" + TITLE + "', '" + DESCRIPTION + "', '" + URL + "', '" + DELIVERDATE + "', ' " + PUBISHDATE +"')");
        } catch (SQLiteConstraintException c) {
            Log.d("Exception: ", c.getMessage());
        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());
        }
        sqLiteOpenHelper.closeDatabase();
    }

}
