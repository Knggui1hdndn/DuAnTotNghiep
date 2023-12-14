package com.knd.duantotnghiep.duantotnghiep.ui.search;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.knd.duantotnghiep.duantotnghiep.Dao.SearchDao;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.core.Pagination;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivitySearchBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.ui.details.DetailsActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchActivity extends BaseActivity<ActivitySearchBinding> {
    private SearchViewModel searchViewModel;
    public SearchAdapter searchAdapter;
    @Inject
    public SearchDao searchDao;
    private ArrayList<ProductResponse> localArrayList = new ArrayList<ProductResponse>();
    private Pagination<ProductResponse> productResponsePagination;

    @Override
    protected void initData() {
        searchAdapter = new SearchAdapter();
        //      localArrayList.addAll(searchDao.getListSearch());
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    protected void initView() {
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
                localArrayList.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchAdapter.setData(localArrayList);
                if (binding.search.getText().toString().length() > 0)
                    searchViewModel.searchProduct(editable.toString(), 0);
                else binding.mProgress.setVisibility(View.GONE);
            }
        });
        productResponsePagination = new Pagination<ProductResponse>(this,
                binding.mProgress,
                searchAdapter,
                searchViewModel.searchLiveData, size -> {
            searchViewModel.searchProduct(binding.search.getText().toString(), size);
        }).attach();
        binding.rcy.addOnScrollListener(productResponsePagination);
        searchAdapter.setOnClickItemListener(item -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("idProduct", item.get_id());
            startActivity(intent);
        });
        localArrayList = productResponsePagination.getList();
    }

    @Override
    protected void initObserver() {
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