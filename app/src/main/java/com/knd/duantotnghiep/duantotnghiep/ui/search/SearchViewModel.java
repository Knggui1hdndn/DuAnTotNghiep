package com.knd.duantotnghiep.duantotnghiep.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;
import com.knd.duantotnghiep.duantotnghiep.respository.UserResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchViewModel extends ViewModel {
    @Inject
    public SearchViewModel(UserResponse userResponse) {
        this.userResponse = userResponse;
        searchLiveData = userResponse.search;
    }

    public UserResponse userResponse;
    public LiveData<NetworkResult<List<SearchLocal>>> searchLiveData;

    public void searchProduct(String name, int skip) {
        userResponse.searchProduct(name, skip);
    }
}
