package com.knd.duantotnghiep.duantotnghiep.ui.dialog;

public interface ConfirmOtpCallback {
    interface ConfirmOtp {
        void onConfirmSuccess();
        void onConfirm(String otp);
        void onResent( );
    }
}
