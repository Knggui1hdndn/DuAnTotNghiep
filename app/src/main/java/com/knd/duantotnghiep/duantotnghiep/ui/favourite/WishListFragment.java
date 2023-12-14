package com.knd.duantotnghiep.duantotnghiep.ui.favourite;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentWishListBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.ProductRepository;
import com.knd.duantotnghiep.duantotnghiep.ui.details.DetailsActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.main.ClothesViewModel;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WishListFragment extends BaseFragment<FragmentWishListBinding> {
     public ClothesViewModel clothesViewModel;
    private FavouriteAdapter favouriteAdapter;

    public WishListFragment() {
        super(R.layout.fragment_wish_list);
    }

    @Override
    public void initData() {
        clothesViewModel=new ViewModelProvider(this).get(ClothesViewModel.class);
        clothesViewModel.getFavourites();
    }

    @Override
    public void onResume() {
        super.onResume();
        clothesViewModel.getFavourites();
    }

    @Override
    public void initObserver() {
        clothesViewModel.getFavourites.observe(this, arrayListNetworkResult -> {
            ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(ArrayList<ProductResponse> data) {
                     favouriteAdapter.setData(data);
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
    public void initView() {
        favouriteAdapter = new FavouriteAdapter();
        binding.rcvWishList.setAdapter(favouriteAdapter);
        favouriteAdapter.setOnClickItemListener(item -> {
            Intent intent = new Intent(requireActivity(), DetailsActivity.class) ;
            intent.putExtra("idProduct",item.get_id());
            startActivity(intent);
        });
    }

    @Override
    public FragmentWishListBinding getViewBinding(View view) {
        return FragmentWishListBinding.bind(view);
    }
}