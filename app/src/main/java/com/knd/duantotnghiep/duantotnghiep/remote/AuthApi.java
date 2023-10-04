package com.knd.duantotnghiep.duantotnghiep.remote;

import com.knd.duantotnghiep.duantotnghiep.models.LoginRequest;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    //login,logout,send otp,forgot password
    @POST("auth/signIn")
    public Call<MessageResponse> Login(@Body LoginRequest loginRequest);
}
