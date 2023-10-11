package com.knd.duantotnghiep.duantotnghiep.ui.sign_up;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SignUpRequest;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.respository.AuthResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignUpViewModel extends ViewModel {
    private AuthResponse authResponse;

    @Inject
    public SignUpViewModel(AuthResponse authResponse) {
        this.authResponse = authResponse;
        sendOtp = authResponse.sendOtp;
        signUpLocal = authResponse.signUpLocal;
        verificationOtp = authResponse.verifyOtp;
    }

    public MutableLiveData<String> txtLastName = new MutableLiveData<>();
    public MutableLiveData<String> txtFirstName = new MutableLiveData<>();
    public MutableLiveData<String> txtAddress = new MutableLiveData<>();
    public MutableLiveData<String> txtPhoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> txtPassword = new MutableLiveData<>();
    public MutableLiveData<String> errorPhoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> errorPassword = new MutableLiveData<>();
    public MutableLiveData<SignUpRequest> signUpRequest = new MutableLiveData<>();
    public LiveData<NetworkResult<MessageResponse>> sendOtp;
    public LiveData<NetworkResult<MessageResponse>> verificationOtp;
    public LiveData<NetworkResult<MessageResponse>> signUpLocal;

    public LiveData<NetworkResult<User>> getAuthenticateGoogle() {
        return authResponse.authenticateGoogleLiveData;
    }

    private Boolean isNotNullAndEmpty(MutableLiveData<String> mutableLiveData) {
        return mutableLiveData.getValue() != null && !mutableLiveData.getValue().isEmpty();
    }

    public void handleSignUpLocal() {
        boolean isPhoneNumberValid = isNotNullAndEmpty(txtPhoneNumber) && Patterns.PHONE.matcher(txtPhoneNumber.getValue()).matches();
        boolean isPasswordValid = isNotNullAndEmpty(txtPassword) && txtPassword.getValue().length() >= 8;
        boolean isFirstNameNotEmpty = isNotNullAndEmpty(txtFirstName);
        boolean isLastNameNotEmpty = isNotNullAndEmpty(txtLastName);
        boolean isAddressNotEmpty = isNotNullAndEmpty(txtAddress);


        if (isNotNullAndEmpty(txtPhoneNumber))
            errorPhoneNumber.postValue(!isPhoneNumberValid ? "Invalid phone number" : null);
        if (isNotNullAndEmpty(txtPassword))
            errorPassword.postValue(!isPasswordValid ? "Password contains at least 8 characters" : null);

        if (isPhoneNumberValid && isPasswordValid && isFirstNameNotEmpty && isLastNameNotEmpty && isAddressNotEmpty) {
            SignUpRequest signUpRequest1 = new SignUpRequest(getValue(txtFirstName) + " " + getValue(txtLastName),
                    getValue(txtPassword),
                    getValue(txtAddress),
                    getValue(txtPhoneNumber));
            signUpRequest.setValue(signUpRequest1);
            sendOtp("phone", txtPhoneNumber.getValue());
        } else {
            setValueIfNotNull(txtPhoneNumber);
            setValueIfNotNull(txtPassword);
            setValueIfNotNull(txtFirstName);
            setValueIfNotNull(txtLastName);
            setValueIfNotNull(txtAddress);
        }
    }

    private void setValueIfNotNull(MutableLiveData<String> mutableLiveData) {
        mutableLiveData.setValue(getValue(mutableLiveData) != null ? getValue(mutableLiveData) : "");
    }

    public String getValue(MutableLiveData<String> mutableLiveData) {
        return mutableLiveData.getValue();
    }

    public void sendOtp(String type, String address) {
        authResponse.sendOtp(type, address);
    }
    public void verificationOtp(String type, String address) {
        authResponse.verificationOTP(type, address);
    }

    public void signUpLocal(SignUpRequest signUpRequest, String otp) {
        authResponse.signUpLocal(signUpRequest, otp);
    }

    public void authenticateGoogle(String idToken) {
        authResponse.authenticateGoogle(idToken);
    }
}