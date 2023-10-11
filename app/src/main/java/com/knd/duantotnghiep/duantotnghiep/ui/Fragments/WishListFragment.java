package com.knd.duantotnghiep.duantotnghiep.ui.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;


public class WishListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    public WishListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WishListFragment newInstance() {
        WishListFragment fragment = new WishListFragment();

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
        return inflater.inflate(R.layout.fragment_wish_list, container, false);
    }
}