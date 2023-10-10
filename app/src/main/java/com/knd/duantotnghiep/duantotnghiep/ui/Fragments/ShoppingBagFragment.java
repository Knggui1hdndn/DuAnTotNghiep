package com.knd.duantotnghiep.duantotnghiep.ui.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;


public class ShoppingBagFragment extends Fragment {



    public ShoppingBagFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ShoppingBagFragment newInstance() {

        ShoppingBagFragment fragment = new ShoppingBagFragment();
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
        return inflater.inflate(R.layout.fragment_shopping_bag, container, false);
    }
}