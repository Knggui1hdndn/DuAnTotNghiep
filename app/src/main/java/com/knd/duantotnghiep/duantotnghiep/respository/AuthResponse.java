package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SignUpRequest;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.remote.AuthApi;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.TokenManager;

import javax.inject.Inject;

public class AuthResponse {//lớp trung gian giao tiếp giữa APi or local với viemModel
    private AuthApi auth;
    private TokenManager tokenManager;
    private final MutableLiveData<NetworkResult<User>> _authenticateGoogle = new MutableLiveData<>();

    public LiveData<NetworkResult<User>> authenticateGoogleLiveData = _authenticateGoogle;

    private final MutableLiveData<NetworkResult<MessageResponse>> _signUpLocal = new MutableLiveData<>();

    public LiveData<NetworkResult<MessageResponse>> signUpLocal = _signUpLocal;

    private final MutableLiveData<NetworkResult<MessageResponse>> _sendOtp = new MutableLiveData<>();

    public LiveData<NetworkResult<MessageResponse>>  sendOtp = _sendOtp;

    private final MutableLiveData<NetworkResult<MessageResponse>> _updatePassword = new MutableLiveData<>();

    public LiveData<NetworkResult<MessageResponse>>  updatePassword = _updatePassword;

    private final MutableLiveData<NetworkResult<MessageResponse>> _verifyOtp = new MutableLiveData<>();

    public LiveData<NetworkResult<MessageResponse>>  verifyOtp = _verifyOtp;

    @Inject
    public AuthResponse(AuthApi auth,TokenManager tokenManager) {
        this.auth = auth;
        this.tokenManager=tokenManager;
    }

    public void authenticateGoogle(String idToken) {
        NetworkResult.handleCallApi(auth.authenticateGoogle(idToken), _authenticateGoogle, null);
    }

    public void signUpLocal(SignUpRequest signUpRequest,String otp ) {
        NetworkResult.handleCallApi(auth.signUpLocal(signUpRequest, otp), _signUpLocal ,null);
    }

    public void sendOtp(String type ,String address) {
        NetworkResult.handleCallApi(auth.sendOtp(type,address), _sendOtp, null);
    }

    public void verificationOTP(String type ,String address) {
        NetworkResult.handleCallApi(auth.verificationOTP(type,address), _verifyOtp, null);
    }

    public void updatePassword(String address ,String newPass) {
        NetworkResult.handleCallApi(auth.updatePassword(address,newPass), _updatePassword, null);
    }
}