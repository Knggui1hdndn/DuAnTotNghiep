package com.knd.duantotnghiep.duantotnghiep.core;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> extends RecyclerView.OnScrollListener {
    private ProgressBar progressBar;
    private boolean checkLoading = false;
    private BaseAdapter vbtBaseAdapter;
    public ArrayList<T> list = new ArrayList<>();
    private PaginationCallback paginationCallback;
    private LifecycleOwner lifecycleOwner;
    private boolean isAttached = false;
    private boolean isAddAll = true;

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public boolean isAddAll() {
        return isAddAll;
    }

    public void setAddAll(boolean addAll) {
        isAddAll = addAll;
    }

    private LiveData<NetworkResult<List<T>>> liveData;

    public Pagination(LifecycleOwner lifecycleOwner, ProgressBar progressBar, BaseAdapter vbtBaseAdapter,
                      LiveData<NetworkResult<List<T>>> liveData, PaginationCallback paginationCallback) {
        this.progressBar = progressBar;
        this.lifecycleOwner = lifecycleOwner;
        this.vbtBaseAdapter = vbtBaseAdapter;
        this.liveData = liveData;
        this.paginationCallback = paginationCallback;
    }

    public void onChanged(LiveData<NetworkResult<List<T>>> liveData) {
        liveData.observe(lifecycleOwner, arrayListNetworkResult -> ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<List<T>>() {
            @Override
            public void handleSuccess(List<T> data) {

                if (!data.isEmpty() ) {
                    if (isAddAll()) {
                        list.addAll(data);
                    }
                    vbtBaseAdapter.setData(isAddAll() ? list : data);
                }
                checkLoading = false;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void handleError(String error) {
                checkLoading = false;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void handleLoading() {
                checkLoading = true;
                progressBar.setVisibility(View.VISIBLE);
            }
        }));

    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (isAttached) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            assert layoutManager != null;
            Log.d("sìkalfklsf",checkLoading+
                    ""+layoutManager.findLastVisibleItemPosition()+"sscccs"+list.size()+"    "+(!checkLoading && layoutManager.findLastVisibleItemPosition() == list.size() -1));
            if (!checkLoading && layoutManager.findLastVisibleItemPosition() == list.size() -1) {
                paginationCallback.onPagination(list.size());
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }


    public Pagination<T> attach() {
        isAttached = true;
        onChanged(liveData);
        return this;
    }

}
