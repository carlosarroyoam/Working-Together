package com.workingtogether.workingtogether.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.database.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.Message;

import java.util.ArrayList;

public class MessagesDAO {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public MessagesDAO(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Message> getAllMessagesByConversationId(int UIDCONVERSATION) {
        ArrayList<Message> messageArrayList = new ArrayList<>();

        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDCONVERSATION)};
        StringBuilder query = new StringBuilder("SELECT UIDMESSAGE, UIDCONVERSATION, UIDUSERFROM, UIDUSERTO, DATA, SENDDATE, DELIVERDATE, READEDDATE, SENDSTATE, DELIVERSTATE, READEDSTATE" +
                " FROM MESSAGES" +
                " WHERE UIDCONVERSATION = ?" +
                " ORDER BY SENDDATE ASC");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            Message message = new Message();

            message.setUIDMESSAGE(cursor.getInt(0));
            message.setUIDCONVERSATION(cursor.getInt(1));
            message.setUIDUSERFROM(cursor.getInt(2));
            message.setUIDUSERTO(cursor.getInt(3));
            message.setDATA(cursor.getString(4));
            message.setSENDDATE(cursor.getString(5));
            message.setDELIVERDATE(cursor.getString(6));
            message.setREADEDDATE(cursor.getString(7));
            message.setSENDSTATE(cursor.getInt(8));
            message.setDELIVERSTATE(cursor.getInt(9));
            message.setREADEDSTATE(cursor.getInt(10));

            messageArrayList.add(message);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return messageArrayList;
    }

    public Message getLastConversationMessage(int UIDCONVERSATION) {
        Message message = new Message();

        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDCONVERSATION)};
        StringBuilder query = new StringBuilder("SELECT UIDMESSAGE, UIDCONVERSATION, UIDUSERFROM, UIDUSERTO, DATA, SENDDATE, DELIVERDATE, READEDDATE, SENDSTATE, DELIVERSTATE, READEDSTATE" +
                " FROM MESSAGES" +
                " WHERE UIDCONVERSATION = ?" +
                " ORDER BY SENDDATE DESC" +
                " LIMIT 1");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {

            message.setUIDMESSAGE(cursor.getInt(0));
            message.setUIDCONVERSATION(cursor.getInt(1));
            message.setUIDUSERFROM(cursor.getInt(2));
            message.setUIDUSERTO(cursor.getInt(3));
            message.setDATA(cursor.getString(4));
            message.setSENDDATE(cursor.getString(5));
            message.setDELIVERDATE(cursor.getString(6));
            message.setREADEDDATE(cursor.getString(7));
            message.setSENDSTATE(cursor.getInt(8));
            message.setDELIVERSTATE(cursor.getInt(9));
            message.setREADEDSTATE(cursor.getInt(10));

        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return message;
    }

    public void insertMessage(int UIDCONVERSATION, int UIDUSERFROM, int UIDUSERTO, String DATA, String SENDDATE) {
        sqLiteOpenHelper.openDatabase();

        try {
            mDatabase.execSQL("INSERT INTO MESSAGES (UIDCONVERSATION, UIDUSERFROM, UIDUSERTO, DATA, SENDDATE, SENDSTATE) " +
                    "VALUES (" + UIDCONVERSATION+", "+UIDUSERFROM+", "+UIDUSERTO+", '" + DATA + "', '" + SENDDATE + "',  1)");
        } catch (SQLiteConstraintException c) {
            Log.d("Exception: ", c.getMessage());
        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());
        }
        sqLiteOpenHelper.closeDatabase();
    }

    public void deleteMessages(int UIDCONVERSATION) {
        sqLiteOpenHelper.openDatabase();
        String[] whereArgs = {Integer.toString(UIDCONVERSATION)};
        mDatabase.delete("MESSAGES", "UIDCONVERSATION = ?", whereArgs);
        sqLiteOpenHelper.closeDatabase();

    }

    public void deleteMessagesById(int UIDMESSAGE) {
        sqLiteOpenHelper.openDatabase();
        String[] whereArgs = {Integer.toString(UIDMESSAGE)};
        mDatabase.delete("MESSAGES", "UIDMESSAGE = ?", whereArgs);
        sqLiteOpenHelper.closeDatabase();

    }

}
