package com.knd.duantotnghiep.duantotnghiep.ui.forgot_password;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragEnterEmailPhoneBinding;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OTP;
import com.knd.duantotnghiep.duantotnghiep.ui.dialog.ConfirmOtpBottomSheet;
import com.knd.duantotnghiep.duantotnghiep.ui.dialog.ConfirmOtpCallback;
import com.knd.duantotnghiep.duantotnghiep.ui.dialog.DialogLoading;
import com.knd.duantotnghiep.duantotnghiep.ui.sign_up.SignUpViewModel;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgotPassEnterInfoFragment extends BaseFragment<FragEnterEmailPhoneBinding> {
    public ForgotPassEnterInfoFragment() {
        super(R.layout.frag_enter_email_phone);
    }

    private String type;
    private SignUpViewModel signUpViewModel;

    @Override
    public void initData() {
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
    }

    @Override
    public void initObserver() {
        DialogLoading dialogLoading = new DialogLoading();
        ConfirmOtpBottomSheet confirmOtpBottomSheet = new ConfirmOtpBottomSheet(new ConfirmOtpCallback.ConfirmOtp() {
            @Override
            public void onConfirmSuccess() {

            }

            @Override
            public void onConfirm(String otp) {
                signUpViewModel.verificationOtp(new OTP(type,  binding.edtAddress.getEditText().getText().toString(), null));
            }

            @Override
            public void onResent() {
                validateAddress();
            }
        });

        signUpViewModel.verificationOtp.observe(requireActivity(), result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    Bundle bundle = new Bundle();
                    bundle.putString("address", binding.edtAddress.getEditText().getText().toString());
                    Navigation.findNavController(requireView()).navigate(R.id.createPasswordFragment, bundle);
                }

                @Override
                public void handleError(String error) {
                    Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show();
                }

                @Override
                public void handleLoading() {

                }
            });
        });

        signUpViewModel.sendOtp.observe(requireActivity(), result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    if (!confirmOtpBottomSheet.isVisible()) {
                        confirmOtpBottomSheet.show(getChildFragmentManager(), "ConfirmOtpBottomSheet");
                    }
                }

                @Override
                public void handleError(String error) {
                    Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show();
                }

                @Override
                public void handleLoading() {

                }
            });
        });
    }

    @Override
    public void initView() {
        binding.btnNext.setOnClickListener(view -> {
            validateAddress();
        });
        binding.edtAddress.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.edtAddress.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public FragEnterEmailPhoneBinding getViewBinding(View view) {
        return FragEnterEmailPhoneBinding.bind(view);
    }

    private void validateAddress() {
        String address = binding.edtAddress.getEditText().getText().toString();
        if (address.isEmpty()) {
            binding.edtAddress.setError("Không để trống đầu vào");
        } else {
            if (Patterns.PHONE.matcher(address).matches()) {
                signUpViewModel.sendOtp(new OTP("phone", address,null));
                type = "phone";
            }

            if (Patterns.EMAIL_ADDRESS.matcher(address).matches()) {
                signUpViewModel.sendOtp(new OTP("email", address,null));
                type = "email";
            }
        }

    }
}
