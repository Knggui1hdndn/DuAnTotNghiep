package com.knd.duantotnghiep.duantotnghiep.ui.main;

import android.content.Intent;

import android.view.View;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentHomeBinding;
import com.knd.duantotnghiep.duantotnghiep.ui.search.SearchActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initView() {
        binding.txtSearch.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), SearchActivity.class));
            requireActivity().overridePendingTransition(R.anim.transtion_search_enter,R.anim.transtion_search);
        });
    }

    @Override
    public FragmentHomeBinding getViewBinding(View view) {
        return FragmentHomeBinding.bind(view);
    }
}