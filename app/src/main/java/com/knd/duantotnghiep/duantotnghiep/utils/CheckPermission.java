package com.knd.duantotnghiep.duantotnghiep.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

public class CheckPermission {
    public static final String ACCESS_COARSE_LOCATION= Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String ACCESS_FINE_LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_CAMERA= Manifest.permission.CAMERA;
    public static final String POST_NOTIFICATIONS= Manifest.permission.POST_NOTIFICATIONS;

    public static Boolean isPermissionCamera(Context context){
        return context.checkSelfPermission(ACCESS_CAMERA) == PackageManager.PERMISSION_GRANTED  ;
    }
    public static Boolean isPermissionPostNotification(Context context){
        return context.checkSelfPermission(POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED  ;
    }
    public static Boolean isPermissionLocation(Context context) {
        return context.checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                context.checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
