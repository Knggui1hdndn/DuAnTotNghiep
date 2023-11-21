package com.knd.duantotnghiep.duantotnghiep.utils;

import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;

public interface AdapterCallBack {
    interface OnClickItemListener<T> {
        void onClickItem(T item);
    }

    interface EvaluateAdapterCallback {
        void onYesClick( String idEvaluate) ;
        void onNoClick( String idEvaluate) ;
    }

    interface OnClickViewDetailsOrder {


        void onMinusClick(DetailOrderResponse detailOrderResponse);

        void onPlusClick(DetailOrderResponse detailOrderResponse);

        void unconditional(int position);//KHÔNG ĐỦ ĐIỀU KIỆN

        void onCloseClick(DetailOrderResponse detailOrderResponse);

        void onCheckedClick(DetailOrderResponse detailOrderResponse);
    }

}
