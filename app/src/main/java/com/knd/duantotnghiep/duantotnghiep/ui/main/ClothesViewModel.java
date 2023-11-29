package com.knd.duantotnghiep.duantotnghiep.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.CategoryResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.respository.ProductRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ClothesViewModel extends ViewModel {
    private ProductRepository productRepository;

    public void getProduct() {
        productRepository.getProducts();
    }
    public void getProductsNew(int skip) {
        productRepository.getProductsNew(skip);
    }
    public void getProductsSale(int skip) {
        productRepository.getProductsSale(skip);
    }
    public void getFavourites() {
        productRepository.getFavourites();
    }

    public void getProductByCategory(String idCategory) {productRepository.getProductByCategory(idCategory);}

    public void getCategories() {
        productRepository.getCategories();
    }

    public LiveData<NetworkResult<ArrayList<ProductResponse>>> getProducts;
    public LiveData<NetworkResult<ArrayList<ProductResponse>>> getProductByCategory;
    public LiveData<NetworkResult<CategoryResponse>> getCategories;
    public LiveData<NetworkResult<List<ProductResponse>>> getProductsNew;
    public LiveData<NetworkResult<ArrayList<ProductResponse>>> getFavourites;
    public LiveData<NetworkResult<ArrayList<ProductResponse>>> getProductsSale;

    @Inject
    public ClothesViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
        getProducts = productRepository.getProducts;
        getProductByCategory = productRepository.getProductByCategory;
        getCategories = productRepository.getCategories;
        getProductsSale = productRepository.getProductsSale;
        getProductsNew = productRepository.getProductsNew;
        getFavourites = productRepository.getFavourites;
    }


}
