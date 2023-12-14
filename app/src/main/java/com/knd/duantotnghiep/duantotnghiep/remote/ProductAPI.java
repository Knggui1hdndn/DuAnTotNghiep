package com.knd.duantotnghiep.duantotnghiep.remote;

import com.knd.duantotnghiep.duantotnghiep.models.CategoryResponse;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductAPI {


    @GET("products/")
    Call<List<ProductResponse>> getProducts(@Query("skip") Integer skip);

    @POST("products/favourite/{idProduct}")
    Call<MessageResponse> addFavourite(@Path("idProduct") String idProduct);

    @DELETE("products/favourite/{idProduct}")
    Call<MessageResponse> deleteFavourite(@Path("idProduct") String idProduct);

    @GET("products/favourite/{idProduct}")
    Call<ArrayList<ProductResponse>> getFavourites();

    @GET("products/{idCategory}")
    Call<List<ProductResponse>> getProductByCategory(@Path("idCategory") String idCata,@Query("skip") Integer skip);

    @GET("products/categories")
    Call<CategoryResponse> getCategories();

    @GET("products/details/{idProduct}")
    Call<ProductResponse> getDetailsProduct(@Path("idProduct")String idProduct);
    @GET("products/sale")
    Call<ArrayList<ProductResponse>> getProductsSale(@Query("skip") int skip);

    @GET("products/new")
    Call<List<ProductResponse>> getProductsNew(@Query("skip") int skip);

}
