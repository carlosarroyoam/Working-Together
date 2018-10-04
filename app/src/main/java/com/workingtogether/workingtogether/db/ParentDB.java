package com.workingtogether.workingtogether.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.workingtogether.obj.Parent;
import com.workingtogether.workingtogether.obj.User;

public class ParentDB {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public ParentDB(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public Parent getParentById(int UIDPARENT) {
        Parent parent = new Parent();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDPARENT)}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDPARENT, NAME, LASTNAME, EMAIL" +
                " FROM PARENTS" +
                " WHERE UIDPARENT = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            parent.setUIDPARENT(cursor.getInt(0));
            parent.setNAME(cursor.getString(1));
            parent.setLASTNAME(cursor.getString(2));
            parent.setEMAIL(cursor.getString(3));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return parent;
    }

}
