package com.knd.duantotnghiep.duantotnghiep.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.knd.duantotnghiep.duantotnghiep.Dao.SearchDao;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivitySearchBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;
import com.knd.duantotnghiep.duantotnghiep.ui.sign_up.SignUpViewModel;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchActivity extends BaseActivity<ActivitySearchBinding> {
    private SearchViewModel searchViewModel;
    public SearchAdapter searchAdapter;
    @Inject
    public SearchDao searchDao;
    private final ArrayList<Object> localArrayList = new ArrayList<>();

    @Override
    protected void initData() {
        searchAdapter = new SearchAdapter();
        //      localArrayList.addAll(searchDao.getListSearch());
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    protected void initView() {
        handleUIVisibility(View.GONE, View.VISIBLE, View.INVISIBLE);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        binding.rcy.addItemDecoration(itemDecoration);
        binding.rcy.setAdapter(searchAdapter);
        Utils.showKeyboard(binding.search, this);
        binding.imageView.setOnClickListener(view -> {
            Utils.hideSoftKeyboard(this);
            finish();
        });
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // searchAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.search.getText().toString().length() > 0)
                    searchViewModel.searchProduct(editable.toString(), 0);
                else handleUIVisibility(View.GONE, View.VISIBLE, View.INVISIBLE);
            }
        });
        binding.rcy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int i = 0;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int total = layoutManager.getItemCount();
//                int currentLastItem = layoutManager.findLastVisibleItemPosition();
//                if (total - 1 == currentLastItem) {
//                    searchViewModel.searchProduct(binding.search.getText().toString(), i * 10);
//                    handleUIVisibility(View.VISIBLE, View.VISIBLE, View.INVISIBLE);
//                    i += 1;
//                }
            }
        });
    }

    @Override
    protected void initObserver() {
        searchViewModel.searchLiveData.observe(this, listNetworkResult -> ApiCallBack.handleResult(listNetworkResult, new ApiCallBack.HandleResult<List<ProductResponse>>() {
            @Override
            public void handleSuccess(List<ProductResponse> data) {
                handleUIVisibility(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
                localArrayList.clear();
                localArrayList.addAll(data);
                searchAdapter.setData(localArrayList);
            }

            @Override
            public void handleError(String error) {
                if (binding.search.getText().toString().length() > 0)
                    handleUIVisibility(View.VISIBLE, View.INVISIBLE, View.VISIBLE);
            }

            @Override
            public void handleLoading() {
                if (binding.search.getText().toString().length() > 0)
                    handleUIVisibility(View.VISIBLE, View.VISIBLE, View.INVISIBLE);
            }
        }));
    }

    private void handleUIVisibility(int frameLayoutVisibility, int progressVisibility, int imageViewVisibility) {
        binding.mFrameLayout.setVisibility(frameLayoutVisibility);
        binding.mProgress.setVisibility(progressVisibility);
        binding.refresh.setVisibility(imageViewVisibility);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.hideSoftKeyboard(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public ActivitySearchBinding getViewBinding() {
        return ActivitySearchBinding.inflate(getLayoutInflater());
    }
}