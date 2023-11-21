package com.knd.duantotnghiep.duantotnghiep.models;

public class CombinedOrderRequest {
   private OrderRequest orderRequest;
   private DetailOrderRequest detailOrderRequest;

    public CombinedOrderRequest(OrderRequest orderRequest, DetailOrderRequest detailOrderRequest) {
        this.orderRequest = orderRequest;
        this.detailOrderRequest = detailOrderRequest;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public DetailOrderRequest getDetailOrderRequest() {
        return detailOrderRequest;
    }

    public void setDetailOrderRequest(DetailOrderRequest detailOrderRequest) {
        this.detailOrderRequest = detailOrderRequest;
    }
}
