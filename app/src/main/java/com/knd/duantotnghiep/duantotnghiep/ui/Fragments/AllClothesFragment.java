package com.knd.duantotnghiep.duantotnghiep.ui.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.adapter.ProductAdapter;
import com.knd.duantotnghiep.duantotnghiep.models.Product;
import com.knd.duantotnghiep.duantotnghiep.remote.ProductAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllClothesFragment extends Fragment {

    private RecyclerView recyclerView;

    private ProductAdapter adapter;

    private ArrayList<Product> list;


    public AllClothesFragment() {

    }


    public static AllClothesFragment newInstance() {
        AllClothesFragment fragment = new AllClothesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_clothes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recAllCothes);
        getProduct();

        super.onViewCreated(view, savedInstanceState);
    }
    public void getProduct(){
        ProductAPI.api.getProduct().enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if(response.body()!=null){
                    list = new ArrayList<>();
                    list = response.body();
                    adapter = new ProductAdapter(list);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}