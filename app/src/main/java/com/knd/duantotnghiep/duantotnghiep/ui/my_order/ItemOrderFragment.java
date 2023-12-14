package com.knd.duantotnghiep.duantotnghiep.ui.my_order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.core.Pagination;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentItemOrderBinding;
import com.knd.duantotnghiep.duantotnghiep.models.EvaluateResponse;
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
    public static String idOrder = "";

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

    private Pagination orderResponsePagination;

    @Override
    public void initData() {

        myOrderAdapter = new MyOrderAdapter(idOrder -> {
            this.idOrder="";
            Intent intent = new Intent(requireActivity(), PaymentConfirmationActivity.class);
            intent.putExtra("order", idOrder);
            startActivity(intent);
        });
        myOrderAdapter.setOnClickItemListener(item -> {

        });
        binding.rcy.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        assert getArguments() != null;
        status = getArguments().getString("status");
        orderRepository.getOrdersByStatus(status, 0);

        orderResponsePagination = new Pagination<>(this,
                binding.mProgress,
                myOrderAdapter,
                orderRepository.getOrdersByStatus, size -> {
            orderRepository.getOrdersByStatus(status, size);
        }).attach();
        binding.rcy.addOnScrollListener(orderResponsePagination);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!idOrder.isEmpty()) {
            idOrder="";
          requireActivity().recreate();
        }
    }

    @Override
    public void initView() {
        binding.rcy.setAdapter(myOrderAdapter);
        myOrderAdapter.setOnClickItemListener(item -> {
            if (Objects.equals(item.getPayments(), "Chờ xác nhận")) {
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
