package com.knd.duantotnghiep.duantotnghiep.ui.dialog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseBottomSheetFrg;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentConfirmOtpBinding;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SignUpRequest;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.ui.SignInActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.sign_up.SignUpActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.sign_up.SignUpViewModel;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.TextChangeListener;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ConfirmOtpBottomSheet extends BaseBottomSheetFrg<FragmentConfirmOtpBinding> {
    ConfirmOtpCallback.ConfirmOtp confirmOtp;

    public ConfirmOtpBottomSheet(ConfirmOtpCallback.ConfirmOtp confirmOtp) {
        super(R.layout.fragment_confirm_otp);
        this.confirmOtp = confirmOtp;
    }

    private SignUpViewModel signUpViewModel;
    private ConfirmOtpViewModel mConfirmOtpViewModel;
    List<TextInputLayout> textInputLayouts = new ArrayList<>();
    private SignUpRequest signUpRequest;
    @Inject
    public UserPreferencesManager userPreferencesManager;

    @Override
    protected FragmentConfirmOtpBinding getViewBinding(View view) {
        return FragmentConfirmOtpBinding.bind(view);
    }

    @Override
    protected void initView() {
        countdown(binding.txtResend).start();
        binding.simmer.hideShimmer();
        binding.setViewModel(mConfirmOtpViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.btnConfirm.setOnClickListener(view -> {
            String txtOTP = mConfirmOtpViewModel.txtOtp1.getValue() +
                    mConfirmOtpViewModel.txtOtp2.getValue() +
                    mConfirmOtpViewModel.txtOtp3.getValue() +
                    mConfirmOtpViewModel.txtOtp4.getValue() +
                    mConfirmOtpViewModel.txtOtp5.getValue() +
                    mConfirmOtpViewModel.txtOtp6.getValue();
            confirmOtp.onConfirm(txtOTP);

        });
        binding.txtResend.setOnClickListener(view -> {
            countdown(binding.txtResend).start();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mConfirmOtpViewModel.txtOtp1.setValue("");
        mConfirmOtpViewModel.txtOtp2.setValue("");
        mConfirmOtpViewModel.txtOtp3.setValue("");
        mConfirmOtpViewModel.txtOtp4.setValue("");
        mConfirmOtpViewModel.txtOtp5.setValue("");
        mConfirmOtpViewModel.txtOtp6.setValue("");
    }

    @Override
    protected void initData() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                signUpRequest = getArguments().getSerializable("SignUpRequest", SignUpRequest.class);
            } else {
                signUpRequest = (SignUpRequest) getArguments().getSerializable("SignUpRequest");
            }
        } catch (Exception Re) {

        }
        mConfirmOtpViewModel = new ViewModelProvider(requireActivity()).get(ConfirmOtpViewModel.class);
        Collections.addAll(textInputLayouts, binding.number1, binding.number2, binding.number3, binding.number4, binding.number5, binding.number6);
        TextChangeListener textChangeListener = new TextChangeListener();
        textChangeListener.onTextChange(this::handleFocus, textInputLayouts.toArray(new TextInputLayout[6]));
    }

    private void handleFocus(String text, TextInputLayout textInputLayout) {
        mConfirmOtpViewModel.error.postValue("Vui lòng nhập mã OTP được gửi tới số điện thoại của bạn để hoàn tất đăng ký.");

        int currentIndex = textInputLayouts.indexOf(textInputLayout);
        if (text.length() == 1) {
            if (currentIndex < textInputLayouts.size() - 1) {
                textInputLayouts.get(currentIndex + 1).requestFocus();
            }
        } else {
            if (currentIndex >= 1) {
                textInputLayouts.get(currentIndex - 1).requestFocus();
            }
        }

    }

    private CountDownTimer countdown(TextView txtResend) {
        confirmOtp.onResent();
        return new CountDownTimer(60 * 2 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                txtResend.setText("Gửi lại mã sau " + (l / 1000) + " giây.");

            }

            @Override
            public void onFinish() {
                txtResend.setText("Gửi lại");
                txtResend.setEnabled(true);
            }
        };
    }

    @Override
    protected void initObserve() {
        mConfirmOtpViewModel.verifyOtp.observe(requireActivity(), result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<MessageResponse>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    confirmOtp.onConfirmSuccess();
                    dismiss();
                }

                @Override
                public void handleError(String error) {
                    Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleLoading() {

                }
            });
        });


    }
}