package com.example.project_work;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.core.app.NotificationCompat;

public class WaterReminderService extends Service {
    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create notification
        NotificationChannel channel = new NotificationChannel(
                "water_reminder",
                "Water Reminder",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(this, "water_reminder")
                .setContentTitle("Time to drink water!")
                .setContentText("Drink 250 ml to stay hydrated.")
                .setSmallIcon(R.drawable.ic_water)
                .build();

        startForeground(NOTIFICATION_ID, notification);

        // Schedule reminders
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            manager.notify(NOTIFICATION_ID + 1, notification);
        }, 2 * 60 * 60 * 1000); // Every 2 hours

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}