package com.workingtogether.workingtogether.firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.workingtogether.workingtogether.models.Conversation;
import com.workingtogether.workingtogether.models.SessionApp;
import com.workingtogether.workingtogether.models.User;
import com.workingtogether.workingtogether.models.dao.ActivityDAO;
import com.workingtogether.workingtogether.models.dao.ConversationsDAO;
import com.workingtogether.workingtogether.models.dao.HomeworksDAO;
import com.workingtogether.workingtogether.models.dao.MessagesDAO;
import com.workingtogether.workingtogether.models.dao.NotificationsDAO;
import com.workingtogether.workingtogether.models.dao.SessionDAO;
import com.workingtogether.workingtogether.models.dao.UserDAO;
import com.workingtogether.workingtogether.util.GlobalParams;
import com.workingtogether.workingtogether.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import static com.workingtogether.workingtogether.firebase.Notification.buildNotification;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onNewToken(String newToken) {
        super.onNewToken(newToken);
        sendRegistrationToServer(newToken);
        Log.d("newToken: ", newToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("From: ", remoteMessage.getFrom());
        SessionDAO sessionDAO = new SessionDAO(this);


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("Message data payload: ", remoteMessage.getData().toString());

            if (sessionDAO.getUserlogged().getTYPEUSER().equals(User.UserTypes.PARENTUSER)) {
                if (!remoteMessage.getData().get(Notification.NOTIFICATIONTYPE).trim().equals("")) {

                    if (remoteMessage.getData().get(Notification.NOTIFICATIONTYPE).equals(Notification.HOMEWORKNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("HOMEWORKCONTENT"));
                            sendHomeworkToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (remoteMessage.getData().get(Notification.NOTIFICATIONTYPE).equals(Notification.ACTIVITYNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("ACTIVITYCONTENT"));
                            sendActivityToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (remoteMessage.getData().get(Notification.NOTIFICATIONTYPE).equals(Notification.NOTESNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("NOTESCONTENT"));
                            sendNoteToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    Log.d("Notification Error: ", "No notification type specified");
                }
            }

            if (remoteMessage.getData().get(Notification.NOTIFICATIONTYPE).equals(Notification.MESSAGENOTIFICATION)) {
                try {
                    JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("MESSAGECONTENT"));
                    sendMessageToDatabase(dataPayload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void sendNotificationToDatabase(String TITLE, String DESCRIPTION, String NOTIFICATIONTYPE, int UIDRESOURSE) {
        NotificationsDAO notificationsDB = new NotificationsDAO(this);
        notificationsDB.insertNotification(TITLE, DESCRIPTION, Util.Date.getDateTime(), NOTIFICATIONTYPE, UIDRESOURSE);
    }

    private void sendHomeworkToDatabase(JSONObject json) throws JSONException {
        String title, description, deliverDate, publishDate;
        title = json.getString(GlobalParams.TITLE);
        description = json.getString(GlobalParams.DESCRIPTION);
        deliverDate = json.getString(GlobalParams.DELIVERDATE);
        publishDate = json.getString(GlobalParams.PUBLISHDATE);

        HomeworksDAO homeworksDB = new HomeworksDAO(this);
        homeworksDB.insertHomework(title, description, deliverDate, publishDate);

        sendNotificationToDatabase("Diana López publico una nueva tarea", title, Notification.HOMEWORKNOTIFICATION, 1);
        buildNotification(this, title, description, Notification.HOMEWORKNOTIFICATION);
        sendNewHomeworkBroadcastReceiver();
    }

    private void sendNewHomeworkBroadcastReceiver(){
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".newHomework");
        sendBroadcast(intent);
    }

    private void sendActivityToDatabase(JSONObject json) throws JSONException {
        String title, description, deliverDate, url, publishDate;
        title = json.getString(GlobalParams.TITLE);
        description = json.getString(GlobalParams.DESCRIPTION);
        url = json.getString(GlobalParams.DESCRIPTION);
        deliverDate = json.getString(GlobalParams.DELIVERDATE);
        publishDate = json.getString(GlobalParams.PUBLISHDATE);

        ActivityDAO activityDAO = new ActivityDAO(this);
        activityDAO.insertActivity(title, description, url, deliverDate, publishDate);

        sendNotificationToDatabase("Diana López publico una nueva actividad", title, Notification.ACTIVITYNOTIFICATION, 1);
        buildNotification(this, title, description, Notification.ACTIVITYNOTIFICATION);
        sendNewActivityBroadcastReceiver();
    }

    private void sendNewActivityBroadcastReceiver(){
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".newActivity");
        sendBroadcast(intent);
    }

    private void sendNoteToDatabase(JSONObject json) throws JSONException {

        sendNewNoteBroadcastReceiver();
    }

    private void sendNewNoteBroadcastReceiver(){
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".newNote");
        sendBroadcast(intent);
    }

    private void sendMessageToDatabase(JSONObject json) throws JSONException {
        int UIDUSERFROM = Integer.parseInt(json.getString("UIDUSERFROM"));
        int UIDUSERTO = Integer.parseInt(json.getString("UIDUSERTO"));
        String data = json.getString("DATA");
        String date = json.getString("SENDDATE");

        SessionDAO sessionDAO = new SessionDAO(this);
        SessionApp sessionApp = sessionDAO.getUserlogged();
        if (sessionApp.getUIDUSER() != UIDUSERFROM) {
            ConversationsDAO conversationsDAO = new ConversationsDAO(this);
            MessagesDAO messagesDAO = new MessagesDAO(this);
            Conversation conversation = conversationsDAO.getConversationByContactId(UIDUSERFROM);
            if (conversation.getUIDCONVERSATION() > 0) {
                messagesDAO.insertMessage(conversation.getUIDCONVERSATION(), UIDUSERFROM, UIDUSERTO, data, date);
            } else {
                conversationsDAO.insertConversation(UIDUSERFROM);
                Conversation conversation1 = conversationsDAO.getConversationByContactId(UIDUSERFROM);
                messagesDAO.insertMessage(conversation1.getUIDCONVERSATION(), UIDUSERFROM, UIDUSERTO, data, date);
            }

            UserDAO userDAO = new UserDAO(this);
            User userFrom = userDAO.getUserDetails(UIDUSERFROM);

            sendNotificationToDatabase("Tienes un nuevo mensaje de " + userFrom.getNAME() + " " + userFrom.getLASTNAME(), data, Notification.MESSAGENOTIFICATION, 1);
            buildNotification(this, "Tienes un nuevo mensaje de " + userFrom.getNAME() + " " + userFrom.getLASTNAME(), data, Notification.MESSAGENOTIFICATION);
            sendNewMessageBroadcastReceiver();
        }
    }

    private void sendNewMessageBroadcastReceiver(){
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".newMessage");
        sendBroadcast(intent);
    }

    private void sendRegistrationToServer(String newToken) {

    }

}
