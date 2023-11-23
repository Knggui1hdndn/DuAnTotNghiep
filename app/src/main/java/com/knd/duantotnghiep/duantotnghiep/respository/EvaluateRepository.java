package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.EvaluateRequest;
import com.knd.duantotnghiep.duantotnghiep.models.EvaluateResponse;
import com.knd.duantotnghiep.duantotnghiep.models.FeelingRequest;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.remote.AuthApi;
import com.knd.duantotnghiep.duantotnghiep.remote.EvaluateApi;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.TokenManager;

import java.util.List;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class EvaluateRepository {
    private EvaluateApi auth;

    private final MutableLiveData<NetworkResult<List<EvaluateResponse>>> _getEvaluates = new MutableLiveData<>();
    public LiveData<NetworkResult<List<EvaluateResponse>>> getEvaluates = _getEvaluates;

    private final MutableLiveData<NetworkResult<EvaluateResponse>> _addEvaluates = new MutableLiveData<>();
    public LiveData<NetworkResult<EvaluateResponse>> addEvaluates = _addEvaluates;



    private final MutableLiveData<NetworkResult<EvaluateResponse>> _handelFeelingEvaluates = new MutableLiveData<>();
    public LiveData<NetworkResult<EvaluateResponse>>  handelFeelingEvaluates = _handelFeelingEvaluates;

    private final MutableLiveData<NetworkResult<MessageResponse>> _deleteEvaluate = new MutableLiveData<>();
    public LiveData<NetworkResult<MessageResponse>> deleteEvaluate = _deleteEvaluate;

    @Inject
    public EvaluateRepository(EvaluateApi auth) {
        this.auth = auth;
    }

    public void handelFeelingEvaluates(String idEvaluate, FeelingRequest feelingRequest) {
        NetworkResult.handleCallApi(auth.handelFeelingEvaluates(idEvaluate, feelingRequest), _handelFeelingEvaluates, null);
    }


    public void addEvaluates(String idProduct, RequestBody star,   RequestBody comment, List<MultipartBody.Part> images) {
        NetworkResult.handleCallApi(auth.addEvaluates(idProduct, star,comment, images), _addEvaluates, null);
    }

    public void getEvaluates(String idProduct) {
        NetworkResult.handleCallApi(auth.getEvaluates(idProduct), _getEvaluates, null);
    }

    public void deleteEvaluate(String idEvaluate) {
        NetworkResult.handleCallApi(auth.deleteEvaluates(idEvaluate), _deleteEvaluate, null);
    }
}
