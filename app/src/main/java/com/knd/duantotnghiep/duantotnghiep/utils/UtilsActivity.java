package com.knd.duantotnghiep.duantotnghiep.utils;

import android.content.Context;
import android.widget.Toast;


class UserActivity {
    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

