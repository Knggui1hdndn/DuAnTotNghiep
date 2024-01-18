package com.knd.duantotnghiep.duantotnghiep.ui.details;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemImageEvaluateBinding;
import com.squareup.picasso.Picasso;

public class ImageEvaluateAdapter extends BaseAdapter<ItemImageEvaluateBinding, String> {
    @Override
    protected ItemImageEvaluateBinding getItemBinding(ViewGroup parent) {
        return ItemImageEvaluateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @Override
    protected void bind(String data, ItemImageEvaluateBinding binding) {
        System.out.printf("khang  " + data);
        if (data.startsWith("content://")) {
            Picasso.get().load(Uri.parse(data)).into(binding.img);
        } else {
            Log.e("Nguyen khang", data);
            Picasso.get().load(data).placeholder(R.drawable.baseline_image_24).into(binding.img);
        }

    }
}