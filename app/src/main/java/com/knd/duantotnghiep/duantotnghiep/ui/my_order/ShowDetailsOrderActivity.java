package com.knd.duantotnghiep.duantotnghiep.ui.my_order;

import android.view.View;

import com.google.gson.Gson;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityShowDetailsOrderBinding;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderDTO;
import com.knd.duantotnghiep.duantotnghiep.models.OrderDetailDTO;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.OrderRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import java.util.List;
import java.util.Objects;

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
        binding.address.setText("Địa chỉ: " + orderResponse.getAddress());
        binding.name.setText("Tên: " + orderResponse.getName());
        binding.phoneNumber.setText("Số điện thoại: " + orderResponse.getPhoneNumber());
        if (Objects.equals(orderResponse.getStatus(), "Chờ xác nhận")) {
            binding.cancel.setVisibility(View.VISIBLE);
        } else {
            binding.cancel.setVisibility(View.GONE);
        }
        binding.cancel.setOnClickListener(view -> {
            orderRepository.cancelOrder(orderResponse.get_id());
        });


        orderRepository.getDetailOrdersShow(getIntent().getStringExtra("idOrder"));
        orderRepository.cancelOrder.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<MessageResponse>() {
            @Override
            public void handleSuccess(MessageResponse message) {
                ItemOrderFragment.idOrder=orderResponse.get_id();
                showMessage(message.getMessage()+"");
                finish();
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
            }

            @Override
            public void handleLoading() {

            }
        }));

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