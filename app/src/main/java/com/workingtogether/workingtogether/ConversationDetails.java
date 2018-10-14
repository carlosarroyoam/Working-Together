package com.workingtogether.workingtogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.workingtogether.workingtogether.adapter.ConversationDetailListAdapter;
import com.workingtogether.workingtogether.adapter.NotificationsDrawerAdapter;
import com.workingtogether.workingtogether.db.ConversationsDB;
import com.workingtogether.workingtogether.db.MessagesDB;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.db.UserDB;
import com.workingtogether.workingtogether.obj.Conversation;
import com.workingtogether.workingtogether.obj.Message;
import com.workingtogether.workingtogether.obj.Notification;
import com.workingtogether.workingtogether.obj.SessionApp;
import com.workingtogether.workingtogether.obj.User;
import com.workingtogether.workingtogether.util.DateUtils;
import com.workingtogether.workingtogether.util.LocalParams;
import com.workingtogether.workingtogether.util.firebaseConsoleWS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConversationDetails extends AppCompatActivity {
    private ListView messages_listview;
    private ConversationDetailListAdapter mAdapter;
    private ArrayList<Message> mMessagesDataset;
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        messageEditText = findViewById(R.id.editText);

        User conversationUser = getUser();
        getSupportActionBar().setTitle(conversationUser.getNAME() + " " + conversationUser.getLASTNAME());

        mMessagesDataset = new ArrayList<>();
        mMessagesDataset.addAll(loadMessagesList());

        mAdapter = new ConversationDetailListAdapter(this, mMessagesDataset);
        messages_listview = findViewById(R.id.list_view_conversations);
        messages_listview.setAdapter(mAdapter);
    }

    private int getConversationId() {
        return getIntent().getIntExtra(LocalParams.UIDCONVERSATION, 0);
    }

    private User getUser() {
        UserDB userDB = new UserDB(this);
        return userDB.getUserDetails(getIntent().getIntExtra(LocalParams.UIDUSER, 0));
    }

    private ArrayList<Message> loadMessagesList() {
        MessagesDB messagesDB = new MessagesDB(this);
        ArrayList<Message> messageArrayList = messagesDB.getAllMessagesByConversationId(getConversationId());
        return messageArrayList;
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
        SessionDB sessionDB = new SessionDB(this);
        SessionApp usuarioLogeado = sessionDB.getUserlogged();
        User conversationUser = getUser();

        if (!messageEditText.getText().toString().trim().equals("")) {
            if (getConversationId() > 0) {
                insertMessage(getConversationId(), usuarioLogeado.getUIDUSER(), conversationUser.getUIDUSER(), messageEditText.getText().toString());
                firebaseSend(usuarioLogeado.getUIDUSER(), conversationUser.getUIDUSER(), messageEditText.getText().toString());
            } else {
                ConversationsDB conversationsDB = new ConversationsDB(this);
                conversationsDB.insertConversation(conversationUser.getUIDUSER());
                Conversation conversation = conversationsDB.getConversationByContactId(conversationUser.getUIDUSER());
                insertMessage(conversation.getUIDCONVERSATION(), usuarioLogeado.getUIDUSER(), conversationUser.getUIDUSER(), messageEditText.getText().toString());
                firebaseSend(usuarioLogeado.getUIDUSER(), conversationUser.getUIDUSER(), messageEditText.getText().toString());
            }

            messageEditText.setText("");
        }
    }

    private void firebaseSend(int UIDUSERFROM, int UIDUSERTO, String DATA) {
        //TODO reemplazar por un servicio de un servidor propio
        StringBuilder json = new StringBuilder("{\"to\":\"/topics/NOTIFICACIONES\",\"data\":{\"TYPEUSER\":\"PARENTUSER\",\"NOTIFICATIONTYPE\":\"MESSAGENOTIFICATION\",\"HOMEWORKCONTENT\":{\"TITLE\":\"INVESTIGACION\",\"DESCRIPTION\":\"Aquí estará todo el contenido de la tarea\",\"DELIVERDATE\":\"4/10/2018\",\"PUBLISHDATE\":\"4/10/2018 03:23:40\"},\"ACTIVITYCONTENT\":{\"TITLE\":\"\",\"DESCRIPTION\":\"\",\"URL\":\"\",\"DELIVERDATE\":\"\",\"PUBLISHDATE\":\"\"},\"NOTESCONTENT\":{\"NOTE\":\"\"},\"MESSAGECONTENT\":{\"DATA\":\"" + DATA + "\",\"UIDUSERFROM\":\"" + UIDUSERFROM + "\",\"UIDUSERTO\":\"" + UIDUSERTO + "\",\"SENDDATE\":\"" + DateUtils.getDateTime() + "\"}}}");

        try {
            JSONObject jsonArray = new JSONObject(String.valueOf(json));
            firebaseConsoleWS firebaseConsoleWS = new firebaseConsoleWS(jsonArray);
            firebaseConsoleWS.execute();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertMessage(int UIDCONVERSATION, int UIDUSERFROM, int UIDUSERTO, String DATA) {
        MessagesDB messagesDB = new MessagesDB(this);
        messagesDB.insertMessage(UIDCONVERSATION, UIDUSERFROM, UIDUSERTO, DATA, DateUtils.getDateTime());
        mAdapter.notifyDataSetChanged();
    }
}
