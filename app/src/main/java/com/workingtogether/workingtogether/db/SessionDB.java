package com.workingtogether.workingtogether.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.obj.SessionApp;

public class SessionDB {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public SessionDB(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    /*public List<Product> getListProduct() {
        Product product = null;
        List<Product> productList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM PRODUCT", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
            productList.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return productList;
    }
*/
    public boolean isUserlogged() {
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(1)};
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM SESSION WHERE SESSTATE = ?", selectArgs);
        if (cursor.moveToFirst())
            return true;
        //Only 1 resul
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return false;
    }

    public SessionApp getUserlogged() {
        SessionApp sessionApp = new SessionApp();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(1)};
        Cursor cursor = mDatabase.rawQuery("SELECT UIDUSER, TYPEUSER, SESSTATE FROM SESSION WHERE SESSTATE = ?", selectArgs);
        while (cursor.moveToNext()) {
            sessionApp.setUIDUSER(cursor.getInt(0));
            sessionApp.setTYPEUSER(cursor.getString(1));
            sessionApp.setSESSTATE(cursor.getInt(2));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();

        return sessionApp;
    }


    public void addSession(int UIDUSER, String TYPEUSER) {
        sqLiteOpenHelper.openDatabase();

        try {
            mDatabase.execSQL("INSERT INTO SESSION (UIDUSER, TYPEUSER, SESSTATE) " +
                    "VALUES ('" + UIDUSER + "', '" + TYPEUSER + "', 1)");

        } catch (SQLiteException e) {
            if(e.getMessage().equals("UNIQUE constraint failed: SESSION.UIDUSER (code 1555)")){
                updateSession(UIDUSER, 1);
            }
            Log.d("Exception: ", e.getMessage());
        }
        sqLiteOpenHelper.closeDatabase();
    }

    public void updateSession(int UIDUSER, int SESSTATE) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SESSTATE", SESSTATE);
        sqLiteOpenHelper.openDatabase();
        String[] whereArgs = {Integer.toString(UIDUSER)};
        mDatabase.update("SESSION", contentValues, "UIDUSER = ?", whereArgs);
        sqLiteOpenHelper.closeDatabase();
    }

    public void closeSession() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SESSTATE", 0);
        sqLiteOpenHelper.openDatabase();
        String[] whereArgs = {Integer.toString(1)};
        mDatabase.update("SESSION", contentValues, "SESSTATE = ?", whereArgs);
        sqLiteOpenHelper.closeDatabase();
    }


}
