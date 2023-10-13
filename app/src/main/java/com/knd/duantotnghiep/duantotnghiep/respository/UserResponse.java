package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.Order;
import com.knd.duantotnghiep.duantotnghiep.models.PayQR;
import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;
import com.knd.duantotnghiep.duantotnghiep.remote.AuthApi;
import com.knd.duantotnghiep.duantotnghiep.remote.UserApi;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.http.Query;

public class UserResponse {
    private UserApi userApi;

  @Inject
  public UserResponse(UserApi userApi) {
        this.userApi = userApi;
    }

    private MutableLiveData<NetworkResult<PayQR>> _generateQr = new MutableLiveData<>(); //
    public LiveData<NetworkResult<PayQR>> generateQr =  _generateQr;

    private MutableLiveData<NetworkResult<List<SearchLocal>>> _search = new MutableLiveData<>(); //
    public LiveData<NetworkResult<List<SearchLocal>>>  search =  _search;
    public void generateQR(Order order){
        NetworkResult.handleCallApi(userApi.generateQR(order),_generateQr,null);
    }

    public void searchProduct( String name, int skip){
        NetworkResult.handleCallApi(userApi.searchProduct(name,skip),_search,null);
    }
}
