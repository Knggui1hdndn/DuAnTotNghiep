package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.CategoryResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.remote.ProductAPI;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.http.Query;

public class ProductRepository {//lớp trung gian giao tiếp giữa APi or local với viemModel
    private ProductAPI productAPI;
    private final MutableLiveData<NetworkResult<ArrayList<ProductResponse>>> _getProducts = new MutableLiveData();
    public LiveData<NetworkResult<ArrayList<ProductResponse>>> getProducts = _getProducts;
    private final MutableLiveData<NetworkResult<ArrayList<ProductResponse>>> _getProductByCategory = new MutableLiveData();
    public LiveData<NetworkResult<ArrayList<ProductResponse>>> getProductByCategory = _getProductByCategory;

    private final MutableLiveData<NetworkResult<CategoryResponse>> _getCategories = new MutableLiveData<>();
    public LiveData<NetworkResult<CategoryResponse>> getCategories = _getCategories;

    private final MutableLiveData<NetworkResult<ProductResponse>> _getDetailsProduct = new MutableLiveData<>();
    public LiveData<NetworkResult<ProductResponse>> getDetailsProduct = _getDetailsProduct;

    private final MutableLiveData<NetworkResult<MessageResponse>> _addFavourite = new MutableLiveData<>();
    public LiveData<NetworkResult<MessageResponse>> addFavourite = _addFavourite;


    private final MutableLiveData<NetworkResult<MessageResponse>> _deleteFavourite = new MutableLiveData<>();
    public LiveData<NetworkResult<MessageResponse>> deleteFavourite = _deleteFavourite;

    private final MutableLiveData<NetworkResult<ArrayList<ProductResponse>>> _getFavourites = new MutableLiveData<>();
    public LiveData<NetworkResult<ArrayList<ProductResponse>>> getFavourites = _getFavourites;

    private final MutableLiveData<NetworkResult<ArrayList<ProductResponse>>> _getProductsSale = new MutableLiveData<>();
    public LiveData<NetworkResult<ArrayList<ProductResponse>>> getProductsSale = _getProductsSale;

    private final MutableLiveData<NetworkResult<List<ProductResponse>>> _getProductsNew = new MutableLiveData<>();
    public LiveData<NetworkResult<List<ProductResponse>>> getProductsNew = _getProductsNew;

    @Inject
    public ProductRepository(ProductAPI productAPI) {
        this.productAPI = productAPI;
    }

    public void addFavourite(String idProduct) {
        NetworkResult.handleCallApi(productAPI.addFavourite(idProduct), _addFavourite, null);
    }

    public void deleteFavourite(String idProduct) {
        NetworkResult.handleCallApi(productAPI.deleteFavourite(idProduct), _deleteFavourite, null);
    }

    public void getFavourites() {
        NetworkResult.handleCallApi(productAPI.getFavourites(), _getFavourites, null);
    }

    public void getProducts() {
        NetworkResult.handleCallApi(productAPI.getProducts(), _getProducts, null);
    }

    public void getProductByCategory(String idCategory) {
        NetworkResult.handleCallApi(productAPI.getProductByCategory(idCategory), _getProductByCategory, null);
    }

    public void getCategories() {
        NetworkResult.handleCallApi(productAPI.getCategories(), _getCategories, null);
    }

    public void getDetailsProduct(String idProduct ) {
        NetworkResult.handleCallApi(productAPI.getDetailsProduct(idProduct ), _getDetailsProduct, null);
    }

    public void getProductsSale(int skip) {
        NetworkResult.handleCallApi(productAPI.getProductsSale(skip), _getProductsSale, null);
    }

    public void getProductsNew(int skip) {
        NetworkResult.handleCallApi(productAPI.getProductsNew(skip), _getProductsNew, null);
    }
}