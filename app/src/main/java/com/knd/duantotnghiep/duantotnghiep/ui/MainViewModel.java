package com.knd.duantotnghiep.duantotnghiep.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.CountsOrderDetailsAndNoti;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.AuthRepository;
import com.knd.duantotnghiep.duantotnghiep.respository.OrderRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.TokenManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class MainViewModel extends ViewModel {

    LiveData<NetworkResult<List<DetailOrderResponse>>> getDetailOrders;
    LiveData<NetworkResult<CountsOrderDetailsAndNoti>> getCountNotiAndOrderDetails;

    private OrderRepository orderRepository;

    @Inject //bắt buộc phải khởi tạo  với construct
    public MainViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        getDetailOrders = orderRepository.getDetailOrders;
        getCountNotiAndOrderDetails = orderRepository.getCountNotiAndOrderDetails;
    }

    public void getDetailOrders() {
        orderRepository.getDetailOrders(false);
    }


    public void getCountNotiAndOrderDetails() {
        orderRepository.getCountNotiAndOrderDetails();
    }

}