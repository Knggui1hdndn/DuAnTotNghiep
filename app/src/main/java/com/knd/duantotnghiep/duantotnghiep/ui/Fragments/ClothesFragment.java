package com.knd.duantotnghiep.duantotnghiep.ui.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.adapter.ProductAdapter;
import com.knd.duantotnghiep.duantotnghiep.adapter.ViewPageAdapter;
import com.knd.duantotnghiep.duantotnghiep.models.Product;
import com.knd.duantotnghiep.duantotnghiep.remote.ProductAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClothesFragment extends Fragment {


    private TabLayout tabLayout;

    private ViewPager2 viewPager2;

    private ViewPageAdapter viewPageAdapter;




    public ClothesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ClothesFragment newInstance() {
        ClothesFragment fragment = new ClothesFragment();

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
        return inflater.inflate(R.layout.fragment_clothes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.idTabLayout);
        viewPager2 = view.findViewById(R.id.idViewPager);
        viewPageAdapter =new ViewPageAdapter(getActivity());
        viewPager2.setAdapter(viewPageAdapter);
        TabLayoutMediator mediator =new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("All");
                        break;
                    case 1:
                        tab.setText("Polo and T-shirt");
                        break;
                    case 2:
                        tab.setText("Suit");
                        break;
                    case 3:
                        tab.setText("Out wear");
                        break;
                }
            }
        });
        mediator.attach();
    }

}