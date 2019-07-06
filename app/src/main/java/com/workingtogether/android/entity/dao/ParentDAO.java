package com.workingtogether.android.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.android.database.SQLiteOpenHelper;
import com.workingtogether.android.entity.Parent;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class ParentDAO {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase mDatabase;

    public ParentDAO(Context context) {
        sqLiteOpenHelper = null;
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Parent> getParents() {
        ArrayList<Parent> parentsArrayList = new ArrayList<>();
        Parent parent = new Parent();
        
        StringBuilder query = new StringBuilder("SELECT UIDPARENT, NAME, LASTNAME, EMAIL, UIDTYPEUSER" +
                " FROM PARENTS");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            parent.setId(cursor.getInt(0));
            parent.setFirstName(cursor.getString(1));
            parent.setLastName(cursor.getString(2));
            parent.setEmail(cursor.getString(3));
            parent.setUserType(cursor.getString(4));

            parentsArrayList.add(parent);
        }
        cursor.close();
        
        return parentsArrayList;
    }

    public Parent getParentById(int UIDPARENT) {
        Parent parent = new Parent();
        
        String[] selectArgs = {Integer.toString(UIDPARENT)}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDPARENT, NAME, LASTNAME, EMAIL, UIDTYPEUSER" +
                " FROM PARENTS" +
                " WHERE UIDPARENT = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            parent.setId(cursor.getInt(0));
            parent.setFirstName(cursor.getString(1));
            parent.setLastName(cursor.getString(2));
            parent.setEmail(cursor.getString(3));
            parent.setUserType(cursor.getString(4));
        }
        cursor.close();
        
        return parent;
    }

}
