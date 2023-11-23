package com.knd.duantotnghiep.duantotnghiep.ui.details;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemImageEvaluateBinding;
import com.squareup.picasso.Picasso;

class ImageEvaluateAdapter extends BaseAdapter<ItemImageEvaluateBinding, String> {
    @Override
    protected ItemImageEvaluateBinding getItemBinding(ViewGroup parent) {
        return ItemImageEvaluateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @Override
    protected void bind(String data, ItemImageEvaluateBinding binding) {
        if (data.startsWith("content://")) {
            Picasso.get().load(Uri.parse(data)) .into(binding.img);
        }else {
            Picasso.get().load(data).into(binding.img);
        }

    }
}