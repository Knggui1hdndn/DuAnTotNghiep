package com.knd.duantotnghiep.duantotnghiep.ui.signIn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.knd.duantotnghiep.duantotnghiep.models.LoginRequest;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.respository.AuthRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private AuthRepository authResponse;
    public MutableLiveData<String> txtAccount = new MutableLiveData<>();
    public MutableLiveData<String> txtPassword = new MutableLiveData<>();

    public MutableLiveData<String> errAccount = new MutableLiveData<>();
    public MutableLiveData<String> errPassword = new MutableLiveData<>();

    public LiveData<NetworkResult<User>> loginLocal;

    public void handleSignInLocal() {
        Boolean checkAccount = txtAccount.getValue().isEmpty();
        Boolean checkPass = txtPassword.getValue().isEmpty();
        if (!checkPass && !checkAccount) {
            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(s -> loginLocal(new LoginRequest(txtAccount.getValue(), txtPassword.getValue()),s));
        }
        if (checkPass) {
            errPassword.postValue("Do not blank password");
        }
        if (checkAccount) {
            errAccount.postValue("Do not blank account");
        }

    }

    @Inject
    public LoginViewModel(AuthRepository authResponse) {
        this.authResponse = authResponse;
        loginLocal = authResponse.signInLocal;
    }

    public void authenticateGoogle(String idToken, String fcmToken) {
        authResponse.authenticateGoogle(idToken, fcmToken);
    }

    public void loginLocal(LoginRequest loginRequest, String fcmToken) {
        authResponse.signInLocal(loginRequest, fcmToken);
    }

    public LiveData<NetworkResult<User>> getAuthenticateGoogle() {
        return authResponse.authenticateGoogleLiveData;
    }
}
