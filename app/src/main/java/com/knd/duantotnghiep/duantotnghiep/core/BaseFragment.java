package com.knd.duantotnghiep.duantotnghiep.core;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    protected VB binding;
    private int requestCode = 0;

    @LayoutRes
    private final int layout;

    public BaseFragment(@LayoutRes int layout) {
        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout, container, false);
        binding = getViewBinding(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("palsplad", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        initObserver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("palsplad", "onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("palsplad", "onResume");
    }

    public void showMessage(String string) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show();
    }

    public abstract void initObserver();

    public abstract VB getViewBinding(View view);

    public void initView() {
    }

    public void initData() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
