package com.knd.duantotnghiep.duantotnghiep.respository;

import com.knd.duantotnghiep.duantotnghiep.models.LoginRequest;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.remote.AuthApi;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class AuthRespo {//lớp trung gian giao tiếp giữa APi or local với viemModel
    private AuthApi auth;

    @Inject
    public AuthRespo(AuthApi auth) {
        this.auth = auth;
    }

    public Call<MessageResponse> Login(LoginRequest loginRequest) {
        return auth.Login(loginRequest);
    }
}