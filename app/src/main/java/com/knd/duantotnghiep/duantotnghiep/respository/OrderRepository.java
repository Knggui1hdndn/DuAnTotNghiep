package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.CombinedOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.CountsOrderDetailsAndNoti;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.remote.OrderApi;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.List;

import javax.inject.Inject;

import retrofit2.http.Query;

public class OrderRepository {
    private OrderApi orderApi;

    @Inject
    public OrderRepository(OrderApi orderApi) {
        this.orderApi = orderApi;
    }

    private MutableLiveData<NetworkResult<MessageResponse>> _processDetailsOrder = new MutableLiveData<>();
    public LiveData<NetworkResult<MessageResponse>> processDetailsOrder = _processDetailsOrder;


    private MutableLiveData<NetworkResult<CountsOrderDetailsAndNoti>> _getCountNotiAndOrderDetails = new MutableLiveData<>();
    public LiveData<NetworkResult<CountsOrderDetailsAndNoti>> getCountNotiAndOrderDetails = _getCountNotiAndOrderDetails;


    private MutableLiveData<NetworkResult<List<DetailOrderResponse>>> _getDetailOrders = new MutableLiveData<>();
    public LiveData<NetworkResult<List<DetailOrderResponse>>> getDetailOrders = _getDetailOrders;

    private MutableLiveData<NetworkResult<DetailOrderResponse>> _updateDetailOrders = new MutableLiveData<>();
    public LiveData<NetworkResult<DetailOrderResponse>> updateDetailOrders = _updateDetailOrders;

    private MutableLiveData<NetworkResult<MessageResponse>> _deleteDetailsOrder = new MutableLiveData<>();
    public LiveData<NetworkResult<MessageResponse>> deleteDetailsOrder = _deleteDetailsOrder;

    private MutableLiveData<NetworkResult<MessageResponse>> _selectALl = new MutableLiveData<>();
    public LiveData<NetworkResult<MessageResponse>> selectALl = _selectALl;

    private MutableLiveData<NetworkResult<List<OrderResponse>>> _getOrdersByStatus = new MutableLiveData<>();
    public LiveData<NetworkResult<List<OrderResponse>>> getOrdersByStatus = _getOrdersByStatus;

    private MutableLiveData<NetworkResult<String>> _purchase = new MutableLiveData<>();
    public LiveData<NetworkResult<String>> purchase = _purchase;
    private MutableLiveData<NetworkResult<String>> _purchaseNow = new MutableLiveData<>();
    public LiveData<NetworkResult<String>>  purchaseNow = _purchaseNow;
    private MutableLiveData<NetworkResult<MessageResponse>> _checkBuyNow = new MutableLiveData<>();
    public LiveData<NetworkResult<MessageResponse>> checkBuyNow = _checkBuyNow;
    public void getOrdersByStatus(String status) {
        NetworkResult.handleCallApi(orderApi.getOrderByStatus(status), _getOrdersByStatus, null);
    }
    public void checkBuyNow( String idQuantity, int quantity) {
        NetworkResult.handleCallApi(orderApi.checkBuyNow(idQuantity,quantity), _checkBuyNow, null);
    }
    public void processDetailsOrder(DetailOrderRequest detailOrderRequest) {
        NetworkResult.handleCallApi(orderApi.processDetailsOrder(detailOrderRequest), _processDetailsOrder, null);
    }


    public void selectALl(Boolean isAll) {
        NetworkResult.handleCallApi(orderApi.selectALl(isAll), _selectALl, null);
    }


    public void getDetailOrders(Boolean isPay) {
        NetworkResult.handleCallApi(orderApi.getDetailOrders(isPay), _getDetailOrders, null);
    }

    public void getCountNotiAndOrderDetails() {
        NetworkResult.handleCallApi(orderApi.getCountNotiAndOrderDetails(), _getCountNotiAndOrderDetails, null);
    }

    public void updateDetailOrders(DetailOrderRequest detailOrderResponse) {
        NetworkResult.handleCallApi(orderApi.updateDetailOrders(detailOrderResponse), _updateDetailOrders, null);
    }

    public void deleteDetailsOrder(String idDetailsOrder) {
        NetworkResult.handleCallApi(orderApi.deleteDetailsOrder(idDetailsOrder), _deleteDetailsOrder, null);
    }

    public void purchase(String idOrder, CombinedOrderRequest combinedOrderRequest) {
        NetworkResult.handleCallApi(orderApi.purchase(idOrder, combinedOrderRequest), _purchase, null);
    }

}
