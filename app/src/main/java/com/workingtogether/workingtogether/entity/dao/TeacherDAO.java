package com.workingtogether.workingtogether.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.workingtogether.db.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.Teacher;

import java.util.ArrayList;

public class TeacherDAO {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public TeacherDAO(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> teachersArrayList = new ArrayList<>();
        Teacher teacher = new Teacher();
        sqLiteOpenHelper.openDatabase();
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL, UIDTYPEUSER" +
                " FROM TEACHERS");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            teacher.setUIDUSER(cursor.getInt(0));
            teacher.setNAME(cursor.getString(1));
            teacher.setLASTNAME(cursor.getString(2));
            teacher.setEMAIL(cursor.getString(3));
            teacher.setUSERTYPE(cursor.getString(4));

            teachersArrayList.add(teacher);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return teachersArrayList;
    }

    public Teacher getTeacherById(int UIDTEACHER) {
        Teacher teacher = new Teacher();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDTEACHER)}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL, UIDTYPEUSER" +
                " FROM TEACHERS" +
                " WHERE UIDTEACHER = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            teacher.setUIDUSER(cursor.getInt(0));
            teacher.setNAME(cursor.getString(1));
            teacher.setLASTNAME(cursor.getString(2));
            teacher.setEMAIL(cursor.getString(3));
            teacher.setUSERTYPE(cursor.getString(4));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return teacher;
    }

}
