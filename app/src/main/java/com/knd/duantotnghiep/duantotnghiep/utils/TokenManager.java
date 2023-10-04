package com.knd.duantotnghiep.duantotnghiep.utils;

;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.qualifiers.ApplicationContext;

import javax.inject.Inject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.qualifiers.ApplicationContext;

import javax.inject.Inject;

public class TokenManager {
    private SharedPreferences sharedPreferences;

    @Inject
    public TokenManager(@ApplicationContext Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREFS_TOKEN_FILE, AppCompatActivity.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        Log.d("dddddddddđ", token);
        sharedPreferences.edit().putString(Constants.USER_TOKEN, token).apply();
    }

    public void removeToken() {
        sharedPreferences.edit().putString(Constants.USER_TOKEN, null).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(Constants.USER_TOKEN, null);
    }
}

