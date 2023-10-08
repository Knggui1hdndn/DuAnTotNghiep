package com.knd.duantotnghiep.duantotnghiep.ui.sign_up;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.textfield.TextInputLayout;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivitySignUpBinding;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SignUpRequest;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.ui.LoginActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.SignInActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.dialog.ConfirmOtpBottomSheet;
import com.knd.duantotnghiep.duantotnghiep.ui.dialog.ConfirmOtpCallback;
import com.knd.duantotnghiep.duantotnghiep.ui.dialog.DialogLoading;
import com.knd.duantotnghiep.duantotnghiep.ui.home.HomeActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;


import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpActivity extends HandleSignUpGoogle {

    public SignUpViewModel signUpViewModel;
    private SignUpRequest signUpRequest;
    @Inject
    public UserPreferencesManager userPreferencesManager;

    @Override
    void SignInGoogleClientSuccess(String idToken) {
        signUpViewModel.authenticateGoogle(idToken);
    }

    @Override
    void SignInGoogleClientError(String errorMessage) {
        showMessage(errorMessage);
    }

    @Override
    protected ActivitySignUpBinding getViewBinding() {
        return ActivitySignUpBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding.setSignUpViewModel(signUpViewModel);
        binding.setLifecycleOwner(this);

    }

    @Override
    protected void initView() {
        binding.btnSignIn.setOnClickListener(view -> {
            signUpViewModel.handleSignUpLocal();
        });
        binding.buttonSignIn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });

        binding.btnLoginGg.setOnClickListener(view -> {
            signInGoogle();
        });
    }

    @Override
    protected void initObserver() {
        DialogLoading dialogLoading = new DialogLoading();
        ConfirmOtpBottomSheet confirmOtpBottomSheet = new ConfirmOtpBottomSheet(new ConfirmOtpCallback.ConfirmOtp() {
            @Override
            public void onConfirmSuccess() {

            }

            @Override
            public void onConfirm(String otp) {
                signUpViewModel.signUpLocal(signUpViewModel.signUpRequest.getValue(), otp);
            }

            @Override
            public void onResent() {
                signUpViewModel.handleSignUpLocal();
            }
        });
        HashMap<MutableLiveData<String>, TextInputLayout> mutableLiveData = new HashMap<>();
        mutableLiveData.put(signUpViewModel.txtAddress, binding.edtAddress);
        mutableLiveData.put(signUpViewModel.txtFirstName, binding.edtFirstName);
        mutableLiveData.put(signUpViewModel.txtPassword, binding.edtPassword);
        mutableLiveData.put(signUpViewModel.txtPhoneNumber, binding.edtPhoneNumber);
        mutableLiveData.put(signUpViewModel.txtLastName, binding.edtLateName);

        signUpViewModel.errorPhoneNumber.observe(this, txt -> {
            binding.edtPhoneNumber.setError(txt);
        });

        signUpViewModel.errorPassword.observe(this, txt -> {
            binding.edtPassword.setError(txt);
        });

        mutableLiveData.forEach((liveData, textInputLayout) -> {
            liveData.observe(this, txt -> {
                if (txt != null && !txt.isEmpty()) textInputLayout.setError(null);
                else textInputLayout.setError("Do not leave blank " + textInputLayout.getHint());
            });
        });

        signUpViewModel.getAuthenticateGoogle().observe(this, userNetworkResult -> {
            ApiCallBack.handleResult(userNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(User data) {
                    userPreferencesManager.saveUser(data);
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                    showMessage("Login Success");
                    finish();
                }

                @Override
                public void handleError(String error) {
                    showMessage(error);

                }

                @Override
                public void handleLoading() {

                }
            });

        });

        signUpViewModel.sendOtp.observe(this, result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    showMessage(data.getMessage());
                    if (!confirmOtpBottomSheet.isVisible()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("SignUpRequest", signUpViewModel.signUpRequest.getValue());
                        confirmOtpBottomSheet.setArguments(bundle);
                        confirmOtpBottomSheet.show(getSupportFragmentManager(), confirmOtpBottomSheet.getClass().getName());

                    }
                }

                @Override
                public void handleError(String error) {
                    showMessage(error);

                }

                @Override
                public void handleLoading() {

                }
            });
        });

        signUpViewModel.signUpLocal.observe(this, result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<MessageResponse>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    showMessage("Create account successfully");
                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    finish();
                }

                @Override
                public void handleError(String error) {
                    showMessage(error);
                }

                @Override
                public void handleLoading() {

                }
            });
        });
    }
}