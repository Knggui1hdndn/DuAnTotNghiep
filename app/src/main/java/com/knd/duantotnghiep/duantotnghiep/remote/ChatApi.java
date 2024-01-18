package com.knd.duantotnghiep.duantotnghiep.remote;

import com.knd.duantotnghiep.duantotnghiep.models.ChatMessage;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ChatApi {
    @GET("chat")
    Call<List<ChatMessage>> getListChats(@Query("id") String id );
    @Multipart
    @POST("chat")
    Call<List<String>> addImage( @Part List<MultipartBody.Part> partList);

}
