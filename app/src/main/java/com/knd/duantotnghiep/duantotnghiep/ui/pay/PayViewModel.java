package com.knd.duantotnghiep.duantotnghiep.ui.pay;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.PayQR;
import com.knd.duantotnghiep.duantotnghiep.respository.UserRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import dagger.hilt.android.lifecycle.HiltViewModel;

import javax.inject.Inject;

@HiltViewModel
public class PayViewModel extends ViewModel {
    private final UserRepository userResponse;

    @Inject
    public PayViewModel(UserRepository userResponse) {
        this.userResponse = userResponse;
        getOnGenerateQr = userResponse.generateQr;
    }

    public LiveData<NetworkResult<PayQR>> getOnGenerateQr;


    public void getOnGenerateQr(String order,Boolean recreate) {
        userResponse.generateQR(order,recreate);
    }
}
