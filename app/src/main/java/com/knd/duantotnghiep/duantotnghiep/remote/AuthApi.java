package com.knd.duantotnghiep.duantotnghiep.remote;

import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SignUpRequest;
import com.knd.duantotnghiep.duantotnghiep.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {
    //login,logout,send otp,forgot password
    @POST("auth/signUp")
    Call<MessageResponse> signUpLocal(@Body SignUpRequest signUpRequest, @Query("otp") String otp);

    @POST("auth/google")
    Call<User> authenticateGoogle(@Header("Authorization_Google") String idToken);

    @POST("auth/sendOtp")
    Call<MessageResponse> sendOtp(@Header("Type") String type, @Query("address") String address);

    @POST("auth/verifyOtp")
    Call<MessageResponse>  verificationOTP(@Header("Type") String type, @Query("address") String address);

    @POST("auth/updatePassword")
    Call<MessageResponse>  updatePassword( @Query("address") String address , @Header("newPass")  String newPass);
}
