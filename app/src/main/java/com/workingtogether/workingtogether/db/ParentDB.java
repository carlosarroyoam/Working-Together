package com.workingtogether.workingtogether.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.workingtogether.obj.Parent;
import com.workingtogether.workingtogether.obj.Teacher;
import com.workingtogether.workingtogether.obj.User;

import java.util.ArrayList;

public class ParentDB {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public ParentDB(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Parent> getParents() {
        ArrayList<Parent> parentsArrayList = new ArrayList<>();
        Parent parent = new Parent();
        sqLiteOpenHelper.openDatabase();
        StringBuilder query = new StringBuilder("SELECT UIDPARENT, NAME, LASTNAME, EMAIL, UIDTYPEUSER" +
                " FROM PARENTS");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            parent.setUIDUSER(cursor.getInt(0));
            parent.setNAME(cursor.getString(1));
            parent.setLASTNAME(cursor.getString(2));
            parent.setEMAIL(cursor.getString(3));
            parent.setUSERTYPE(cursor.getString(4));

            parentsArrayList.add(parent);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return parentsArrayList;
    }

    public Parent getParentById(int UIDPARENT) {
        Parent parent = new Parent();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDPARENT)}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDPARENT, NAME, LASTNAME, EMAIL, UIDTYPEUSER" +
                " FROM PARENTS" +
                " WHERE UIDPARENT = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            parent.setUIDUSER(cursor.getInt(0));
            parent.setNAME(cursor.getString(1));
            parent.setLASTNAME(cursor.getString(2));
            parent.setEMAIL(cursor.getString(3));
            parent.setUSERTYPE(cursor.getString(4));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return parent;
    }

}
