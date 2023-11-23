package com.knd.duantotnghiep.duantotnghiep.remote;

import com.knd.duantotnghiep.duantotnghiep.models.CountsOrderDetailsAndNoti;
import com.knd.duantotnghiep.duantotnghiep.models.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface NotificationApi {
    @GET("notification/")
    Call<List<Notification>> getNotification();

    @PUT("notification/")
    Call<Notification> updateNotification(@Query("_id") String _idNotification);
}
