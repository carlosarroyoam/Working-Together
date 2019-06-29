package com.workingtogether.workingtogether.firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.workingtogether.workingtogether.entity.Conversation;
import com.workingtogether.workingtogether.entity.SessionApp;
import com.workingtogether.workingtogether.entity.User;
import com.workingtogether.workingtogether.entity.dao.ActivityDAO;
import com.workingtogether.workingtogether.entity.dao.ConversationsDAO;
import com.workingtogether.workingtogether.entity.dao.HomeworksDAO;
import com.workingtogether.workingtogether.entity.dao.MessagesDAO;
import com.workingtogether.workingtogether.entity.dao.NotificationsDAO;
import com.workingtogether.workingtogether.entity.dao.SessionDAO;
import com.workingtogether.workingtogether.entity.dao.UserDAO;
import com.workingtogether.workingtogether.util.GlobalParams;
import com.workingtogether.workingtogether.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import static com.workingtogether.workingtogether.firebase.NotificationsBuilder.buildNotification;

public class FirebaseMessagingServiceImplementation extends FirebaseMessagingService {

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
                if (!remoteMessage.getData().get(NotificationsBuilder.NOTIFICATION_TYPE).trim().equals("")) {

                    if (remoteMessage.getData().get(NotificationsBuilder.NOTIFICATION_TYPE).equals(NotificationsBuilder.HOMEWORKNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("HOMEWORKCONTENT"));
                            sendHomeworkToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (remoteMessage.getData().get(NotificationsBuilder.NOTIFICATION_TYPE).equals(NotificationsBuilder.ACTIVITYNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("ACTIVITYCONTENT"));
                            sendActivityToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (remoteMessage.getData().get(NotificationsBuilder.NOTIFICATION_TYPE).equals(NotificationsBuilder.NOTESNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("NOTESCONTENT"));
                            sendNoteToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    Log.d("NBuilder Error: ", "No notification type specified");
                }
            }

            if (remoteMessage.getData().get(NotificationsBuilder.NOTIFICATION_TYPE).equals(NotificationsBuilder.MESSAGENOTIFICATION)) {
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
        notificationsDB.insertNotification(TITLE, DESCRIPTION, Util.Dates.getDateTime(), NOTIFICATIONTYPE, UIDRESOURSE);
    }

    private void sendHomeworkToDatabase(JSONObject json) throws JSONException {
        String title, description, deliverDate, publishDate;
        title = json.getString(GlobalParams.TITLE);
        description = json.getString(GlobalParams.DESCRIPTION);
        deliverDate = json.getString(GlobalParams.DELIVERDATE);
        publishDate = json.getString(GlobalParams.PUBLISHDATE);

        HomeworksDAO homeworksDB = new HomeworksDAO(this);
        homeworksDB.insertHomework(title, description, deliverDate, publishDate);

        sendNotificationToDatabase("Diana López publico una nueva tarea", title, NotificationsBuilder.HOMEWORKNOTIFICATION, 1);
        buildNotification(this, title, description, NotificationsBuilder.HOMEWORKNOTIFICATION);
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

        sendNotificationToDatabase("Diana López publico una nueva actividad", title, NotificationsBuilder.ACTIVITYNOTIFICATION, 1);
        buildNotification(this, title, description, NotificationsBuilder.ACTIVITYNOTIFICATION);
        sendNewActivityBroadcastReceiver();
    }

    private void sendNewActivityBroadcastReceiver(){
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".newActivity");
        sendBroadcast(intent);
    }

    private void sendNoteToDatabase(JSONObject json) {
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

            sendNotificationToDatabase("Tienes un nuevo mensaje de " + userFrom.getNAME() + " " + userFrom.getLASTNAME(), data, NotificationsBuilder.MESSAGENOTIFICATION, 1);
            buildNotification(this, "Tienes un nuevo mensaje de " + userFrom.getNAME() + " " + userFrom.getLASTNAME(), data, NotificationsBuilder.MESSAGENOTIFICATION);
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
