package com.knd.duantotnghiep.duantotnghiep.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.AuthResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.TokenManager;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

//Xử lí logic và thao tác với cơ sở giữ liệu
@HiltViewModel//bắt buộc với viewmodel
class MainViewModel extends ViewModel {
    //Tạo MutableLiveData để quan sát khi login
    private final MutableLiveData<NetworkResult<MessageResponse>> _OnLogin = new MutableLiveData();
    //Tạo LiveData để trả về dữ liệu bên view tránh postvalue bừa
    final LiveData<NetworkResult<MessageResponse>> OnLogin = _OnLogin;

    //Khởi tạo không có contruce cần @Inject ở contructer class hoặc khai báo ngoài nhưng vẫn cần @Inject và để public
    private AuthResponse authRespo;
    private TokenManager tokenManager;

    @Inject //bắt buộc phải khởi tạo  với construct
    public MainViewModel(AuthResponse authRespo, TokenManager tokenManager) {
        this.authRespo = authRespo;
        this.tokenManager = tokenManager;
    }

//    void Login(SignInRequest loginRequest) {
//        authRespo.Login(new s("ok",TypeFeeling.SAD.name()));
////          NetworkResult.handleCallApi(authRespo.Login(loginRequest), _OnLogin, new ApiCallBack.OnSaveLocal() {
////              @Override
////              public void save(String value) {//Thực hiện save token nếu không save thì truyền và là null vào tham số
////                  tokenManager.saveToken(value);
////              }
////          });
//    }
}