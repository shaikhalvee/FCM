package com.my.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by eovsiy on 5/11/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = MessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);

        Log.d(TAG, "FCM Message Id: " + message.getMessageId());
        Log.d(TAG, "FCM Notification Message: " +
                message.getNotification());
        Log.d(TAG, "FCM Data Message: " + message.getData());

        if (isMessageContainsDataPayload(message)) {

            if (isLongRunningJob(message)) {
                scheduleJob(message);
            } else {
                handleNow(message);
            }
        }

        if (isMessageContainsNotificationPayload(message)) {
            Log.d(TAG, "Icon: " + message.getNotification().getIcon());
            Log.d(TAG, "Title: " + message.getNotification().getTitle());
            Log.d(TAG, "Body: " + message.getNotification().getBody());
        }
    }

    private boolean isMessageContainsNotificationPayload(RemoteMessage remoteMessage) {
        return remoteMessage.getNotification() != null;
    }

    private boolean isMessageContainsDataPayload(RemoteMessage remoteMessage) {
        return remoteMessage.getData().size() > 0;
    }

    private boolean isLongRunningJob(RemoteMessage remoteMessage) {
        return false;
    }

    private void scheduleJob(RemoteMessage remoteMessage) {
        // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
        // see https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/MyFirebaseMessagingService.java
    }

    private void handleNow(RemoteMessage remoteMessage) {
        // Handle message within 10 seconds
        sendNotification(remoteMessage.getData().get("title"));
    }

    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_directions_run_white_24dp)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
