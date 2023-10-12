package com.knd.duantotnghiep.duantotnghiep.ui.Sign;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiservice = new Retrofit.Builder()
            .baseUrl("https:192.168.1.231/auth/LoginUserhttps:192.168.1.231/auth/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);

    @GET("user")
    Call<List<User>> getListUsers(@Query("email") String email,
                                @Query("password") String password);

}
