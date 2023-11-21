package com.knd.duantotnghiep.duantotnghiep.ui.my_order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentItemOrderBinding;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.OrderRepository;
import com.knd.duantotnghiep.duantotnghiep.ui.pay.OrderConfirmation;
import com.knd.duantotnghiep.duantotnghiep.ui.pay.PaymentConfirmationActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.MyOrderAdapterCallback;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ItemOrderFragment extends BaseFragment<FragmentItemOrderBinding> {
    @Inject
    public OrderRepository orderRepository;

    public ItemOrderFragment() {
        super(R.layout.fragment_item_order);
    }

    private MyOrderAdapter myOrderAdapter;
    private String status;

    public static ItemOrderFragment getInstance(String status) {
        ItemOrderFragment fragment = new ItemOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        myOrderAdapter = new MyOrderAdapter(idOrder -> {
            Intent intent = new Intent(requireActivity(), PaymentConfirmationActivity.class);
            intent.putExtra("order", idOrder);
            startActivity(intent);
        });

        binding.rcy.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        assert getArguments() != null;
        status = getArguments().getString("status");
        orderRepository.getOrdersByStatus(status);
    }

    @Override
    public void initObserver() {
        orderRepository.getOrdersByStatus.observe(this, listNetworkResult -> {
            ApiCallBack.handleResult(listNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(List<OrderResponse> data) {
                    if (!data.isEmpty()) myOrderAdapter.setData(data);
                }

                @Override
                public void handleError(String error) {

                }

                @Override
                public void handleLoading() {

                }
            });
        });
    }

    @Override
    public void initView() {
        binding.rcy.setAdapter(myOrderAdapter);
        myOrderAdapter.setOnClickItemListener(item -> {
            if (Objects.equals(item.getPayments(), "Wait for confirmation") ) {
                Intent intent = new Intent(requireActivity(), PaymentConfirmationActivity.class);
                intent.putExtra("order", item.get_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public FragmentItemOrderBinding getViewBinding(View view) {
        return FragmentItemOrderBinding.bind(view);
    }
}
