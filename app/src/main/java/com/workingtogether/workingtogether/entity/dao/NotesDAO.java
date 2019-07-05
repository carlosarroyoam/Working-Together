package com.workingtogether.workingtogether.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.database.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.Note;

import java.util.ArrayList;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class NotesDAO {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public NotesDAO(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> noteArrayList = new ArrayList<>();

        
        StringBuilder query = new StringBuilder("SELECT UIDNOTE, NOTE, SUBJECT" +
                " FROM NOTES");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Note note = new Note();

            note.setUIDNOTE(cursor.getInt(0));
            note.setNOTE(cursor.getDouble(1));
            note.setSUBJECT(cursor.getString(2));

            noteArrayList.add(note);
        }
        cursor.close();
        
        return noteArrayList;
    }

    public void insertNote(String TITLE, String DESCRIPTION, String DELIVERDATE, String PUBISHDATE) {
        

        try {
            //TODO crar query para insertar calificacion
            mDatabase.execSQL("INSERT INTO NOTES (TITLE, DESCRIPTION, DELIVERDATE, PUBLISHDATE) " +
                    "VALUES ('" + TITLE + "', '" + DESCRIPTION + "', '" + DELIVERDATE + "', ' " + PUBISHDATE +"')");
        } catch (SQLiteConstraintException c) {
            Log.d("Exception: ", c.getMessage());
        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());
        }
        
    }

}
