package com.knd.duantotnghiep.duantotnghiep.ui.my_order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityMyOderBinding;

import java.util.ArrayList;
import java.util.Arrays;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyOderActivity extends BaseActivity<ActivityMyOderBinding> {
private ItemOrderViewPager itemOrderViewPager;
    @Override
    protected void initData() {
        itemOrderViewPager=new ItemOrderViewPager(this);
    }
    private final ArrayList<String> strings = new ArrayList<>(Arrays.asList("Wait for confirmation","Confirmed", "Delivering", "Delivered", "Cancel", "Returns"));

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void initView() {
        setUpToolBar(binding.toolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));
        binding.viewPager.setOffscreenPageLimit(5);
        binding.viewPager.setAdapter(itemOrderViewPager);
         new TabLayoutMediator(binding.tab, binding.viewPager, (tab, position) -> {
             tab.setText(strings.get(position));
        }).attach();
    }

    @Override
    public ActivityMyOderBinding getViewBinding() {
        return ActivityMyOderBinding.inflate(LayoutInflater.from(this));
    }
}