package com.knd.duantotnghiep.duantotnghiep.ui.dialog;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SignUpRequest;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.respository.AuthRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ConfirmOtpViewModel extends ViewModel {
    public MutableLiveData<Boolean> enableError = new MutableLiveData(false);
    public MutableLiveData<String> txtOtp1 = new MutableLiveData("");
    public MutableLiveData<String> txtOtp2 = new MutableLiveData("");
    public MutableLiveData<String> txtOtp3 = new MutableLiveData("");
    public MutableLiveData<String> txtOtp4 = new MutableLiveData("");
    public MutableLiveData<String> txtOtp5 = new MutableLiveData("");
    public MutableLiveData<String> txtOtp6 = new MutableLiveData("");
    public MutableLiveData<String> address = new MutableLiveData("");
    public MutableLiveData<String> error = new MutableLiveData("");
    private final AuthRepository authResponse;
    public LiveData<NetworkResult<MessageResponse>> signUpLocal;
    public LiveData<NetworkResult<MessageResponse>> verifyOtp;


    @Inject
    public ConfirmOtpViewModel(AuthRepository authResponse) {
        this.authResponse = authResponse;
        signUpLocal = authResponse.signUpLocal;
        verifyOtp = authResponse.verifyOtp;
    }

    public void signUpLocal(SignUpRequest signUpRequest, String otp) {
        if (otp.isEmpty()) {
            error.postValue("Please ensure the otp field is not left blank.");
            enableError.postValue(true);
        } else if (otp.length() < 6) {
            error.postValue("Please enter complete information");
            enableError.postValue(true);
        } else {
            enableError.postValue(false);
         }
    }

}
