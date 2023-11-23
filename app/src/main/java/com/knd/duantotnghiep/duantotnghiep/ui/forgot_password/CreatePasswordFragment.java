package com.knd.duantotnghiep.duantotnghiep.ui.forgot_password;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentCreatePasswordBinding;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.AuthRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreatePasswordFragment extends BaseFragment<FragmentCreatePasswordBinding> {
    @Inject
    public AuthRepository authResponse;


    public CreatePasswordFragment() {
        super(R.layout.fragment_create_password);
    }

    @Override
    public void initObserver() {
        authResponse.updatePassword.observe(requireActivity(), result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    Toast.makeText(requireActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
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

    @Override
    public void initData() {
        binding.confirm.setOnClickListener(view -> validatePassWord());
    }


    private void validatePassWord() {
        String pass = binding.pass.getEditText().getText().toString();
        String confirmPass = binding.confirmPass.getEditText().getText().toString();
        String address = getArguments().getString("address");
        Log.d("sakoskosa",address.toString());
        if (pass.equals(confirmPass) && (pass.length() >= 8 && confirmPass.length() >= 8)) {
            authResponse.updatePassword(address, confirmPass);
        } else if (pass.length() <= 8 || confirmPass.length() <= 8) {
            Toast.makeText(requireActivity(), "Password contains at least 8 characters", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireActivity(), "Please enter the same", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public FragmentCreatePasswordBinding getViewBinding(View view) {
        return FragmentCreatePasswordBinding.bind(view);
    }
}