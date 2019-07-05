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

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class ConversationsDAO {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public ConversationsDAO(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Conversation> getAllConversations() {
        ArrayList<Conversation> conversationList = new ArrayList<>();

        
        StringBuilder query = new StringBuilder("SELECT UIDCONVERSATION, UIDUSER" +
                " FROM CONVERSATIONS");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Conversation conversation = new Conversation();

            conversation.setId(cursor.getInt(0));
            conversation.setIdUser(cursor.getInt(1));

            conversationList.add(conversation);
        }
        cursor.close();
        
        return conversationList;
    }

    public Conversation getConversationById(int UIDCONVERSATION) {
        Conversation conversation = new Conversation();
        
        String[] selectArgs = {Integer.toString(UIDCONVERSATION)};
        StringBuilder query = new StringBuilder("SELECT UIDCONVERSATION, UIDUSER" +
                " FROM CONVERSATIONS" +
                " WHERE UIDCONVERSATION = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            conversation.setId(cursor.getInt(0));
            conversation.setIdUser(cursor.getInt(1));
        }
        cursor.close();
        
        return conversation;
    }

    public Conversation getConversationByContactId(int UIDUSER) {
        Conversation conversation = new Conversation();
        
        String[] selectArgs = {Integer.toString(UIDUSER)};
        StringBuilder query = new StringBuilder("SELECT UIDCONVERSATION, UIDUSER" +
                " FROM CONVERSATIONS" +
                " WHERE UIDUSER = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            conversation.setId(cursor.getInt(0));
            conversation.setIdUser(cursor.getInt(1));
        }
        cursor.close();
        
        return conversation;
    }

    public void insertConversation(int UIDUSER) {
        

        try {
            mDatabase.execSQL("INSERT INTO CONVERSATIONS (UIDUSER) " +
                    "VALUES (" + UIDUSER + ")");
        } catch (SQLiteConstraintException c) {
            Log.d("Exception: ", c.getMessage());
        } catch (SQLiteException e) {
            Log.d("Exception: ", e.getMessage());
        }
        
    }

    public void deleteConversation(int UIDCONVERSATION) {
        
        String[] whereArgs = {Integer.toString(UIDCONVERSATION)};
        mDatabase.delete("CONVERSATIONS", "UIDCONVERSATION = ?", whereArgs);
        

    }

}
