package com.knd.duantotnghiep.duantotnghiep.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.broadcast.NetworkMonitor;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.core.Pagination;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemClothesFragmentBinding;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.ui.details.DetailsActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.socket.client.Socket;

@AndroidEntryPoint
public class ItemClothes extends BaseFragment<ItemClothesFragmentBinding> {

    private String idCategory;

    private boolean check = false;

    public ItemClothes() {
        super(R.layout.item_clothes_fragment);
    }

    private ProductAdapter productAdapter;
    private ClothesViewModel clothesViewModel;
    @Inject
    public NetworkMonitor networkMonitor;

    @Override
    public void initData() {
        productAdapter = new ProductAdapter();

        binding.recyclerView.setAdapter(productAdapter);
        productAdapter.setOnClickItemListener(item -> {
             Intent intent = new Intent(requireActivity(), DetailsActivity.class);
            intent.putExtra("idProduct", item.get_id());
            startActivity(intent);
        });
        idCategory = getArguments().getString("idCategory");
        assert idCategory != null;
        check = idCategory.isEmpty();

        clothesViewModel = new ViewModelProvider(this).get(ClothesViewModel.class);

        binding.mRefreshLayout.setOnRefreshListener(this::checkCallApi);

    }

    Observer<NetworkResult<List<ProductResponse>>> productObserver;
    Observer<NetworkResult<List<ProductResponse>>> productByCategory;

    @Override
    public void initObserver() {
        networkMonitor.observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                checkCallApi();
            } else {
                showMessage("Vui lòng kiểm tra kết nối của bạn và thử lại");
            }
        });
        registerObserver();

        Pagination pagination = new Pagination<ProductResponse>(this,
                binding.mProgress,
                productAdapter,
                !check ? clothesViewModel.getProductByCategory : clothesViewModel.getProducts, size -> {
            registerObserver();
            if (!check) {
                clothesViewModel.getProductByCategory(idCategory, size);
            } else {
                clothesViewModel.getProduct(size);


            }
        }).attach();
        binding.recyclerView.addOnScrollListener(pagination);
    }

    private void registerObserver() {
        productByCategory = arrayListNetworkResult -> {
            ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(List<ProductResponse> data) {
                    if (isAdded()) {
                        binding.mRefreshLayout.setEnabled(false);

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
            clothesViewModel.getProductByCategory.removeObserver(productByCategory);

        };
        productObserver = arrayListNetworkResult -> {
            ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(List<ProductResponse> data) {
                    if (isAdded()) {
                        binding.mRefreshLayout.setEnabled(false);

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
            clothesViewModel.getProducts.removeObserver(productObserver);

        };
        clothesViewModel.getProducts.observe(this, productObserver);
        clothesViewModel.getProductByCategory.observe(this, productByCategory);
    }

    private void checkCallApi() {
        if (check) {
            clothesViewModel.getProduct(0);
        } else {
            clothesViewModel.getProductByCategory(idCategory, 0);
        }
    }

    @Override
    public ItemClothesFragmentBinding getViewBinding(View view) {
        return ItemClothesFragmentBinding.bind(view);
    }
}