package com.knd.duantotnghiep.duantotnghiep.ui.shopping_bag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.CombinedOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderRequest;
import com.knd.duantotnghiep.duantotnghiep.respository.OrderRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShoppingBagViewModel extends ViewModel {

    public LiveData<NetworkResult<List<DetailOrderResponse>>> getDetailOrders;
    public LiveData<NetworkResult<DetailOrderResponse>> updateDetailOrders;
    public LiveData<NetworkResult<MessageResponse>> selectALl;
    public LiveData<NetworkResult<MessageResponse>> deleteDetailsOrder;
    public LiveData<NetworkResult<String>> purchase;

    private OrderRepository orderRepository;

    @Inject //bắt buộc phải khởi tạo  với construct
    public ShoppingBagViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        getDetailOrders = orderRepository.getDetailOrders;
        selectALl = orderRepository.selectALl;
        deleteDetailsOrder = orderRepository.deleteDetailsOrder;
        updateDetailOrders = orderRepository.updateDetailOrders;
        purchase = orderRepository.purchase;
    }

    public void selectALl(Boolean isAll) {
        orderRepository.selectALl(isAll);
    }

    public void getDetailOrders() {
        orderRepository.getDetailOrders(false);
    }

    public void updateDetailOrders(DetailOrderRequest detailOrderResponse) {
        orderRepository.updateDetailOrders(detailOrderResponse);
    }

    public void deleteDetailsOrder(String idDetailsOrder) {
        orderRepository.deleteDetailsOrder(idDetailsOrder);
    }

    public void purchase(String idOrder, CombinedOrderRequest combinedOrderRequest) {
        orderRepository.purchase(idOrder, combinedOrderRequest);
    }


}
