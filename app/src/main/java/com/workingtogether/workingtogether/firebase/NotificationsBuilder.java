package com.workingtogether.workingtogether.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.activities.ActivitiesActivity;
import com.workingtogether.workingtogether.activities.ConversationsActivity;
import com.workingtogether.workingtogether.activities.DashboardActivity;
import com.workingtogether.workingtogether.activities.HomeworksActivity;

public class NotificationsBuilder {

    //NOTIFICATION TYPE
    public static String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
    public static String HOMEWORKNOTIFICATION = "HOMEWORKNOTIFICATION";
    public static String ACTIVITYNOTIFICATION = "ACTIVITYNOTIFICATION";
    public static String NOTESNOTIFICATION = "NOTESNOTIFICATION";
    public static String MESSAGENOTIFICATION = "MESSAGENOTIFICATION";

    public static void buildNotification(Context context, String title, String body, String notificationType) {
        Intent intent = setIntentType(context, notificationType);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.orange))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

    private static Intent setIntentType(Context context, String notificationType) {
        Intent intent;
        if (notificationType.equals(HOMEWORKNOTIFICATION)) {
            intent = new Intent(context, HomeworksActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        } else if (notificationType.equals(ACTIVITYNOTIFICATION)) {
            intent = new Intent(context, ActivitiesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        } else if (notificationType.equals(MESSAGENOTIFICATION)) {
            intent = new Intent(context, ConversationsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        }

        return new Intent(context, DashboardActivity.class);
    }

}
