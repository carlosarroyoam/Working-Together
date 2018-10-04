package com.workingtogether.workingtogether.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.workingtogether.obj.Parent;
import com.workingtogether.workingtogether.obj.Teacher;

public class TeacherDB {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public TeacherDB(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public Teacher getTeacherById(int UIDTEACHER) {
        Teacher teacher = new Teacher();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDTEACHER)}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL" +
                " FROM TEACHERS" +
                " WHERE UIDTEACHER = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            teacher.setUIDTEACHER(cursor.getInt(0));
            teacher.setNAME(cursor.getString(1));
            teacher.setLASTNAME(cursor.getString(2));
            teacher.setEMAIL(cursor.getString(3));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return teacher;
    }

}
