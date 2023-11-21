package com.knd.duantotnghiep.duantotnghiep.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.EvaluateRequest;
import com.knd.duantotnghiep.duantotnghiep.models.EvaluateResponse;
import com.knd.duantotnghiep.duantotnghiep.models.FeelingRequest;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.remote.OrderViewModel;
import com.knd.duantotnghiep.duantotnghiep.respository.EvaluateRepository;
import com.knd.duantotnghiep.duantotnghiep.respository.OrderRepository;
import com.knd.duantotnghiep.duantotnghiep.respository.ProductRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@HiltViewModel
public class DetailsViewModel extends OrderViewModel {
    private ProductRepository productRepository;
    private EvaluateRepository evaluateRepository;


    public LiveData<NetworkResult<ProductResponse>> getDetailsProduct;
    public LiveData<NetworkResult<List<EvaluateResponse>>> getEvaluates;
    public LiveData<NetworkResult<MessageResponse>> addFavourite;
    public LiveData<NetworkResult<MessageResponse>> deleteFavourite;
    public LiveData<NetworkResult<MessageResponse>> deleteEvaluate;
    public LiveData<NetworkResult<EvaluateResponse>> handelFeelingEvaluates;
    public LiveData<NetworkResult<EvaluateResponse>> addEvaluate;



    @Inject
    public DetailsViewModel(ProductRepository productRepository, EvaluateRepository evaluateRepository, OrderRepository orderRepository) {
        super(orderRepository);
        this.productRepository = productRepository;
        this.evaluateRepository = evaluateRepository;
        getDetailsProduct = productRepository.getDetailsProduct;
        getEvaluates = evaluateRepository.getEvaluates;
        addFavourite = productRepository.addFavourite;
        deleteFavourite = productRepository.deleteFavourite;
        deleteEvaluate = evaluateRepository.deleteEvaluate;
        addEvaluate = evaluateRepository.addEvaluates;
        handelFeelingEvaluates = evaluateRepository.handelFeelingEvaluates;
     }

    public void getDetailsProduct(String idProduct) {
        productRepository.getDetailsProduct(idProduct);
    }

    public void addEvaluates(String idProduct, RequestBody star,   RequestBody comment, List<MultipartBody.Part> images) {
        evaluateRepository.addEvaluates(idProduct, star,comment, images);
    }

    public void getEvaluates(String idProduct) {
        evaluateRepository.getEvaluates(idProduct);
    }

    public void deleteEvaluate(String idEvaluate) {
        evaluateRepository.deleteEvaluate(idEvaluate);
    }


    public void  handelFeelingEvaluates(String idEvaluate,FeelingRequest feelingRequest) {
        evaluateRepository. handelFeelingEvaluates(idEvaluate,feelingRequest);
    }

    public void addFavourite(String idProduct) {
        productRepository.addFavourite(idProduct);
    }

    public void deleteFavourite(String idProduct) {
        productRepository.deleteFavourite(idProduct);
    }
}
