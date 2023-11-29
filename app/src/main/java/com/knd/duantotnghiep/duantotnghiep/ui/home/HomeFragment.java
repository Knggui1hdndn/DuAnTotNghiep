package com.knd.duantotnghiep.duantotnghiep.ui.home;

import android.content.Intent;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Api;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.core.Pagination;
import com.knd.duantotnghiep.duantotnghiep.core.PaginationCallback;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentHomeBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.ui.details.DetailsActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.main.ClothesViewModel;
import com.knd.duantotnghiep.duantotnghiep.ui.main.ProductAdapter;
import com.knd.duantotnghiep.duantotnghiep.ui.search.SearchActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private ClothesViewModel clothesViewModel;
    private AdapterPagerSale adapterPagerSale;
    private ProductAdapter productAdapter;
    private boolean checkLoading = false;
    private ArrayList<ProductResponse> productResponses = new ArrayList<ProductResponse>();
    private int position;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void initData() {
        productAdapter = new ProductAdapter();
        adapterPagerSale = new AdapterPagerSale();
        clothesViewModel = new ViewModelProvider(requireActivity()).get(ClothesViewModel.class);
    }

    @Override
    public void onPause() {
        position = binding.vpager.getCurrentItem();
        super.onPause();
    }


    @Override
    public void initObserver() {
        clothesViewModel.getProductsSale(0);
        clothesViewModel.getProductsNew(0);

        Pagination<ProductResponse> productResponsePagination = new Pagination<>(requireActivity(),
                binding.mProgress,
                productAdapter,
                clothesViewModel.getProductsNew, size -> {
            clothesViewModel.getProductsNew(size);
        }).attach();
        binding.rcvSpmoi.addOnScrollListener(productResponsePagination);

        clothesViewModel.getProductsSale.observe(this, arrayListNetworkResult -> ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<ArrayList<ProductResponse>>() {
            @Override
            public void handleSuccess(ArrayList<ProductResponse> data) {
                adapterPagerSale.setData(data);
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
            }

            @Override
            public void handleLoading() {

            }
        }));
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }



    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void initView() {
        binding.vpager.setAdapter(adapterPagerSale);
        binding.rcvSpmoi.setAdapter(productAdapter);
        adapterPagerSale.setOnClickItemListener(item -> {
            Intent intent = new Intent(requireActivity(), DetailsActivity.class);
            intent.putExtra("idProduct", item.get_id());
            startActivity(intent);
        });
        productAdapter.setOnClickItemListener(item -> {
            Intent intent = new Intent(requireActivity(), DetailsActivity.class);
            intent.putExtra("idProduct", item.get_id());
            startActivity(intent);
        });

        binding.rcvSpmoi.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

            }
        });
        binding.vpager.setOffscreenPageLimit(3);
    }

    @Override
    public FragmentHomeBinding getViewBinding(View view) {
        return FragmentHomeBinding.bind(view);
    }
}