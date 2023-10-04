package com.knd.duantotnghiep.duantotnghiep.utils;

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
            handler.handleSuccess(result.data);
        } else if (result instanceof NetworkResult.Error) {
            handler.handleError(result.message);
        } else if (result instanceof NetworkResult.Loading) {
            handler.handleLoading();
        }
    }
}
