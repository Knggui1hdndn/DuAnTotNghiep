package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.PayQR;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.remote.UserApi;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class UserRepository {
    private UserApi userApi;

    @Inject
    public UserRepository(UserApi userApi) {
        this.userApi = userApi;
    }

    private MutableLiveData<NetworkResult<PayQR>> _generateQr = new MutableLiveData<>(); //
    public LiveData<NetworkResult<PayQR>> generateQr = _generateQr;

    private MutableLiveData<NetworkResult<List<ProductResponse>>> _search = new MutableLiveData<>(); //
    public LiveData<NetworkResult<List<ProductResponse>>> search = _search;

    private MutableLiveData<NetworkResult<User>> _editUser = new MutableLiveData<>(); //
    public LiveData<NetworkResult<User>> editUser = _editUser;

    public void generateQR(String orderResponse) {
        NetworkResult.handleCallApi(userApi.generateQR(orderResponse), _generateQr, null);
    }

    public void searchProduct(String name, int skip) {
        NetworkResult.handleCallApi(userApi.searchProduct(name, skip), _search, null);
    }

    public void editUser(MultipartBody.Part avatar,
                              RequestBody name,
                              RequestBody address,
                              RequestBody phoneNumber,
                              RequestBody email) {
        NetworkResult.handleCallApi(userApi.editUser(avatar, name, address, phoneNumber, email), _editUser, null);
    }
}
