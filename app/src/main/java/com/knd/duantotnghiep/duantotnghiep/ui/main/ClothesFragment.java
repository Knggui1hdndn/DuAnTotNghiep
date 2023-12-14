package com.knd.duantotnghiep.duantotnghiep.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.broadcast.NetworkMonitor;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentClothesBinding;
import com.knd.duantotnghiep.duantotnghiep.models.Category;
import com.knd.duantotnghiep.duantotnghiep.models.CategoryResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClothesFragment extends BaseFragment<FragmentClothesBinding> {

    public ClothesFragment() {
        super(R.layout.fragment_clothes);
    }

    private ClothesViewModel clothesViewModel;
    private CategoryResponse categoryResponse;
    @Inject
    public NetworkMonitor networkMonitor;
    private ClothesViewPager clothesViewPager;

    @Override
    public void initData() {
        clothesViewModel = new ViewModelProvider(this).get(ClothesViewModel.class);
    }

    @Override
    public void initView() {
        binding.viewPager2.setOffscreenPageLimit(3);
        binding.mRefreshLayout.setOnRefreshListener(() -> {
            clothesViewModel.getCategories();
        });
    }

    @Override
    public void initObserver() {
        networkMonitor.observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                clothesViewModel.getCategories();
            } else {
                showMessage("Vui lòng kiểm tra kết nối của bạn và thử lại");
            }
        });

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2, (tab, position) -> {
            tab.setText(categoryResponse.get(position).getCategory());
        });

        clothesViewModel.getCategories.observe(requireActivity(), arrayListNetworkResult -> {
            ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(CategoryResponse data) {
                    categoryResponse = data;
                    if (isAdded() && clothesViewPager == null) {
                        binding.mRefreshLayout.setEnabled(false);
                        clothesViewPager = new ClothesViewPager(requireActivity().getSupportFragmentManager(), getLifecycle(), categoryResponse);
                        binding.viewPager2.setAdapter(clothesViewPager);
                        if (!tabLayoutMediator.isAttached()) {
                            tabLayoutMediator.attach();
                        }
                    }
                }

                @Override
                public void handleError(String error) {
                    showMessage(error);
                }

                @Override
                public void handleLoading() {

                }
            });
        });
    }

    @Override
    public FragmentClothesBinding getViewBinding(View view) {
        return FragmentClothesBinding.bind(view);
    }
}