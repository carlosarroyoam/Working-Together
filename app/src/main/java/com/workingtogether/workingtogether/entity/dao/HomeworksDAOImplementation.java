package com.workingtogether.workingtogether.entity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.workingtogether.database.DatabaseSchema;
import com.workingtogether.workingtogether.database.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.Homework;
import com.workingtogether.workingtogether.entity.dao.interfaces.HomeworksDAO;

import java.util.ArrayList;
import java.util.List;

public class HomeworksDAOImplementation implements HomeworksDAO {

    SQLiteOpenHelper mSQLiteOpenHelper;
    SQLiteDatabase mDatabase;

    private static HomeworksDAOImplementation HomeworkDAOImplementationInstance;

    public static HomeworksDAOImplementation getInstance(Context context) {
        if (HomeworkDAOImplementationInstance == null) {
            HomeworkDAOImplementationInstance = new HomeworksDAOImplementation(context);
        }

        return HomeworkDAOImplementationInstance;
    }

    private HomeworksDAOImplementation(Context context) {
        mSQLiteOpenHelper = new SQLiteOpenHelper(context);
    }

    @Override
    public List<Homework> getAll() {
        List<Homework> homeworkList = null;
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
        Cursor cursor = mDatabase.query(
                DatabaseSchema.HomeworksTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if (cursor.isBeforeFirst()) {
            homeworkList = new ArrayList<>();

            while (cursor.moveToNext()) {
                Homework homework = new Homework();
                homework.setId(cursor.getInt(0));
                homework.setTitle(cursor.getString(1));
                homework.setDescription(cursor.getString(2));
                homework.setCreatedAt(cursor.getString(3));
                homework.setDeliveryDate(cursor.getString(4));

                homeworkList.add(homework);
            }
        }

        cursor.close();
        return homeworkList;
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
        Cursor cursor = mDatabase.query(
                DatabaseSchema.HomeworksTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            homework = new Homework();
            homework.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.UUID)));
            homework.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.TITLE)));
            homework.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.DESCRIPTION)));
            homework.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.CREATED_AT)));
            homework.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.UPDATED_AT)));
            homework.setDeliveryDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseSchema.HomeworksTable.Cols.DELIVERY_DATE)));
        }

        cursor.close();

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

        mDatabase = mSQLiteOpenHelper.getWritableDatabase();
        Long newRowId = mDatabase.insert(DatabaseSchema.HomeworksTable.TABLE_NAME, null, contentValues);

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
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();

        return mDatabase.update(
                DatabaseSchema.HomeworksTable.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs) > 0;
    }

    @Override
    public boolean delete(Homework homework) {
        String selection = DatabaseSchema.HomeworksTable.Cols.UUID + " = ?";
        String[] selectionArgs = {String.valueOf(homework.getId())};
        mDatabase = mSQLiteOpenHelper.getWritableDatabase();

        return mDatabase.delete(
                DatabaseSchema.HomeworksTable.TABLE_NAME,
                selection,
                selectionArgs) > 0;
    }

    @Override
    public void closeDBHelper() {
        mSQLiteOpenHelper.close();
    }

}
