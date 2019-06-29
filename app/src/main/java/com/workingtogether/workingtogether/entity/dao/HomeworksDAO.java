package com.workingtogether.workingtogether.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.database.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.Homework;

import java.util.ArrayList;

public class HomeworksDAO {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public HomeworksDAO(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Homework> getAllHomeworks() {
        ArrayList<Homework> homeworkList = new ArrayList<>();

        sqLiteOpenHelper.openDatabase();
        StringBuilder query = new StringBuilder("SELECT UIDHOMEWORK, TITLE, DESCRIPTION, DELIVERDATE, PUBLISHDATE" +
                " FROM HOMEWORKS" +
                " ORDER BY PUBLISHDATE DESC");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Homework homework = new Homework();

            homework.setUIDHOMEWORK(cursor.getInt(0));
            homework.setTITLE(cursor.getString(1));
            homework.setDESCRIPTION(cursor.getString(2));
            homework.setDELIVERDATE(cursor.getString(3));
            homework.setPUBLISHDATE(cursor.getString(4));

            homeworkList.add(homework);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return homeworkList;
    }

    public Homework getHomeworkById(int UIDHOMEWORK) {
        Homework homework = new Homework();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDHOMEWORK)};
        StringBuilder query = new StringBuilder("SELECT UIDHOMEWORK, TITLE, DESCRIPTION, DELIVERDATE, PUBLISHDATE" +
                " FROM HOMEWORKS" +
                " WHERE UIDHOMEWORK = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            homework.setUIDHOMEWORK(cursor.getInt(0));
            homework.setTITLE(cursor.getString(1));
            homework.setDESCRIPTION(cursor.getString(2));
            homework.setDELIVERDATE(cursor.getString(3));
            homework.setPUBLISHDATE(cursor.getString(4));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return homework;
    }

    public void insertHomework(String TITLE, String DESCRIPTION, String DELIVERDATE, String PUBISHDATE) {
        sqLiteOpenHelper.openDatabase();

        try {
            mDatabase.execSQL("INSERT INTO HOMEWORKS (TITLE, DESCRIPTION, DELIVERDATE, PUBLISHDATE) " +
                    "VALUES ('" + TITLE + "', '" + DESCRIPTION + "', '" + DELIVERDATE + "', '" + PUBISHDATE + "')");
        } catch (SQLiteConstraintException c) {
            Log.d("Exception: ", c.getMessage());
        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());
        }
        sqLiteOpenHelper.closeDatabase();
    }

    public void deleteHomework(int UIDHOMEWORK) {
        sqLiteOpenHelper.openDatabase();
        String[] whereArgs = {Integer.toString(UIDHOMEWORK)};
        mDatabase.delete("HOMEWORKS","UIDHOMEWORK = ?", whereArgs);
        sqLiteOpenHelper.closeDatabase();

    }

}
