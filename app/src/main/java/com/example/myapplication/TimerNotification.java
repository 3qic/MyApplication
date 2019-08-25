package com.example.myapplication;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
//https://www.youtube.com/watch?v=j6kQ9gikU-A
//https://stackoverflow.com/questions/18425108/how-should-i-do-from-notification-back-to-activity-without-new-intent/18426787

public class TimerNotification extends ContextWrapper{



    public static final String channelID = "channelID";
    public static final String channelName = "channelName";
    private NotificationManager notificationManager;

    public TimerNotification(Context base) {
        super(base);
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
            createNotificationChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.setLightColor(R.color.defaultOrange);
        channel.enableVibration(true);
        getNotificationManager().createNotificationChannel(channel);

    }

    public NotificationManager getNotificationManager() {
        if(notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        }

        return notificationManager;
    }


    //https://developer.android.com/reference/android/app/Notification.Builder
    public NotificationCompat.Builder getChannelNotification(String title, Intent i) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP ),PendingIntent.FLAG_ONE_SHOT| PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(),channelID)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.baseline_alarm_on_black_18dp)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

    }
}
