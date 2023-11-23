package com.knd.duantotnghiep.duantotnghiep.ui.signIn;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.messaging.FirebaseMessaging;
import com.knd.duantotnghiep.duantotnghiep.Dao.SearchDao;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivitySignInBinding;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.ui.MainActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.forgot_password.ForgotPasswordActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.sign_up.HandleSignUpGoogle;
import com.knd.duantotnghiep.duantotnghiep.ui.sign_up.SignUpActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint //bắt buộc
public class LoginActivity extends HandleSignInGoogle {
    //bắt buộc @Inject
    @Inject
    public SearchDao searchDao;
    private LoginViewModel loginViewModel;
    @Inject
    public UserPreferencesManager userPreferencesManager;


    @Override
    protected void initData() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setSignInViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
    }


    @Override
    protected void initView() {
        if (userPreferencesManager.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
        binding.tvForgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        binding.btnSignIn.setOnClickListener(view -> {
            loginViewModel.handleSignInLocal();
        });

        binding.btnLoginGg.setOnClickListener(view -> {
            signInGoogle();
        });

        binding.SignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void initObserver() {
        loginViewModel.loginLocal.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
            @Override
            public void handleSuccess(User data) {
                Log.d("adssssssssssssssss", data.getName() + "");
                userPreferencesManager.saveUser(data);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
                loginViewModel.errAccount.setValue(error);
                loginViewModel.errPassword.setValue(error);
            }

            @Override
            public void handleLoading() {

            }
        }));

        loginViewModel.getAuthenticateGoogle().observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<User>() {
            @Override
            public void handleSuccess(User data) {
                userPreferencesManager.saveUser(data);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
            }

            @Override
            public void handleLoading() {

            }
        }));
    }

    @Override
    public void SignInGoogleClientSuccess(String idToken) {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(s -> loginViewModel.authenticateGoogle(idToken, s));
    }

    @Override
    public void SignInGoogleClientError(String errorMessage) {
        showMessage(errorMessage);
    }


}