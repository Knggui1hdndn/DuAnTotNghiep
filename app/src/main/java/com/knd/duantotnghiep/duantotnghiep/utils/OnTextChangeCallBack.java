package com.knd.duantotnghiep.duantotnghiep.utils;

import com.google.android.material.textfield.TextInputLayout;

public interface OnTextChangeCallBack {
    interface OnTextChange {
        public void onTextChanged(String text, TextInputLayout textInputLayout);
    }

    public void onTextChange(OnTextChange onTextChange, TextInputLayout... textInputLayout);
}
