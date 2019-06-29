package com.workingtogether.workingtogether.entity.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.database.SQLiteOpenHelper;
import com.workingtogether.workingtogether.entity.Conversation;

import java.util.ArrayList;

public class ConversationsDAO {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public ConversationsDAO(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Conversation> getAllConversations() {
        ArrayList<Conversation> conversationList = new ArrayList<>();

        sqLiteOpenHelper.openDatabase();
        StringBuilder query = new StringBuilder("SELECT UIDCONVERSATION, UIDUSER" +
                " FROM CONVERSATIONS");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Conversation conversation = new Conversation();

            conversation.setUIDCONVERSATION(cursor.getInt(0));
            conversation.setUIDUSER(cursor.getInt(1));

            conversationList.add(conversation);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return conversationList;
    }

    public Conversation getConversationById(int UIDCONVERSATION) {
        Conversation conversation = new Conversation();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDCONVERSATION)};
        StringBuilder query = new StringBuilder("SELECT UIDCONVERSATION, UIDUSER" +
                " FROM CONVERSATIONS" +
                " WHERE UIDCONVERSATION = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            conversation.setUIDCONVERSATION(cursor.getInt(0));
            conversation.setUIDUSER(cursor.getInt(1));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return conversation;
    }

    public Conversation getConversationByContactId(int UIDUSER) {
        Conversation conversation = new Conversation();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDUSER)};
        StringBuilder query = new StringBuilder("SELECT UIDCONVERSATION, UIDUSER" +
                " FROM CONVERSATIONS" +
                " WHERE UIDUSER = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            conversation.setUIDCONVERSATION(cursor.getInt(0));
            conversation.setUIDUSER(cursor.getInt(1));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return conversation;
    }

    public void insertConversation(int UIDUSER) {
        sqLiteOpenHelper.openDatabase();

        try {
            mDatabase.execSQL("INSERT INTO CONVERSATIONS (UIDUSER) " +
                    "VALUES (" + UIDUSER + ")");
        } catch (SQLiteConstraintException c) {
            Log.d("Exception: ", c.getMessage());
        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());
        }
        sqLiteOpenHelper.closeDatabase();
    }

    public void deleteConversation(int UIDCONVERSATION) {
        sqLiteOpenHelper.openDatabase();
        String[] whereArgs = {Integer.toString(UIDCONVERSATION)};
        mDatabase.delete("CONVERSATIONS", "UIDCONVERSATION = ?", whereArgs);
        sqLiteOpenHelper.closeDatabase();

    }

}
