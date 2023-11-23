package com.knd.duantotnghiep.duantotnghiep.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.broadcast.NetworkMonitor;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemClothesFragmentBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.ui.details.DetailsActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;

import java.util.ArrayList;


import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ItemClothes extends BaseFragment<ItemClothesFragmentBinding> {

    private String idCategory;


    public ItemClothes() {
        super(R.layout.item_clothes_fragment);
    }

    private ProductAdapter productAdapter;
    private ClothesViewModel clothesViewModel;
    @Inject
    public NetworkMonitor networkMonitor;

    @Override
    public void initData() {
        productAdapter = new ProductAdapter( );
        binding.recyclerView.setAdapter(productAdapter);
        productAdapter.setOnClickItemListener(item -> {
            Intent intent = new Intent(requireActivity(), DetailsActivity.class) ;
            intent.putExtra("idProduct",item.get_id());
            startActivity(intent);
        });
        idCategory = getArguments().getString("idCategory");
        clothesViewModel = new ViewModelProvider(this).get(ClothesViewModel.class);
        binding.mRefreshLayout.setOnRefreshListener(this::checkCallApi);
    }

    @Override
    public void initObserver() {
        networkMonitor.observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                checkCallApi();
            } else {
                showMessage("Please check your connection and try again");
            }
        });

        clothesViewModel.getProducts.observe(requireActivity(), arrayListNetworkResult -> {
            ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(ArrayList<ProductResponse> data) {
                    if (isAdded()) {
                         productAdapter.setData(data);
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
        });

        clothesViewModel.getProductByCategory.observe(requireActivity(), arrayListNetworkResult -> {
            ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(ArrayList<ProductResponse> data) {
                    if (isAdded()) {
                        productAdapter.setData(data);
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
        });
    }

    private void checkCallApi() {
     if (idCategory.isEmpty()) {
            clothesViewModel.getProduct();
        } else {
            clothesViewModel.getProductByCategory(idCategory);
         }
    }

    @Override
    public ItemClothesFragmentBinding getViewBinding(View view) {
        return ItemClothesFragmentBinding.bind(view);
    }
}