package com.knd.duantotnghiep.duantotnghiep.remote;


import com.knd.duantotnghiep.duantotnghiep.models.CombinedOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.CountsOrderDetailsAndNoti;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderDTO;
import com.knd.duantotnghiep.duantotnghiep.models.OrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface OrderApi {

    @POST("order/detail-order")
    Call<MessageResponse> processDetailsOrder(@Body DetailOrderRequest updateRequest);

    @GET("order/detail-order")
    Call<List<DetailOrderResponse>> getDetailOrders(@Query("isPay") Boolean isPay);
    @GET("order/detail-order")
    Call<OrderDTO> getDetailOrders(@Query("idOrder") String idOrder);
    @GET("order/count/orderDetails-notification")
    Call<CountsOrderDetailsAndNoti> getCountNotiAndOrderDetails();


    @DELETE("order/detail-order")
    Call<MessageResponse> deleteDetailsOrder(@Query("idDetailsOrder") String idDetailsOrder);

    @PUT("order/detail-order")
    Call<DetailOrderResponse> updateDetailOrders(@Body DetailOrderRequest detailOrderResponse);

    @PUT("order/detail-order/selectAll")
    Call<MessageResponse> selectALl(@Query("isAll") Boolean isAll);

    @GET("order/")
    Call<List<OrderResponse>> getOrderByStatus(@Query("status") String status,@Query("skip") int skip);

    @POST("order/checkBuyNow")
    Call<MessageResponse> checkBuyNow(@Query("idQuantity") String idQuantity, @Query("quantity") int quantity);

    @POST("order/purchase")
    Call<String> purchase(@Query("idOrder") String idOrder, @Body CombinedOrderRequest orderRequest);


}
