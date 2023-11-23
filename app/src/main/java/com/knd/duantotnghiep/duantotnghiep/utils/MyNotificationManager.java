package com.knd.duantotnghiep.duantotnghiep.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.ui.MainActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MyNotificationManager {
    public static void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("3002992121", "My Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void createNotification(Context context, com.knd.duantotnghiep.duantotnghiep.models.Notification myNotification) {
        Notification.Builder notification = new Notification.Builder(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(context, "3002992121");
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                PendingIntent.FLAG_CANCEL_CURRENT,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification.Builder finalNotification = notification.setContentTitle(myNotification.getTitle())
                .setContentText(myNotification.getBody())
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Load the image on the main thread
        Handler mainHandler = new Handler(context.getMainLooper());
        mainHandler.post(() -> {
            Picasso.get().load(myNotification.getUrl()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    finalNotification.setLargeIcon(bitmap);
                    notificationManager.notify((int) Long.parseLong(String.valueOf(System.currentTimeMillis())), finalNotification.build());
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    // Handle the case where loading the image failed
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    // Optional: handle preparation before loading the image
                }
            });
        });
    }
}
