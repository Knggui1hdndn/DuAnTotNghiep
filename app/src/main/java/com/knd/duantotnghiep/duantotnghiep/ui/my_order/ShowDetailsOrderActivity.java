package com.knd.duantotnghiep.duantotnghiep.ui.my_order;

import com.google.gson.Gson;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityShowDetailsOrderBinding;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderDTO;
import com.knd.duantotnghiep.duantotnghiep.models.OrderDetailDTO;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.OrderRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShowDetailsOrderActivity extends BaseActivity<ActivityShowDetailsOrderBinding> {
    private OrderResponse orderResponse;
    @Inject
    public OrderRepository orderRepository;
    public DetailsOrderAdapter detailsOrderAdapter;

    @Override
    protected void initData() {
        detailsOrderAdapter = new DetailsOrderAdapter();
        binding.recyclerView3.setAdapter(detailsOrderAdapter);
        setUpToolBar(binding.mToolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));
        orderResponse = new Gson().fromJson(getIntent().getStringExtra("order"), OrderResponse.class);
        binding.address.setText("Address: " + orderResponse.getAddress());
        binding.name.setText("Name: " + orderResponse.getName());
        binding.phoneNumber.setText("Phone Number: " + orderResponse.getPhoneNumber());
        orderRepository.getDetailOrdersShow(getIntent().getStringExtra("idOrder"));
        orderRepository.getDetailOrdersShow.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<OrderDTO>() {
            @Override
            public void handleSuccess(OrderDTO data) {
                detailsOrderAdapter.setData(data.getDetailsOrder());
                binding.totalPayment.setText("đ " + Utils.formatPrice(data.getTotalAmount()));
            }

            @Override
            public void handleError(String error) {

            }

            @Override
            public void handleLoading() {

            }
        }));

    }

    @Override
    public ActivityShowDetailsOrderBinding getViewBinding() {
        return ActivityShowDetailsOrderBinding.inflate(getLayoutInflater());
    }
}