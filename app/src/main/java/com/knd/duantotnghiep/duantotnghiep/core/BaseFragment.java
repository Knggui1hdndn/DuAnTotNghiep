package com.knd.duantotnghiep.phonetrackerlocation.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    private VB _binding;
    private int requestCode = 0;

    @LayoutRes
    private final int layout;

    public BaseFragment(@LayoutRes int layout) {
        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout, container, false);
        _binding = getViewBinding(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        initObserver();
    }

    public abstract void initObserver();

    public abstract VB getViewBinding(View view);

    public void initView() {}

    public void initData() {}

    public VB getBinding() {
        return _binding;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _binding = null;
    }
}
