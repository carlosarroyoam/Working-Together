package com.workingtogether.workingtogether.entity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.workingtogether.workingtogether.database.DatabaseSchema;
import com.workingtogether.workingtogether.database.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.Homework;
import com.workingtogether.workingtogether.entity.dao.interfaces.HomeworksDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class HomeworksDAOImplementation implements HomeworksDAO {

    private final String TAG = this.getClass().getSimpleName();

    private SQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mDatabase;

    private static HomeworksDAOImplementation ActivityDAOImplementationInstance;

    public static HomeworksDAOImplementation getInstance(Context context) {
        if (ActivityDAOImplementationInstance == null) {
            ActivityDAOImplementationInstance = new HomeworksDAOImplementation(context);
        }

        return ActivityDAOImplementationInstance;
    }

    private HomeworksDAOImplementation(Context context) {
        mSQLiteOpenHelper = SQLiteOpenHelper.getInstance(context);
    }

    @Override
    public List<Homework> getAll() {
        List<Homework> homeworksList = null;
        String[] projection = {
                DatabaseSchema.HomeworksTable.Cols.UUID,
                DatabaseSchema.HomeworksTable.Cols.TITLE,
                DatabaseSchema.HomeworksTable.Cols.DESCRIPTION,
                DatabaseSchema.HomeworksTable.Cols.CREATED_AT,
                DatabaseSchema.HomeworksTable.Cols.UPDATED_AT,
                DatabaseSchema.HomeworksTable.Cols.DELIVERY_DATE
        };
        String sortOrder = DatabaseSchema.HomeworksTable.Cols.UUID + " DESC";
        mDatabase = mSQLiteOpenHelper.getReadableDatabase();

        try (Cursor cursor = mDatabase.query(
                DatabaseSchema.HomeworksTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        )) {
            if (cursor.isBeforeFirst()) {
                homeworksList = new ArrayList<>();

                while (cursor.moveToNext()) {
                    Homework homework = new Homework();
                    homework.setId(cursor.getInt(0));
                    homework.setTitle(cursor.getString(1));
                    homework.setDescription(cursor.getString(2));
                    homework.setCreatedAt(cursor.getString(3));
                    homework.setDeliveryDate(cursor.getString(4));

                    homeworksList.add(homework);
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get records");
        }

        return homeworksList;
    }

    @Override
    public Homework get(int id) {
        Homework homework = null;
        String[] projection = {
                DatabaseSchema.HomeworksTable.Cols.UUID,
                DatabaseSchema.HomeworksTable.Cols.TITLE,
                DatabaseSchema.HomeworksTable.Cols.DESCRIPTION,
                DatabaseSchema.HomeworksTable.Cols.CREATED_AT,
                DatabaseSchema.HomeworksTable.Cols.UPDATED_AT,
                DatabaseSchema.HomeworksTable.Cols.DELIVERY_DATE,
        };
        String selection = DatabaseSchema.HomeworksTable.Cols.UUID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        String sortOrder = DatabaseSchema.HomeworksTable.Cols.UUID + " DESC";

        mDatabase = mSQLiteOpenHelper.getReadableDatabase();


        try (Cursor cursor = mDatabase.query(
                DatabaseSchema.HomeworksTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        )) {
            while (cursor.moveToNext()) {
                homework = new Homework();
                homework.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.UUID)));
                homework.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.TITLE)));
                homework.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.DESCRIPTION)));
                homework.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.CREATED_AT)));
                homework.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.UPDATED_AT)));
                homework.setDeliveryDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.DELIVERY_DATE)));
            }

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get record by id");
        }

        return homework;
    }

    @Override
    public Homework create(Homework homework) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.TITLE, homework.getTitle());
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.DESCRIPTION, homework.getDescription());
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.CREATED_AT, homework.getCreatedAt());
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.UPDATED_AT, homework.getUpdatedAt());
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.DELIVERY_DATE, homework.getDeliveryDate());

        Long newRowId = Long.valueOf("-1");
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mDatabase.beginTransaction();

        try {
            newRowId = mDatabase.insertOrThrow(DatabaseSchema.HomeworksTable.TABLE_NAME, null, contentValues);
            mDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to insert record");
        } finally {
            mDatabase.endTransaction();
        }

        return get(newRowId.intValue());
    }

    @Override
    public boolean update(Homework homework) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.TITLE, homework.getTitle());
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.DESCRIPTION, homework.getDescription());
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.CREATED_AT, homework.getCreatedAt());
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.UPDATED_AT, homework.getUpdatedAt());
        contentValues.put(DatabaseSchema.HomeworksTable.Cols.DELIVERY_DATE, homework.getDeliveryDate());

        String selection = DatabaseSchema.HomeworksTable.Cols.UUID + " = ?";
        String[] selectionArgs = {String.valueOf(homework.getId())};
        boolean thereAreUpdatedRows = false;
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mDatabase.beginTransaction();

        try {
            thereAreUpdatedRows = mDatabase.update(
                    DatabaseSchema.HomeworksTable.TABLE_NAME,
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
    public boolean delete(Homework homework) {
        String selection = DatabaseSchema.HomeworksTable.Cols.UUID + " = ?";
        String[] selectionArgs = {String.valueOf(homework.getId())};
        boolean thereAreDeletedRows = false;
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mDatabase.beginTransaction();

        try {
            thereAreDeletedRows = mDatabase.delete(
                    DatabaseSchema.HomeworksTable.TABLE_NAME,
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
