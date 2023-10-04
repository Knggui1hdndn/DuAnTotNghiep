package com.knd.duantotnghiep.duantotnghiep.di;

import com.knd.duantotnghiep.duantotnghiep.remote.AuthApi;

import com.knd.duantotnghiep.duantotnghiep.remote.AuthInterceptor;
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

@Module
@InstallIn(SingletonComponent.class)
public class Network {
    @Singleton
    @Provides
    public OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);
    }


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
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL);
    }
    @Singleton
    @Provides
    public UserApi providesApiUser(OkHttpClient okHttpClient, Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.client(okHttpClient).build().create(UserApi.class);
    }
    @Singleton
    @Provides
    public AuthApi providesApiAuth(Retrofit.Builder retrofitBuilder) {
        return retrofitBuilder.build().create(AuthApi.class);
    }
}
