package com.workingtogether.android.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.workingtogether.android.database.DatabaseOpenHelper;
import com.workingtogether.android.entity.User;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class UserDAO {

	private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase mDatabase;

    public UserDAO(Context context) {
		databaseOpenHelper = null;
		mDatabase = databaseOpenHelper.getWritableDatabase();
    }

    public User getUserDetails(String mail) {
        User user = new User();
        String[] selectArgs = {mail, mail}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL, UIDTYPEUSER FROM TEACHERS" +
                " WHERE EMAIL = ?" +
                " UNION" +
                " SELECT UIDPARENT, NAME, LASTNAME, EMAIL, UIDTYPEUSER FROM PARENTS" +
                " WHERE EMAIL = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            user.setId(cursor.getInt(0));
            user.setFirstName(cursor.getString(1));
            user.setLastName(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setUserType(cursor.getString(4));
        }
        cursor.close();
        return user;
    }

    public User getUserDetails(int UIDUSER) {
        User user = new User();
        String[] selectArgs = {Integer.toString(UIDUSER), Integer.toString(UIDUSER)}; // para buscar en las dos tablas repetimos el parametro
        StringBuilder query = new StringBuilder("SELECT UIDTEACHER, NAME, LASTNAME, EMAIL, UIDTYPEUSER FROM TEACHERS" +
                " WHERE UIDTEACHER = ?" +
                " UNION" +
                " SELECT UIDPARENT, NAME, LASTNAME, EMAIL, UIDTYPEUSER FROM PARENTS" +
                " WHERE UIDPARENT = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            user.setId(cursor.getInt(0));
            user.setFirstName(cursor.getString(1));
            user.setLastName(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setUserType(cursor.getString(4));
        }
        cursor.close();
        return user;
    }

    public boolean verifyLogin(String mail, String password) {
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
        return false;
    }

}
