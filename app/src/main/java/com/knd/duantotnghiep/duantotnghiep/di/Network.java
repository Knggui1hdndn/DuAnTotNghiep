package com.knd.duantotnghiep.duantotnghiep.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knd.duantotnghiep.duantotnghiep.remote.AuthApi;

import com.knd.duantotnghiep.duantotnghiep.remote.AuthInterceptor;
import com.knd.duantotnghiep.duantotnghiep.remote.EvaluateApi;
import com.knd.duantotnghiep.duantotnghiep.remote.NotificationApi;
import com.knd.duantotnghiep.duantotnghiep.remote.OrderApi;
import com.knd.duantotnghiep.duantotnghiep.remote.ProductAPI;
import com.knd.duantotnghiep.duantotnghiep.remote.UserApi;
import com.knd.duantotnghiep.duantotnghiep.utils.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class Network {
    @Singleton
    @Provides
    public OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClientInterceptor(
            AuthInterceptor interceptor,
            OkHttpClient.Builder okHttpClientBuilder
    ) {
        return okHttpClientBuilder
                .addInterceptor(interceptor)
                .build();
    }

    @Singleton
    @Provides
    public Retrofit.Builder providesRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())

                .baseUrl(Constants.BASE_URL);
    }

    @Singleton
    @Provides
    public UserApi providesApiUser(OkHttpClient okHttpClient, Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.client(okHttpClient).build().create(UserApi.class);
    }

    @Singleton
    @Provides
    public NotificationApi providesApiNotificationApi(OkHttpClient okHttpClient, Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.client(okHttpClient).build().create(NotificationApi.class);
    }
    @Singleton
    @Provides
    public AuthApi providesApiAuth(Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.build().create(AuthApi.class);
    }
    @Singleton
    @Provides
    public ProductAPI providesApiProduct(OkHttpClient okHttpClient, Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.client(okHttpClient).build().create(ProductAPI.class);
    }

    @Singleton
    @Provides
    public EvaluateApi providesApiEvaluate(OkHttpClient okHttpClient, Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.client(okHttpClient).build().create(EvaluateApi.class);
    }

    @Singleton
    @Provides
    public OrderApi providesApiOrder(OkHttpClient okHttpClient, Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.client(okHttpClient).build().create(OrderApi.class);
    }
}
