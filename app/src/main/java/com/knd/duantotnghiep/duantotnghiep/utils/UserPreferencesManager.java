package com.knd.duantotnghiep.duantotnghiep.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.knd.duantotnghiep.duantotnghiep.models.User;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class UserPreferencesManager {
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    @Inject
    public UserPreferencesManager(@ApplicationContext Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.USER_PREFERENCES, AppCompatActivity.MODE_PRIVATE);
    }

    public void saveUser(User user) {
        sharedPreferences.edit().putString(Constants.CURRENT_USER, gson.toJson(user)).apply();
    }

    public void removeCurrentUser() {
        sharedPreferences.edit().putString(Constants.CURRENT_USER, null).apply();
    }

    public User getCurrentUser() {
        try {
            String jsonObj = sharedPreferences.getString(Constants.CURRENT_USER, null);
            if (jsonObj != null) {
                return gson.fromJson(jsonObj, User.class);
            }
        } catch (Exception e) {
            Log.e("UserPreferencesManager", "Error retrieving user", e);
        }
        return null;
    }
}
