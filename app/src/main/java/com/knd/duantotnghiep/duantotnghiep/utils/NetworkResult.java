package com.knd.duantotnghiep.duantotnghiep.utils;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkResult<T> {
    final T data;
    final String message;

    public static <T> void handleCallApi(Call<T> call, MutableLiveData<NetworkResult<T>> liveData, ApiCallBack.OnSaveLocal onSaveLocal) {
        liveData.postValue(new NetworkResult.Loading<>());
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    T data = response.body();
                    if (onSaveLocal != null && response.headers().size() > 0) {
                        String auth = response.headers().get("Authorization");
                        onSaveLocal.save(auth);
                    }

                    liveData.postValue(new NetworkResult.Success<>(data));
                } else if (response.errorBody() != null) {
                    try {
                        String errorBody = response.errorBody().string();
                        MessageResponse messageResponse = new Gson().fromJson(errorBody, MessageResponse.class);
                        liveData.postValue(new NetworkResult.Error(messageResponse.getMessage()));
                    } catch (IOException e) {
                        liveData.postValue(new NetworkResult.Error("Error parsing error response"));
                    }
                } else {
                    liveData.postValue(new NetworkResult.Error("Something Went Wrong"));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                liveData.postValue(new NetworkResult.Error(t.getLocalizedMessage()));
            }
        });
    }


    private NetworkResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static final class Success<T> extends NetworkResult<T> {
        public Success(T data) {
            super(data, null);
        }
    }

    public static final class Error<T> extends NetworkResult<T> {
        public Error(String message) {
            super(null, message);
        }
    }

    public static final class Loading<T> extends NetworkResult<T> {
        private Loading() {
            super(null, null);
        }
    }

}
