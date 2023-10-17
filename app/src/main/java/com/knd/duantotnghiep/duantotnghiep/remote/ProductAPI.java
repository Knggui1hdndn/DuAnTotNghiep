package com.knd.duantotnghiep.duantotnghiep.remote;

import com.knd.duantotnghiep.duantotnghiep.models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ProductAPI {
    ProductAPI api = new Retrofit.Builder().baseUrl("http:192.168.1.181:8000").addConverterFactory(GsonConverterFactory.create()).build().create(ProductAPI.class);


    @GET("/pro")
    Call<ArrayList<Product>> getProduct();
}
