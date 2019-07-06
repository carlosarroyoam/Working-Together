package com.workingtogether.android.entity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.workingtogether.android.database.DatabaseSchema;
import com.workingtogether.android.database.SQLiteOpenHelper;
import com.workingtogether.android.entity.Activity;
import com.workingtogether.android.entity.dao.interfaces.ActivityDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class ActivityDAOImplementation implements ActivityDAO {

    private final String TAG = this.getClass().getSimpleName();

    private SQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mDatabase;

    private static ActivityDAOImplementation ActivityDAOImplementationInstance;

    public static ActivityDAOImplementation getInstance(Context context) {
        if (ActivityDAOImplementationInstance == null) {
            ActivityDAOImplementationInstance = new ActivityDAOImplementation(context);
        }

        return ActivityDAOImplementationInstance;
    }

    private ActivityDAOImplementation(Context context) {
        mSQLiteOpenHelper = SQLiteOpenHelper.getInstance(context);
    }

    @Override
    public List<Activity> getAll() {
        String[] projection = {
                DatabaseSchema.ActivitiesTable.Cols.UUID,
                DatabaseSchema.ActivitiesTable.Cols.TITLE,
                DatabaseSchema.ActivitiesTable.Cols.DESCRIPTION,
                DatabaseSchema.ActivitiesTable.Cols.CREATED_AT,
                DatabaseSchema.ActivitiesTable.Cols.UPDATED_AT,
                DatabaseSchema.ActivitiesTable.Cols.DELIVERY_DATE
        };
        String sortOrder = DatabaseSchema.ActivitiesTable.Cols.UUID + " DESC";
        mDatabase = mSQLiteOpenHelper.getReadableDatabase();

        try (Cursor cursor = mDatabase.query(
                DatabaseSchema.ActivitiesTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        )) {
            if (cursor.isBeforeFirst()) {
                List<Activity> activitiesList = new ArrayList<>();

                while (cursor.moveToNext()) {
                    Activity activity = new Activity();
                    activity.setId(cursor.getInt(0));
                    activity.setTitle(cursor.getString(1));
                    activity.setDescription(cursor.getString(2));
                    activity.setCreatedAt(cursor.getString(3));
                    activity.setDeliveryDate(cursor.getString(4));

                    activitiesList.add(activity);
                }

                return activitiesList;
            }

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get records");
        }

        return Collections.emptyList();
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

        mDatabase = mSQLiteOpenHelper.getReadableDatabase();


        try (Cursor cursor = mDatabase.query(
                DatabaseSchema.ActivitiesTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        )) {
            while (cursor.moveToNext()) {
                activity = new Activity();
                activity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.UUID)));
                activity.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.TITLE)));
                activity.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.DESCRIPTION)));
                activity.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.CREATED_AT)));
                activity.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.UPDATED_AT)));
                activity.setDeliveryDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.ActivitiesTable.Cols.DELIVERY_DATE)));
            }

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get record by id");
        }

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

        Long newRowId = Long.valueOf("-1");
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mDatabase.beginTransaction();

        try {
            newRowId = mDatabase.insertOrThrow(DatabaseSchema.ActivitiesTable.TABLE_NAME, null, contentValues);
            mDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to insert record");
        } finally {
            mDatabase.endTransaction();
        }

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
        boolean thereAreUpdatedRows = false;
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mDatabase.beginTransaction();

        try {
            thereAreUpdatedRows = mDatabase.update(
                    DatabaseSchema.ActivitiesTable.TABLE_NAME,
                    contentValues,
                    selection,
                    selectionArgs) > 0;
            mDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to update record");
        } finally {
            mDatabase.endTransaction();
        }

        return thereAreUpdatedRows;
    }

    @Override
    public boolean delete(Activity activity) {
        String selection = DatabaseSchema.ActivitiesTable.Cols.UUID + " = ?";
        String[] selectionArgs = {String.valueOf(activity.getId())};
        boolean thereAreDeletedRows = false;
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mDatabase.beginTransaction();

        try {
            thereAreDeletedRows = mDatabase.delete(
                    DatabaseSchema.ActivitiesTable.TABLE_NAME,
                    selection,
                    selectionArgs) > 0;
            mDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete record");
        } finally {
            mDatabase.endTransaction();
        }

        return thereAreDeletedRows;
    }

    @Override
    public void closeDBHelper() {
        mSQLiteOpenHelper.close();
    }

}
