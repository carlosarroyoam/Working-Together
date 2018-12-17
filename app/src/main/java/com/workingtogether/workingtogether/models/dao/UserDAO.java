package com.workingtogether.workingtogether.models.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.workingtogether.db.SQLiteOpenHelper;
import com.workingtogether.workingtogether.models.User;

public class UserDAO {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public UserDAO(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public User getUserDetails(String mail) {
        User user = new User();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {mail, mail}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL, UIDTYPEUSER FROM TEACHERS" +
                " WHERE EMAIL = ?" +
                " UNION" +
                " SELECT UIDPARENT, NAME, LASTNAME, EMAIL, UIDTYPEUSER FROM PARENTS" +
                " WHERE EMAIL = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            user.setUIDUSER(cursor.getInt(0));
            user.setNAME(cursor.getString(1));
            user.setLASTNAME(cursor.getString(2));
            user.setEMAIL(cursor.getString(3));
            user.setUSERTYPE(cursor.getString(4));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return user;
    }

    public User getUserDetails(int UIDUSER) {
        User user = new User();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDUSER), Integer.toString(UIDUSER)}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL, UIDTYPEUSER FROM TEACHERS" +
                " WHERE UIDTEACHER = ?" +
                " UNION" +
                " SELECT UIDPARENT, NAME, LASTNAME, EMAIL, UIDTYPEUSER FROM PARENTS" +
                " WHERE UIDPARENT = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            user.setUIDUSER(cursor.getInt(0));
            user.setNAME(cursor.getString(1));
            user.setLASTNAME(cursor.getString(2));
            user.setEMAIL(cursor.getString(3));
            user.setUSERTYPE(cursor.getString(4));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return user;
    }

    public boolean verifyLogin(String mail, String password) {
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {mail, password, mail, password}; //se repiten parametros para poder buscar en dos tablas
        StringBuilder query = new StringBuilder("SELECT * FROM TEACHERS" +
                " WHERE EMAIL = ? AND PASSWORD = ?" +
                " UNION" +
                " SELECT * FROM PARENTS" +
                " WHERE EMAIL = ? AND PASSWORD = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        if (cursor.moveToFirst())
            return true;
        //Only 1 resul
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return false;
    }

}
