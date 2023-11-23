package com.knd.duantotnghiep.duantotnghiep.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class TextChangeListener implements OnTextChangeCallBack {

    @Override
    public void onTextChange(OnTextChange onTextChange, TextInputLayout... textInputLayout) {
          for (int i = 0; i < textInputLayout.length; i++){
             textInputLayout[i].getEditText().addTextChangedListener(new TextWatcher() {
                 @Override
                 public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 }

                 @Override
                 public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                     onTextChange.onTextChanged(charSequence.toString(),textInputLayout[i] );
                 }

                 @Override
                 public void afterTextChanged(Editable editable) {

                 }
             });
         }
    }
}