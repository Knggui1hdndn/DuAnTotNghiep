package com.knd.duantotnghiep.duantotnghiep.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityMainBinding;
import com.knd.duantotnghiep.duantotnghiep.models.LoginRequest;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    MainViewModel mainViewModel;

    @Override
    protected void initData() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @NonNull
    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    //phương thức bên base activity
    @Override
    protected void initObserver() {
       mainViewModel.Login(new LoginRequest("Nguyễn Duy Khang","0867896418"));//start login

        mainViewModel.OnLogin.observe(this, result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    //Xử lí khi thành công
                    Log.d("Test result", data.getMessage());
                    binding.text.setText(data.getMessage());
                }

                @Override
                public void handleError(String error) {
                    //Xử lí khi lỗi
                    Log.d("Test result", error);
                }

                @Override
                public void handleLoading() {
                    //Xử lí khi đang load
                    Log.d("Test result", "load");

                }
            });

        });
    }
}