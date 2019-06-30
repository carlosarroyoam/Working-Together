package com.workingtogether.workingtogether.entity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.workingtogether.database.DatabaseSchema;
import com.workingtogether.workingtogether.database.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.Activity;
import com.workingtogether.workingtogether.entity.dao.interfaces.ActivityDAOInterface;

import java.util.ArrayList;
import java.util.List;

public class ActivityDAOImplementation implements ActivityDAOInterface {

    SQLiteOpenHelper mSQLiteOpenHelper;
    SQLiteDatabase mDatabase;

    private static ActivityDAOImplementation ActivityDAOImplementationInstance;

    public static ActivityDAOImplementation getInstance(Context context) {
        if (ActivityDAOImplementationInstance == null) {
            ActivityDAOImplementationInstance = new ActivityDAOImplementation(context);
        }

        return ActivityDAOImplementationInstance;
    }

    private ActivityDAOImplementation(Context context) {
        mSQLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    @Override
    public List<Activity> getAll() {
        List<Activity> activitiesList = null;
        String[] projection = {
                DatabaseSchema.ActivitiesTable.Cols.UUID,
                DatabaseSchema.ActivitiesTable.Cols.TITLE,
                DatabaseSchema.ActivitiesTable.Cols.DESCRIPTION,
                DatabaseSchema.ActivitiesTable.Cols.CREATED_AT,
                DatabaseSchema.ActivitiesTable.Cols.UPDATED_AT,
                DatabaseSchema.ActivitiesTable.Cols.DELIVERY_DATE
        };
        String sortOrder = DatabaseSchema.ActivitiesTable.Cols.UUID + " DESC";
        mSQLiteOpenHelper.openDatabase();

        Cursor cursor = mDatabase.query(
                DatabaseSchema.ActivitiesTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if (cursor.isBeforeFirst()) {
            activitiesList = new ArrayList<>();

            while (cursor.moveToNext()) {
                Activity activity = new Activity();

                activity.setId(cursor.getInt(0));
                activity.setTitle(cursor.getString(1));
                activity.setDescription(cursor.getString(2));
                activity.setCreatedAt(cursor.getString(3));
                activity.setDeliveryDate(cursor.getString(4));

                activitiesList.add(activity);
            }
        }

        cursor.close();
        mSQLiteOpenHelper.closeDatabase();
        return activitiesList;
    }

    @Override
    public Activity get(int id) {
        Activity activity = null;
        String[] projection = {
                DatabaseSchema.ActivitiesTable.Cols.UUID,
                DatabaseSchema.ActivitiesTable.Cols.TITLE,
                DatabaseSchema.ActivitiesTable.Cols.DESCRIPTION,
                DatabaseSchema.ActivitiesTable.Cols.CREATED_AT,
                DatabaseSchema.ActivitiesTable.Cols.UPDATED_AT,
                DatabaseSchema.ActivitiesTable.Cols.DELIVERY_DATE,
        };
        String selection = DatabaseSchema.ActivitiesTable.Cols.UUID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        String sortOrder = DatabaseSchema.ActivitiesTable.Cols.UUID + " DESC";
        mSQLiteOpenHelper.openDatabase();

        Cursor cursor = mDatabase.query(
                DatabaseSchema.ActivitiesTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            activity = new Activity();

            activity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.UUID)));
            activity.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.TITLE)));
            activity.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.DESCRIPTION)));
            activity.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.CREATED_AT)));
            activity.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.UPDATED_AT)));
            activity.setDeliveryDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.DELIVERY_DATE)));
        }

        cursor.close();
        mSQLiteOpenHelper.closeDatabase();

        return activity;
    }

    @Override
    public Activity create(Activity activity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.TITLE, activity.getTitle());
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.DESCRIPTION, activity.getDescription());
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.CREATED_AT, activity.getCreatedAt());
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.UPDATED_AT, activity.getUpdatedAt());
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.DELIVERY_DATE, activity.getDeliveryDate());

        mSQLiteOpenHelper.openDatabase();
        Long newRowId = mDatabase.insert(DatabaseSchema.ActivitiesTable.TABLE_NAME, null, contentValues);
        mSQLiteOpenHelper.closeDatabase();

        return get(newRowId.intValue());
    }

    @Override
    public boolean update(Activity activity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.TITLE, activity.getTitle());
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.DESCRIPTION, activity.getDescription());
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.CREATED_AT, activity.getCreatedAt());
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.UPDATED_AT, activity.getUpdatedAt());
        contentValues.put(DatabaseSchema.ActivitiesTable.Cols.DELIVERY_DATE, activity.getDeliveryDate());

        String selection = DatabaseSchema.ActivitiesTable.Cols.UUID + " = ?";
        String[] selectionArgs = {String.valueOf(activity.getId())};
        mSQLiteOpenHelper.openDatabase();
        boolean thereAreUpdatedRows = (mDatabase.update(
                DatabaseSchema.ActivitiesTable.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs)) > 0 ? true : false;

        mSQLiteOpenHelper.closeDatabase();

        return thereAreUpdatedRows;
    }

    @Override
    public boolean delete(Activity activity) {
        String selection = DatabaseSchema.ActivitiesTable.Cols.UUID + " = ?";
        String[] selectionArgs = {String.valueOf(activity.getId())};
        mSQLiteOpenHelper.openDatabase();
        boolean thereAreDeletedRows = (mDatabase.delete(
                DatabaseSchema.ActivitiesTable.TABLE_NAME,
                selection,
                selectionArgs) > 0) ? true : false;

        mSQLiteOpenHelper.closeDatabase();
        return thereAreDeletedRows;
    }

}
