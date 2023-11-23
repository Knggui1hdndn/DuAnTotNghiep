package com.knd.duantotnghiep.duantotnghiep.ui.details;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemColorBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ImageProduct;
import com.knd.duantotnghiep.duantotnghiep.models.ImageQuantity;
import com.squareup.picasso.Picasso;

public class ColorAdapter extends BaseAdapter<ItemColorBinding, ImageQuantity> {
    @Override
    protected ItemColorBinding getItemBinding(ViewGroup parent) {
        return ItemColorBinding.inflate(LayoutInflater.from(parent.getContext()), null, false);
    }

    @Override
    protected void bind(ImageQuantity data, ItemColorBinding binding) {
         Picasso.get().load(data.getImageProduct().getImage()).error(R.drawable.img_1).into(binding.img);
        binding.cardView.setCardBackgroundColor(data.getClick() ? Color.RED : Color.parseColor("#51BBBAEC"));
    }
}
