package com.workingtogether.workingtogether.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.adapter.list.ConversationDetailListAdapter;
import com.workingtogether.workingtogether.entity.Conversation;
import com.workingtogether.workingtogether.entity.Message;
import com.workingtogether.workingtogether.entity.SessionApp;
import com.workingtogether.workingtogether.entity.User;
import com.workingtogether.workingtogether.entity.dao.ConversationsDAO;
import com.workingtogether.workingtogether.entity.dao.MessagesDAO;
import com.workingtogether.workingtogether.entity.dao.SessionDAO;
import com.workingtogether.workingtogether.entity.dao.UserDAO;
import com.workingtogether.workingtogether.util.GlobalParams;
import com.workingtogether.workingtogether.util.Util;
import com.workingtogether.workingtogether.util.firebaseConsoleWS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConversationDetailsActivity extends AppCompatActivity {
    private ListView messages_listview;
    private ConversationDetailListAdapter mAdapter;
    private ArrayList<Message> mMessagesDataset;
    private EditText messageEditText;
    MyReceiver myReceiver;
    private int UIDCONVERSATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        messageEditText = findViewById(R.id.editText);

        User conversationUser = getUser();
        getSupportActionBar().setTitle(conversationUser.getNAME() + " " + conversationUser.getLASTNAME());

        UIDCONVERSATION = getConversationId();

        mMessagesDataset = new ArrayList<>();
        mMessagesDataset.addAll(loadMessagesList());

        mAdapter = new ConversationDetailListAdapter(this, mMessagesDataset);
        messages_listview = findViewById(R.id.list_view_conversations);
        messages_listview.setAdapter(mAdapter);
        messages_listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        scrollMyListViewToBottom();
        myReceiver = new MyReceiver();
    }

    private int getConversationId() {
        return getIntent().getIntExtra(GlobalParams.UIDCONVERSATION, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getPackageName() + ".newMessage");
        registerReceiver(myReceiver, filter);

    }

    private User getUser() {
        UserDAO userDAO = new UserDAO(this);
        return userDAO.getUserDetails(getIntent().getIntExtra(GlobalParams.UIDUSER, 0));
    }

    private ArrayList<Message> loadMessagesList() {
        MessagesDAO messagesDAO = new MessagesDAO(this);
        ArrayList<Message> messageArrayList = messagesDAO.getAllMessagesByConversationId(UIDCONVERSATION);
        return messageArrayList;
    }

    private void scrollMyListViewToBottom() {
        messages_listview.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                messages_listview.setSelection(messages_listview.getCount() - 1);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void sendMessage(View view) {
        SessionDAO sessionDAO = new SessionDAO(this);
        SessionApp usuarioLogeado = sessionDAO.getUserlogged();
        User conversationUser = getUser();

        if (!messageEditText.getText().toString().trim().equals("")) {
            if (UIDCONVERSATION > 0) {
                insertMessage(UIDCONVERSATION, usuarioLogeado.getUIDUSER(), conversationUser.getUIDUSER(), messageEditText.getText().toString());
                firebaseSend(usuarioLogeado.getUIDUSER(), conversationUser.getUIDUSER(), messageEditText.getText().toString());
            } else {
                ConversationsDAO conversationsDAO = new ConversationsDAO(this);
                conversationsDAO.insertConversation(conversationUser.getUIDUSER());
                Conversation conversation = conversationsDAO.getConversationByContactId(conversationUser.getUIDUSER());
                insertMessage(conversation.getUIDCONVERSATION(), usuarioLogeado.getUIDUSER(), conversationUser.getUIDUSER(), messageEditText.getText().toString());
                firebaseSend(usuarioLogeado.getUIDUSER(), conversationUser.getUIDUSER(), messageEditText.getText().toString());
                UIDCONVERSATION = conversation.getUIDCONVERSATION();
            }

            messageEditText.setText("");
            updateMessagesList();
        }
    }

    private void firebaseSend(int UIDUSERFROM, int UIDUSERTO, String DATA) {
        //TODO reemplazar por un servicio de un servidor propio
        StringBuilder json = new StringBuilder("{\"to\":\"/topics/NOTIFICACIONES\",\"data\":{\"TYPEUSER\":\"PARENTUSER\",\"NOTIFICATIONTYPE\":\"MESSAGENOTIFICATION\",\"HOMEWORKCONTENT\":{\"TITLE\":\"INVESTIGACION\",\"DESCRIPTION\":\"Aquí estará todo el contenido de la tarea\",\"DELIVERDATE\":\"4/10/2018\",\"PUBLISHDATE\":\"4/10/2018 03:23:40\"},\"ACTIVITYCONTENT\":{\"TITLE\":\"\",\"DESCRIPTION\":\"\",\"URL\":\"\",\"DELIVERDATE\":\"\",\"PUBLISHDATE\":\"\"},\"NOTESCONTENT\":{\"NOTE\":\"\"},\"MESSAGECONTENT\":{\"DATA\":\"" + DATA + "\",\"UIDUSERFROM\":\"" + UIDUSERFROM + "\",\"UIDUSERTO\":\"" + UIDUSERTO + "\",\"SENDDATE\":\"" + Util.Dates.getDateTime() + "\"}}}");

        try {
            JSONObject jsonArray = new JSONObject(String.valueOf(json));
            firebaseConsoleWS firebaseConsoleWS = new firebaseConsoleWS(jsonArray);
            firebaseConsoleWS.execute();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertMessage(int UIDCONVERSATION, int UIDUSERFROM, int UIDUSERTO, String DATA) {
        MessagesDAO messagesDAO = new MessagesDAO(this);
        messagesDAO.insertMessage(UIDCONVERSATION, UIDUSERFROM, UIDUSERTO, DATA, Util.Dates.getDateTime());
    }

    private void updateMessagesList() {
        mMessagesDataset.clear();
        mMessagesDataset.addAll(loadMessagesList());
        mAdapter.notifyDataSetChanged();
        scrollMyListViewToBottom();
    }

    private class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            updateMessagesList();
        }
    }

}
