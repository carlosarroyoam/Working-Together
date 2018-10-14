package com.workingtogether.workingtogether.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.workingtogether.workingtogether.Conversations;
import com.workingtogether.workingtogether.Dashboard;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.db.ActivityDB;
import com.workingtogether.workingtogether.db.ConversationsDB;
import com.workingtogether.workingtogether.db.HomeworksDB;
import com.workingtogether.workingtogether.db.MessagesDB;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.Activities;
import com.workingtogether.workingtogether.Homeworks;
import com.workingtogether.workingtogether.db.UserDB;
import com.workingtogether.workingtogether.obj.Conversation;
import com.workingtogether.workingtogether.obj.SessionApp;
import com.workingtogether.workingtogether.obj.User;
import com.workingtogether.workingtogether.util.DateUtils;
import com.workingtogether.workingtogether.util.LocalParams;

import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("From: ", remoteMessage.getFrom());
        SessionDB sessionDB = new SessionDB(this);


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("Message data payload: ", remoteMessage.getData().toString());

            if (sessionDB.getUserlogged().getTYPEUSER().equals(LocalParams.PARENTUSER)) {
                if (!remoteMessage.getData().get(LocalParams.NOTIFICATIONTYPE).trim().equals("")) {

                    if (remoteMessage.getData().get(LocalParams.NOTIFICATIONTYPE).equals(LocalParams.HOMEWORKNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("HOMEWORKCONTENT"));
                            sendHomeworkToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (remoteMessage.getData().get(LocalParams.NOTIFICATIONTYPE).equals(LocalParams.ACTIVITYNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("ACTIVITYCONTENT"));
                            sendActivityToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (remoteMessage.getData().get(LocalParams.NOTIFICATIONTYPE).equals(LocalParams.NOTESNOTIFICATION)) {
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

            if (remoteMessage.getData().get(LocalParams.NOTIFICATIONTYPE).equals(LocalParams.MESSAGENOTIFICATION)) {
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
        NotificationsDB notificationsDB = new NotificationsDB(this);
        notificationsDB.insertNotification(TITLE, DESCRIPTION, DateUtils.getDateTime(), NOTIFICATIONTYPE, UIDRESOURSE);
    }

    private void sendHomeworkToDatabase(JSONObject json) throws JSONException {
        String title, description, deliverDate, publishDate;
        title = json.getString(LocalParams.TITLE);
        description = json.getString(LocalParams.DESCRIPTION);
        deliverDate = json.getString(LocalParams.DELIVERDATE);
        publishDate = json.getString(LocalParams.PUBLISHDATE);

        HomeworksDB homeworksDB = new HomeworksDB(this);
        homeworksDB.insertHomework(title, description, deliverDate, publishDate);

        sendNotificationToDatabase("Diana López publico una nueva tarea", title, LocalParams.HOMEWORKNOTIFICATION, 1);
        mostrarNotificacion(title, description, LocalParams.HOMEWORKNOTIFICATION);
        sendNewHomeworkBroadcastReceiver();
    }

    private void sendNewHomeworkBroadcastReceiver(){
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".newHomework");
        sendBroadcast(intent);
    }

    private void sendActivityToDatabase(JSONObject json) throws JSONException {
        String title, description, deliverDate, url, publishDate;
        title = json.getString(LocalParams.TITLE);
        description = json.getString(LocalParams.DESCRIPTION);
        url = json.getString(LocalParams.DESCRIPTION);
        deliverDate = json.getString(LocalParams.DELIVERDATE);
        publishDate = json.getString(LocalParams.PUBLISHDATE);

        ActivityDB activityDB = new ActivityDB(this);
        activityDB.insertActivity(title, description, url, deliverDate, publishDate);

        sendNotificationToDatabase("Diana López publico una nueva actividad", title, LocalParams.ACTIVITYNOTIFICATION, 1);
        mostrarNotificacion(title, description, LocalParams.ACTIVITYNOTIFICATION);
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

        SessionDB sessionDB = new SessionDB(this);
        SessionApp sessionApp = sessionDB.getUserlogged();
        if (sessionApp.getUIDUSER() != UIDUSERFROM) {
            ConversationsDB conversationsDB = new ConversationsDB(this);
            MessagesDB messagesDB = new MessagesDB(this);
            Conversation conversation = conversationsDB.getConversationByContactId(UIDUSERFROM);
            if (conversation.getUIDCONVERSATION() > 0) {
                messagesDB.insertMessage(conversation.getUIDCONVERSATION(), UIDUSERFROM, UIDUSERTO, data, date);
            } else {
                conversationsDB.insertConversation(UIDUSERFROM);
                Conversation conversation1 = conversationsDB.getConversationByContactId(UIDUSERFROM);
                messagesDB.insertMessage(conversation1.getUIDCONVERSATION(), UIDUSERFROM, UIDUSERTO, data, date);
            }

            UserDB userDB = new UserDB(this);
            User userFrom = userDB.getUserDetails(UIDUSERFROM);

            sendNotificationToDatabase("Tienes un nuevo mensaje de " + userFrom.getNAME() + " " + userFrom.getLASTNAME(), data, LocalParams.MESSAGENOTIFICATION, 1);
            mostrarNotificacion("Tienes un nuevo mensaje de " + userFrom.getNAME() + " " + userFrom.getLASTNAME(), data, LocalParams.MESSAGENOTIFICATION);
            sendNewMessageBroadcastReceiver();
        }
    }

    private void sendNewMessageBroadcastReceiver(){
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".newMessage");
        sendBroadcast(intent);
    }

    private void mostrarNotificacion(String title, String body, String notificationType) {
        Intent intent = setIntentType(notificationType);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.orange))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

    private Intent setIntentType(String notificationType) {
        Intent intent;
        if (notificationType.equals(LocalParams.HOMEWORKNOTIFICATION)) {
            intent = new Intent(this, Homeworks.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        } else if (notificationType.equals(LocalParams.ACTIVITYNOTIFICATION)) {
            intent = new Intent(this, Activities.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        } else if (notificationType.equals(LocalParams.MESSAGENOTIFICATION)) {
            intent = new Intent(this, Conversations.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        }

        return new Intent(this, Dashboard.class);
    }

}
