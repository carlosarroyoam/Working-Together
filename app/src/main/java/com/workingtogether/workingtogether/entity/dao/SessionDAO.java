package com.workingtogether.workingtogether.entity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.database.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.SessionApp;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class SessionDAO {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public SessionDAO(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public boolean isUserlogged() {
        
        String[] selectArgs = {Integer.toString(1)};
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM SESSION WHERE SESSTATE = ?", selectArgs);
        if (cursor.moveToFirst())
            return true;
        
        return false;
    }

    public SessionApp getUserlogged() {
        SessionApp sessionApp = new SessionApp();
        
        String[] selectArgs = {Integer.toString(1)};
        Cursor cursor = mDatabase.rawQuery("SELECT UIDUSER, TYPEUSER, SESSTATE FROM SESSION WHERE SESSTATE = ?", selectArgs);
        while (cursor.moveToNext()) {
            sessionApp.setUIDUSER(cursor.getInt(0));
            sessionApp.setTYPEUSER(cursor.getString(1));
            sessionApp.setSESSTATE(cursor.getInt(2));
        }
        

        return sessionApp;
    }


    public void addSession(int UIDUSER, String TYPEUSER) {

        try {
            
            mDatabase.execSQL("INSERT INTO SESSION (UIDUSER, TYPEUSER, SESSTATE) " +
                    "VALUES ('" + UIDUSER + "', '" + TYPEUSER + "', 1)");

        } catch (SQLiteConstraintException c) {
            updateSession(UIDUSER, 1);

        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());

        } finally {
            

        }

    }

    public void updateSession(int UIDUSER, int SESSTATE) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SESSTATE", SESSTATE);
        String[] whereArgs = {Integer.toString(UIDUSER)};

        
        mDatabase.update("SESSION", contentValues, "UIDUSER = ?", whereArgs);
        
    }

    public void closeSession() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SESSTATE", 0);
        String[] whereArgs = {Integer.toString(1)};

        
        mDatabase.update("SESSION", contentValues, "SESSTATE = ?", whereArgs);
        
    }

}
