package com.knd.duantotnghiep.duantotnghiep.ui.details;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemSizeBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ProductDetail;

public class SizeAdapter extends BaseAdapter<ItemSizeBinding, ProductDetail> {
    @Override
    protected ItemSizeBinding getItemBinding(ViewGroup parent) {
        return ItemSizeBinding.inflate(LayoutInflater.from(parent.getContext()), null, false);
    }

    @Override
    protected void bind(ProductDetail data, ItemSizeBinding binding) {
        binding.txtSize.setText(data.getSize().split("")[0] );
        int background;
        int colorText;
        if (data.getClick()) {
            colorText=Color.WHITE;
            background = Color.parseColor("#DB0000");
        } else {
            background = Color.parseColor("#51BBBAEC");
            colorText=Color.BLACK;
        }
        binding.txtSize.setTextColor(colorText);
        binding.txtSize.setBackgroundColor(background);
    }
}

