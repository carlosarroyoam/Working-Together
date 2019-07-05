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
    static final String NOTIFICATION_TYPE = "notification_type";
    public static final String HOMEWORK_NOTIFICATION = "homework_notification";
    public static final String ACTIVITY_NOTIFICATION = "activity_notification";
    public static final String NOTES_NOTIFICATION = "notes_notification";
    public static final String MESSAGE_NOTIFICATION = "message_notification";

    // Channel ids
    private static final String HOMEWORK_NOTIFICATION_CHANNEL = "homework_notification_channel";
    private static final String ACTIVITY_NOTIFICATION_CHANNEL = "activity_notification_channel";
    private static final String NOTES_NOTIFICATION_CHANNEL = "notes_notification_channel";
    private static final String MESSAGE_NOTIFICATION_CHANNEL = "message_notification_channel";
    private static final String DEFAULT_NOTIFICATION_CHANNEL = "default_notification_channel";

    static void buildNotification(Context context, String title, String body, String notificationType) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, getIntentType(context, notificationType), PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context.getApplicationContext(), getChannelId(notificationType))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setColor(ContextCompat.getColor(context.getApplicationContext(), R.color.orange))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());
        notificationManager.notify(generateNotificationId(), notificationBuilder.build());
    }

    private static int generateNotificationId() {
        return (100 + new Random().nextInt(500 - 100)) + 1;
    }

    private static String getChannelId(String notificationType) {
        switch (notificationType) {
            case HOMEWORK_NOTIFICATION:
                return HOMEWORK_NOTIFICATION_CHANNEL;

            case ACTIVITY_NOTIFICATION:
                return ACTIVITY_NOTIFICATION_CHANNEL;

            case NOTES_NOTIFICATION:
                return NOTES_NOTIFICATION_CHANNEL;

            case MESSAGE_NOTIFICATION:
                return MESSAGE_NOTIFICATION_CHANNEL;
        }

        return DEFAULT_NOTIFICATION_CHANNEL;
    }

    private static Intent getIntentType(Context context, String notificationType) {
        Context applicationContext = context.getApplicationContext();

        switch (notificationType) {
            case HOMEWORK_NOTIFICATION:
                return new Intent(applicationContext, HomeworksActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            case ACTIVITY_NOTIFICATION:
                return new Intent(applicationContext, ActivitiesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            case NOTES_NOTIFICATION:
                return new Intent(applicationContext, ParentNotes.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            case MESSAGE_NOTIFICATION:
                return new Intent(applicationContext, ConversationsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        return new Intent(applicationContext, DashboardActivity.class);
    }

    public static void createNotificationChannels(Context context) {
        Context applicationContext = context.getApplicationContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel homeworksNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    applicationContext.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            homeworksNotificationChannel.setDescription(applicationContext.getResources().getString(R.string.homeworks_channel_description));

            NotificationChannel activitiesNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    applicationContext.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            activitiesNotificationChannel.setDescription(applicationContext.getResources().getString(R.string.homeworks_channel_description));

            NotificationChannel notesNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    applicationContext.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notesNotificationChannel.setDescription(applicationContext.getResources().getString(R.string.homeworks_channel_description));

            NotificationChannel messagesNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    applicationContext.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            messagesNotificationChannel.setDescription(applicationContext.getResources().getString(R.string.homeworks_channel_description));

            NotificationChannel defaultNotificationChannel = new NotificationChannel(HOMEWORK_NOTIFICATION_CHANNEL,
                    applicationContext.getResources().getString(R.string.homeworks_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            defaultNotificationChannel.setDescription(applicationContext.getResources().getString(R.string.homeworks_channel_description));

            NotificationManager notificationManager = applicationContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(homeworksNotificationChannel);
            notificationManager.createNotificationChannel(activitiesNotificationChannel);
            notificationManager.createNotificationChannel(notesNotificationChannel);
            notificationManager.createNotificationChannel(messagesNotificationChannel);
            notificationManager.createNotificationChannel(defaultNotificationChannel);
        }
    }
}
