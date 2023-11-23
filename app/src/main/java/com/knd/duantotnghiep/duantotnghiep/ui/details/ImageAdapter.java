package com.knd.duantotnghiep.duantotnghiep.ui.details;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemImageBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ImageQuantity;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter<ItemImageBinding, ImageQuantity> {
    @Override
    protected ItemImageBinding getItemBinding(ViewGroup parent) {
        return ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @Override
    protected void bind(ImageQuantity data, ItemImageBinding binding) {
        Log.d("ImageAdapter", "ok");
      Picasso.get().load(data.getImageProduct().getImage()).fit().into(binding.img);
      //  binding.img.setImageResource(R.drawable.img_1);
    }
}