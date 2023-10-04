package com.knd.duantotnghiep.duantotnghiep.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.knd.duantotnghiep.duantotnghiep.utils.TokenManager;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;

public class AuthInterceptor implements Interceptor {
    @Inject
    public AuthInterceptor() {
    }

    @Inject
    TokenManager tokenManager;
    @Inject
    UserPreferencesManager userPreferencesManager;


    @NonNull
    @Override

    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        String token = tokenManager.getToken();
        request.addHeader("Authorization", "Bearer $token");
        return chain.proceed(request.build());
    }
}