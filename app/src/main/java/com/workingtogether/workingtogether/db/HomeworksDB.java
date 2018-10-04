package com.workingtogether.workingtogether.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.workingtogether.obj.Homework;
import com.workingtogether.workingtogether.obj.Parent;

import java.util.ArrayList;
import java.util.List;

public class HomeworksDB {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public HomeworksDB(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public List<Homework> getAllHomeworksDetails() {
        List<Homework> homeworkList = new ArrayList<>();

        sqLiteOpenHelper.openDatabase();
        StringBuilder query = new StringBuilder("SELECT UIDHOMEWORK, TITLE, DESCRIPTION, DELIVERDATE, PUBLISHDATE" +
                " FROM HOMEWORKS");
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

}
