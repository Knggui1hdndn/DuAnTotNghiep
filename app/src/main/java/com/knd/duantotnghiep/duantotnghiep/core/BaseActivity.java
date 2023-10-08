package com.knd.duantotnghiep.duantotnghiep.core;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.LayoutRes;
import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<BD extends ViewBinding> extends AppCompatActivity {


    protected abstract BD getViewBinding();

    protected void initView() {
    }

    protected void initObserver() {
    }

    protected void initData() {
    }

    @MenuRes
    protected int getMenu() {
        return -1;
    }

    protected BD binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        setContentView(binding.getRoot());
        initData();
        initView();
        initObserver();

    }

    protected void setUpToolBar(Toolbar toolbar, boolean enableDisplayHome, Drawable draw) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enableDisplayHome);
            getSupportActionBar().setHomeAsUpIndicator(draw);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuResId = getMenu();
        if (menuResId != -1) {
            getMenuInflater().inflate(menuResId, menu);
            this.menu = menu;
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public  void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
