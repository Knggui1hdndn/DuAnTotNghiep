package com.knd.duantotnghiep.duantotnghiep.core;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.knd.duantotnghiep.duantotnghiep.R;

import androidx.annotation.LayoutRes;

public abstract class BaseBottomSheetFrg<VB extends ViewBinding> extends BottomSheetDialogFragment {

    private int layout;
    protected VB binding;
    private FragmentActivity activity;

    public BaseBottomSheetFrg( int layout) {
         this.layout = layout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();
        binding = getViewBinding(view);

        getDialog().setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                bottomSheet.setBackgroundColor(Color.TRANSPARENT);
//                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) bottomSheet.getLayoutParams();
//                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//                bottomSheet.setLayoutParams(layoutParams);
            }
        });
        initData();
        initView();
        initObserve();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    protected abstract VB getViewBinding(View view);

    protected abstract void initView();

    protected void initObserve() {
    }

    protected void initData() {
    }

    protected VB getBinding() {
        return binding;
    }
}
