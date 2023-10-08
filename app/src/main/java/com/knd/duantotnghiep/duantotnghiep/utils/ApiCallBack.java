package com.knd.duantotnghiep.duantotnghiep.utils;

import android.util.Log;

public interface ApiCallBack {
    interface OnSaveLocal {
        void save(String value);
    }

    interface HandleResult<T> {
        void handleSuccess(T data);

        void handleError(String error);

        void handleLoading();
    }

    public static <T> void handleResult(NetworkResult<T> result, HandleResult<T> handler) {


        if (result instanceof NetworkResult.Success) {
            Log.d("instanceof3",result.data.toString());
            handler.handleSuccess(result.data);
        } else if (result instanceof NetworkResult.Error) {
            Log.d("instanceof2",result.toString());

            handler.handleError(result.message);
        } else if (result instanceof NetworkResult.Loading) {
            Log.d("instanceof1",result.toString());

            handler.handleLoading();
        }
    }
}
