package com.workingtogether.workingtogether.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.workingtogether.workingtogether.Conversations;
import com.workingtogether.workingtogether.obj.Conversation;
import com.workingtogether.workingtogether.obj.Notification;

import java.util.ArrayList;

public class ConversationsDB {
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase mDatabase;

    public ConversationsDB(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context);
        mDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public ArrayList<Conversation> getAllConversations() {
        ArrayList<Conversation> notificationList = new ArrayList<>();

        sqLiteOpenHelper.openDatabase();
        StringBuilder query = new StringBuilder("SELECT UIDCONVERSATION, CONTACTNAME, LASTMESSAGECONTENT, LASTMESSAGEDATE" +
                " FROM CONVERSATIONS" +
                " ORDER BY LASTMESSAGEDATE DESC");
        Cursor cursor = mDatabase.rawQuery(query.toString(), null);
        while (cursor.moveToNext()) {
            Conversation notification = new Conversation();

            /*notification.setUIDNOTIFICATION(cursor.getInt(0));
            notification.setTITLE(cursor.getString(1));
            notification.setDESCRIPTION(cursor.getString(2));
            notification.setPUBLISHDATE(cursor.getString(3));
            notification.setNOTIFICATIONTYPE(cursor.getString(4));
            notification.setUIDRESOURSE(cursor.getInt(5));*/

            notificationList.add(notification);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return notificationList;
    }

    public ArrayList<Conversation> getLastConversationMessage(int UIDMESSAGE) {
        ArrayList<Conversation> conversationsList = new ArrayList<>();

        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDMESSAGE)};
        StringBuilder query = new StringBuilder("SELECT UIDMESSAGE" +
                " FROM MESSAGES" +
                " WHERE UIDCONVERSATION = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            Conversation conversation = new Conversation();

            /*conversation.setUIDNOTIFICATION(cursor.getInt(0));
            conversation.setTITLE(cursor.getString(1));
            conversation.setDESCRIPTION(cursor.getString(2));
            conversation.setPUBLISHDATE(cursor.getString(3));
            conversation.setNOTIFICATIONTYPE(cursor.getString(4));
            conversation.setUIDRESOURSE(cursor.getInt(5));*/

            conversationsList.add(conversation);
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return conversationsList;
    }

    public Notification getConversationById(int UIDHOMEWORK) {
        Notification notification = new Notification();
        sqLiteOpenHelper.openDatabase();
        String[] selectArgs = {Integer.toString(UIDHOMEWORK)};
        StringBuilder query = new StringBuilder("SELECT UIDCONVERSATION, CONTACTNAME, LASTMESSAGECONTENT, LASTMESSAGEDATE" +
                " FROM CONVERSATIONS" +
                " WHERE UIDNOTIFICATION = ?");
        Cursor cursor = mDatabase.rawQuery(query.toString(), selectArgs);
        while (cursor.moveToNext()) {
            notification.setUIDNOTIFICATION(cursor.getInt(0));
            notification.setTITLE(cursor.getString(1));
            notification.setDESCRIPTION(cursor.getString(2));
            notification.setPUBLISHDATE(cursor.getString(3));
            notification.setNOTIFICATIONTYPE(cursor.getString(4));
            notification.setUIDRESOURSE(cursor.getInt(5));
        }
        cursor.close();
        sqLiteOpenHelper.closeDatabase();
        return notification;
    }

    public void insertConversation(String TITLE, String DESCRIPTION, String DATE, String NOTIFICATIONTYPE, int UIDRESOURSE) {
        sqLiteOpenHelper.openDatabase();

        try {
            mDatabase.execSQL("INSERT INTO NOTIFICATIONS (TITLE, DESCRIPTION, DATE, NOTIFICATIONTYPE, UIDRESOURSE) " +
                    "VALUES ('" + TITLE + "', '" + DESCRIPTION + "', '" + DATE + "', '" + NOTIFICATIONTYPE + "', " + UIDRESOURSE + ")");
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
