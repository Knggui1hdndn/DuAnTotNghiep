package com.knd.duantotnghiep.duantotnghiep.broadcast;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.knd.duantotnghiep.duantotnghiep.models.Notification;
import com.knd.duantotnghiep.duantotnghiep.utils.MyNotificationManager;
import java.util.Map;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Map<String, String> stringStringMap = message.getData();
        Log.d("ịaaofjofnasjfn",message.toString());
        MyNotificationManager.createNotification(getApplicationContext(), new Notification(stringStringMap.get("url"), stringStringMap.get("title"), stringStringMap.get("body")));
    }
}
