package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.LoginRequest;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OTP;
import com.knd.duantotnghiep.duantotnghiep.models.SignUpRequest;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.remote.AuthApi;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.TokenManager;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;

import javax.inject.Inject;

public class AuthRepository {//lớp trung gian giao tiếp giữa APi or local với viemModel
    private AuthApi auth;
    private TokenManager tokenManager;
    private UserPreferencesManager userPreferencesManager;
    private final MutableLiveData<NetworkResult<User>> _authenticateGoogle = new MutableLiveData<>();

    public LiveData<NetworkResult<User>> authenticateGoogleLiveData = _authenticateGoogle;

    private final MutableLiveData<NetworkResult<MessageResponse>> _signUpLocal = new MutableLiveData<>();

    public LiveData<NetworkResult<MessageResponse>> signUpLocal = _signUpLocal;

    private final MutableLiveData<NetworkResult<User>> _signInLocal = new MutableLiveData<>();

    public LiveData<NetworkResult<User>> signInLocal = _signInLocal;

    private final MutableLiveData<NetworkResult<MessageResponse>> _sendOtp = new MutableLiveData<>();

    public LiveData<NetworkResult<MessageResponse>> sendOtp = _sendOtp;

    private final MutableLiveData<NetworkResult<MessageResponse>> _updatePassword = new MutableLiveData<>();

    public LiveData<NetworkResult<MessageResponse>> updatePassword = _updatePassword;

    private final MutableLiveData<NetworkResult<MessageResponse>> _verifyOtp = new MutableLiveData<>();

    public LiveData<NetworkResult<MessageResponse>> verifyOtp = _verifyOtp;

    @Inject
    public AuthRepository(AuthApi auth, TokenManager tokenManager,UserPreferencesManager userPreferencesManager) {
        this.auth = auth;
        this.tokenManager = tokenManager;
        this.userPreferencesManager = userPreferencesManager;
    }

    public void authenticateGoogle(String idToken,String fcmToken) {
        NetworkResult.handleCallApi(auth.authenticateGoogle(idToken,fcmToken), _authenticateGoogle, value -> tokenManager.saveToken(value));
    }

    public void signUpLocal(SignUpRequest signUpRequest, String otp) {
        NetworkResult.handleCallApi(auth.signUpLocal(signUpRequest, otp), _signUpLocal, null);
    }
    public void signInLocal(LoginRequest loginRequest ,String fcmToken){
        NetworkResult.handleCallApi(auth.signInLocal(loginRequest,fcmToken), _signInLocal, value -> {
           tokenManager.saveToken(value);
        });
    }
    public void sendOtp(OTP otp) {
        NetworkResult.handleCallApi(auth.sendOtp(otp), _sendOtp, null);
    }

    public void verificationOTP(OTP otp) {
        NetworkResult.handleCallApi(auth.verificationOTP(otp), _verifyOtp, null);
    }

    public void updatePassword(String address, String newPass) {
        NetworkResult.handleCallApi(auth.updatePassword(address, newPass), _updatePassword, null);
    }
}