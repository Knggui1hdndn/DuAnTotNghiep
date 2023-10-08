package com.knd.duantotnghiep.duantotnghiep.ui.sign_up;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivitySignUpBinding;

public abstract class HandleSignUpGoogle extends BaseActivity<ActivitySignUpBinding> {
    abstract void SignInGoogleClientSuccess(String idToken);

    abstract void SignInGoogleClientError(String errorMessage);

    @Override
    protected ActivitySignUpBinding getViewBinding() {
        return ActivitySignUpBinding.inflate(getLayoutInflater());
    }

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;


    private ActivityResultLauncher<IntentSenderRequest> senderIntent = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
        if (result.getData() != null) {
            try {
                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                String idToken = credential.getGoogleIdToken();
                SignInGoogleClientSuccess(idToken);
            } catch (ApiException e) {
                SignInGoogleClientError(e.getLocalizedMessage());
            }
        }
    });

    public void signInGoogle() {
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(false)
                .build();
        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener(this, result -> {
            try {
                IntentSenderRequest intentSender = new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                senderIntent.launch(intentSender);
            } catch (Exception e) {
                SignInGoogleClientError(e.getLocalizedMessage());
            }
        }).addOnFailureListener(this, e -> SignInGoogleClientError(e.getLocalizedMessage()));
    }

}