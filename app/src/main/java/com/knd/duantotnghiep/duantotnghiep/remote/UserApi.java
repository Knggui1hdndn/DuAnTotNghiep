package com.knd.duantotnghiep.duantotnghiep.remote;

import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.PayQR;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;
import com.knd.duantotnghiep.duantotnghiep.models.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserApi {
    @GET("users/generate/QR")
    Call<PayQR> generateQR(@Query("idOrder") String idOrder,@Query("recreate") Boolean recreate);

    @GET("users/search")
    Call<List<ProductResponse>> searchProduct(@Query("name") String name, @Query("skip") int skip);

    @Multipart
    @POST("users/editaccount")
    Call<User> editUser(
            @Part MultipartBody.Part avatar,
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("poneNumber") RequestBody phoneNumber,
            @Part("email") RequestBody email);
}
