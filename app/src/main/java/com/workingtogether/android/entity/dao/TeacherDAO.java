package com.workingtogether.android.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.android.database.SQLiteOpenHelper;
import com.workingtogether.android.entity.Teacher;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class TeacherDAO {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase mDatabase;

    public TeacherDAO(Context context) {
        sqLiteOpenHelper = null;
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> teachersArrayList = new ArrayList<>();
        Teacher teacher = new Teacher();
        
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL, UIDTYPEUSER" +
                " FROM TEACHERS");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            teacher.setId(cursor.getInt(0));
            teacher.setFirstName(cursor.getString(1));
            teacher.setLastName(cursor.getString(2));
            teacher.setEmail(cursor.getString(3));
            teacher.setUserType(cursor.getString(4));

            teachersArrayList.add(teacher);
        }
        cursor.close();
        
        return teachersArrayList;
    }

    public Teacher getTeacherById(int UIDTEACHER) {
        Teacher teacher = new Teacher();
        
        String[] selectArgs = {Integer.toString(UIDTEACHER)}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL, UIDTYPEUSER" +
                " FROM TEACHERS" +
                " WHERE UIDTEACHER = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            teacher.setId(cursor.getInt(0));
            teacher.setFirstName(cursor.getString(1));
            teacher.setLastName(cursor.getString(2));
            teacher.setEmail(cursor.getString(3));
            teacher.setUserType(cursor.getString(4));
        }
        cursor.close();
        
        return teacher;
    }

}
