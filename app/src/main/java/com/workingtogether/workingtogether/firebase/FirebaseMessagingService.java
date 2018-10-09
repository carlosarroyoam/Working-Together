package com.workingtogether.workingtogether.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.workingtogether.workingtogether.Dashboard;
import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.db.ActivityDB;
import com.workingtogether.workingtogether.db.HomeworksDB;
import com.workingtogether.workingtogether.db.NotificationsDB;
import com.workingtogether.workingtogether.db.SessionDB;
import com.workingtogether.workingtogether.db.UserDB;
import com.workingtogether.workingtogether.parent.ParentActivities;
import com.workingtogether.workingtogether.parent.ParentHomeworks;
import com.workingtogether.workingtogether.util.DateUtils;
import com.workingtogether.workingtogether.util.LocalParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("From: ", remoteMessage.getFrom());
        SessionDB sessionDB = new SessionDB(this);

        if (sessionDB.getUserlogged().getTYPEUSER().equals(LocalParams.PARENTUSER)) {
            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d("Message data payload: ", remoteMessage.getData().toString());

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
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("HOMEWORKCONTENT"));
                            sendActivityToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (remoteMessage.getData().get(LocalParams.NOTIFICATIONTYPE).equals(LocalParams.NOTESNOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("HOMEWORKCONTENT"));
                            sendNoteToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (remoteMessage.getData().get(LocalParams.NOTIFICATIONTYPE).equals(LocalParams.MESSAGENOTIFICATION)) {
                        try {
                            JSONObject dataPayload = new JSONObject(remoteMessage.getData().get("HOMEWORKCONTENT"));
                            sendNoteToDatabase(dataPayload);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    Log.d("Notification Error: ", "No notification type specified");
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
    }

    private void sendNoteToDatabase(JSONObject json) throws JSONException {

    }

    private void sendMessageToDatabase(JSONObject json) throws JSONException {

    }

    private void mostrarNotificacion(String title, String body, String notificationType) {
        Intent intent = setIntentType(notificationType);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

    private Intent setIntentType(String notificationType){
        Intent intent;
        if (notificationType.equals(LocalParams.HOMEWORKNOTIFICATION)) {
            intent = new Intent(this, ParentHomeworks.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        } else if (notificationType.equals(LocalParams.ACTIVITYNOTIFICATION)) {
            intent = new Intent(this, ParentActivities.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        }

        return new Intent(this, Dashboard.class);
    }

}
