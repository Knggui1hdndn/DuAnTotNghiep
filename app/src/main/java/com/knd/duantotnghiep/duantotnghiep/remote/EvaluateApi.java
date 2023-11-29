package com.knd.duantotnghiep.duantotnghiep.remote;

import com.knd.duantotnghiep.duantotnghiep.models.EvaluateRequest;
import com.knd.duantotnghiep.duantotnghiep.models.EvaluateResponse;
import com.knd.duantotnghiep.duantotnghiep.models.Feeling;
import com.knd.duantotnghiep.duantotnghiep.models.FeelingRequest;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.TypeFeeling;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EvaluateApi {
    @Multipart
    @POST("evaluate/{idProduct}")
    Call<EvaluateResponse> addEvaluates(@Path("idProduct") String idProduct, @Part("star") RequestBody star, @Part("comment") RequestBody comment, @Part List<MultipartBody.Part> images);

    @GET("evaluate/{idProduct}")
    Call<List<EvaluateResponse>> getEvaluates(@Path("idProduct") String idProduct,@Query("skip") int skip);

    @DELETE("evaluate/{idEvaluate}")
    Call<MessageResponse> deleteEvaluates(@Path("idEvaluate") String idEvaluate);

    @POST("evaluate/{idEvaluate}/feeling")
    Call<EvaluateResponse> handelFeelingEvaluates(@Path("idEvaluate") String idEvaluate, @Body FeelingRequest feelingRequest);


}
