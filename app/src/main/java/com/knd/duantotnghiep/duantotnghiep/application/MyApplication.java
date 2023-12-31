package com.knd.duantotnghiep.duantotnghiep.application;

import android.app.Application;

import com.knd.duantotnghiep.duantotnghiep.utils.MyNotificationManager;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyNotificationManager.createNotificationChannel(this);
    }
}
