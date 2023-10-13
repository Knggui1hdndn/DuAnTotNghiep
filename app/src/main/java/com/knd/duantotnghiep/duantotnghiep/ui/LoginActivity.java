package com.knd.duantotnghiep.duantotnghiep.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 
import com.knd.duantotnghiep.duantotnghiep.Dao.SearchDao;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityLoginBinding;
 import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;
import com.knd.duantotnghiep.duantotnghiep.ui.sign_up.SignUpActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint //bắt buộc
public class LoginActivity extends BaseActivity<ActivityLoginBinding>{
  //bắt buộc @Inject
    @Inject
    public SearchDao searchDao;
    @Override
    protected void initData() {
      searchDao.insertSearch(new SearchLocal("150","ok","ok"));
        Log.d("test data base",searchDao.getListSearch().size()+"");

    }

   
 
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