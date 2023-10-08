package com.knd.duantotnghiep.duantotnghiep.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityLoginBinding;
import com.knd.duantotnghiep.duantotnghiep.ui.sign_up.SignUpActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected void initView() {
        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this, SignUpActivity.class );
                startActivity(intent);
            }
        });
    }

    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }
}