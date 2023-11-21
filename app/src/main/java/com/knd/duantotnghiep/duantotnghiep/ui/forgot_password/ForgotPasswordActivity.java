package com.knd.duantotnghiep.duantotnghiep.ui.forgot_password;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityForgotPasswordBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding> {

    @Override
    public ActivityForgotPasswordBinding getViewBinding() {
        return ActivityForgotPasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initObserver() {

    }

    NavController navController;

    @Override
    protected void initView() {
        setSupportActionBar(binding.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(navController.getGraph()).build();
        // Liên kết thanh công cụ với NavController
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfig);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }); ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp() || navController.navigateUp();
    }
}