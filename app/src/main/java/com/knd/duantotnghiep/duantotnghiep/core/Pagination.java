package com.knd.duantotnghiep.duantotnghiep.core;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knd.duantotnghiep.duantotnghiep.models.EvaluateResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Pagination<O> extends RecyclerView.OnScrollListener {

    private ProgressBar progressBar;
    private boolean checkLoading = false;
    private BaseAdapter vbtBaseAdapter;

    public ArrayList<O> list = new ArrayList<O>();
    private PaginationCallback paginationCallback;
    private LifecycleOwner lifecycleOwner;
    private boolean isAttached = false;
    private boolean isAddAll = true;

    public ArrayList<O> getList() {
        return list;
    }

    public void setList(ArrayList<O> list) {
        this.list = list;
    }

    public boolean isAddAll() {
        return isAddAll;
    }

    public void setAddAll(boolean addAll) {
        isAddAll = addAll;
    }

    private LiveData<NetworkResult<List<O>>> liveData;


    public Pagination(LifecycleOwner lifecycleOwner, ProgressBar progressBar, BaseAdapter vbtBaseAdapter,
                      LiveData<NetworkResult<List<O>>> liveData, PaginationCallback paginationCallback) {
        this.progressBar = progressBar;
        this.lifecycleOwner = lifecycleOwner;
        this.vbtBaseAdapter = vbtBaseAdapter;
        this.liveData = liveData;
        this.paginationCallback = paginationCallback;

    }

    private <T> T extractObject(Object o, Class<T> responseType) {
        if (responseType.isInstance(o)) {
            return responseType.cast(o);
        }
        return null;
    }

    private Observer<NetworkResult<List<O>>> productObserver;

    public void onChanged(LiveData<NetworkResult<List<O>>> liveData) {
        productObserver = arrayListNetworkResult -> {
            ApiCallBack.handleResult(arrayListNetworkResult, new ApiCallBack.HandleResult<List<O>>() {
                @Override
                public void handleSuccess(List<O> data) {
                    if (!data.isEmpty() &&!list.contains(data.get(0)))   {

                        if (isAddAll()) {
                            list.addAll(data);
                        }
//
//                        Iterator<O> oIterator = list.iterator();
//                        boolean check=false;
//                        while (oIterator.hasNext()) {
//                            Object o = oIterator.next();
//                            ProductResponse productResponse = extractObject(o, ProductResponse.class);
//                            EvaluateResponse evaluateResponse = extractObject(o, EvaluateResponse.class);
//                            OrderResponse orderResponse = extractObject(o, OrderResponse.class);
//                            if (productResponse != null) {
//                                if (((List<ProductResponse>) list).stream().filter(r -> productResponse.get_id().equals(r.get_id())).count() > 1) {
//                                    oIterator.remove();
//                                    check=true;
//                                }
//                                continue;
//                            }
//                            if (evaluateResponse != null) {
//                                if (((List<EvaluateResponse>) list).stream().filter(r -> evaluateResponse.get_id().equals(r.get_id())).count() > 1) {
//                                    check=true;
//                                    oIterator.remove();
//                                }
//                                continue;
//                            }
//                            if (orderResponse != null) {
//                                if (((List<OrderResponse>) list).stream().filter(r -> orderResponse.get_id().equals(r.get_id())).count() > 1) {
//                                    check=true;
//                                    oIterator.remove();
//                                }
//                                continue;
//                            }
//                        }
//                        if (check) { Collections.reverse(list);}
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
            });

        };
        liveData.observe(lifecycleOwner, productObserver);

    }


//    private Set<String> extractUniqueIds(List<?> list) {
//        Set<String> ids = new HashSet<>();
//        Object instance = getInstanceClass();
//        if (instance instanceof ProductResponse) {
//            ids.addAll(((List<ProductResponse>) list).stream()
//                    .map(ProductResponse::get_id)
//                    .collect(Collectors.toSet()));
//        } else if (instance instanceof EvaluateResponse) {
//            ids.addAll(((List<EvaluateResponse>) list).stream()
//                    .map(EvaluateResponse::get_id)
//                    .collect(Collectors.toSet()));
//        } else if (instance instanceof OrderResponse) {
//            ids.addAll(((List<OrderResponse>) list).stream()
//                    .map(OrderResponse::get_id)
//                    .collect(Collectors.toSet()));
//        }
//        return ids;
//    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (isAttached) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            assert layoutManager != null;

            if (!checkLoading && layoutManager.findLastVisibleItemPosition() == list.size() - 1) {
                paginationCallback.onPagination(list.size());
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }


    public Pagination<O> attach() {
        isAttached = true;
        onChanged(liveData);
        return this;
    }

}
