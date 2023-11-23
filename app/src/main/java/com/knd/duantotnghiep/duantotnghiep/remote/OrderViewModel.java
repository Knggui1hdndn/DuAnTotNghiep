package com.knd.duantotnghiep.duantotnghiep.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.EvaluateRepository;
import com.knd.duantotnghiep.duantotnghiep.respository.OrderRepository;
import com.knd.duantotnghiep.duantotnghiep.respository.ProductRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrderViewModel extends ViewModel {
    public LiveData<NetworkResult<List<DetailOrderResponse>>> getDetailOrders;
    public LiveData<NetworkResult<MessageResponse>> processDetailsOrder;
    private OrderRepository orderRepository;
    public LiveData<NetworkResult<MessageResponse>> checkBuyNow;
    @Inject
    public OrderViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        getDetailOrders = orderRepository.getDetailOrders;
        processDetailsOrder = orderRepository.processDetailsOrder;
        checkBuyNow = orderRepository.checkBuyNow;
    }

    public void checkBuyNow( String idQuantity, int quantity) {
        orderRepository.checkBuyNow(idQuantity,quantity);
    }
    public void processDetailsOrder(DetailOrderRequest detailOrderRequest) {
        orderRepository.processDetailsOrder(detailOrderRequest);
    }
}
