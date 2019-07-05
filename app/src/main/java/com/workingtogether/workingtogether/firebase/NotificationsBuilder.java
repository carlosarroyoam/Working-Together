package com.workingtogether.workingtogether.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.workingtogether.workingtogether.R;
import com.workingtogether.workingtogether.activities.ActivitiesActivity;
import com.workingtogether.workingtogether.activities.ConversationsActivity;
import com.workingtogether.workingtogether.activities.DashboardActivity;
import com.workingtogether.workingtogether.activities.HomeworksActivity;
import com.workingtogether.workingtogether.activities.parent.ParentNotes;

import java.util.Random;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class NotificationsBuilder {

    // Notifications types
    public static final String NOTIFICATION_TYPE = "notification_type";
    public static final String HOMEWORK_NOTIFICATION = "homework_notification";
    public static final String ACTIVITY_NOTIFICATION = "activity_notification";
    public static final String NOTES_NOTIFICATION = "notes_notification";
    public static final String MESSAGE_NOTIFICATION = "message_notification";

    // Channel ids
    public static final String HOMEWORK_NOTIFICATION_CHANNEL = "homework_notification_channel";
    public static final String ACTIVITY_NOTIFICATION_CHANNEL = "activity_notification_channel";
    public static final String NOTES_NOTIFICATION_CHANNEL = "notes_notification_channel";
    public static final String MESSAGE_NOTIFICATION_CHANNEL = "message_notification_channel";
    public static final String DEFAULT_NOTIFICATION_CHANNEL = "default_notification_channel";

    public static void buildNotification(Context context, String title, String body, String notificationType) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, setIntentType(context, notificationType), PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, getChannerId(notificationType))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setColor(ContextCompat.getColor(context, R.color.orange))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(generateNotificationId(), notificationBuilder.build());
    }

    private static String getChannerId(String notificationType) {
        if (notificationType.equals(HOMEWORK_NOTIFICATION)) {
            return HOMEWORK_NOTIFICATION_CHANNEL;

        } else if (notificationType.equals(ACTIVITY_NOTIFICATION)) {
            return ACTIVITY_NOTIFICATION_CHANNEL;

        } else if (notificationType.equals(NOTES_NOTIFICATION)) {
            return NOTES_NOTIFICATION_CHANNEL;

        } else if (notificationType.equals(MESSAGE_NOTIFICATION)) {
            return MESSAGE_NOTIFICATION_CHANNEL;

        }

        return DEFAULT_NOTIFICATION_CHANNEL;
    }

    static int generateNotificationId() {
        return (100 + new Random().nextInt(500 - 100)) + 1;
    }

    private static Intent setIntentType(Context context, String notificationType) {
        Intent intent;

        if (notificationType.equals(HOMEWORK_NOTIFICATION)) {
            intent = new Intent(context, HomeworksActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        } else if (notificationType.equals(ACTIVITY_NOTIFICATION)) {
            intent = new Intent(context, ActivitiesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        } else if (notificationType.equals(NOTES_NOTIFICATION)) {
            intent = new Intent(context, ParentNotes.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        } else if (notificationType.equals(MESSAGE_NOTIFICATION)) {
            intent = new Intent(context, ConversationsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return intent;

        }

        return new Intent(context, DashboardActivity.class);
    }

    public static void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel homeworksNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    context.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            homeworksNotificationChannel.setDescription(context.getResources().getString(R.string.homeworks_channel_description));

            NotificationChannel activitiesNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    context.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            activitiesNotificationChannel.setDescription(context.getResources().getString(R.string.homeworks_channel_description));

            NotificationChannel notesNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    context.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notesNotificationChannel.setDescription(context.getResources().getString(R.string.homeworks_channel_description));

            NotificationChannel messagesNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    context.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            messagesNotificationChannel.setDescription(context.getResources().getString(R.string.homeworks_channel_description));

            NotificationChannel defaultNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    context.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            defaultNotificationChannel.setDescription(context.getResources().getString(R.string.homeworks_channel_description));

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(homeworksNotificationChannel);
            notificationManager.createNotificationChannel(activitiesNotificationChannel);
            notificationManager.createNotificationChannel(notesNotificationChannel);
            notificationManager.createNotificationChannel(messagesNotificationChannel);
            notificationManager.createNotificationChannel(defaultNotificationChannel);
        }
    }
}
