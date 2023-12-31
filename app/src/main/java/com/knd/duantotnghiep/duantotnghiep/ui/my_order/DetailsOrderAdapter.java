package com.knd.duantotnghiep.duantotnghiep.ui.my_order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemLayoutShowOrderBinding;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderDetailDTO;
import com.knd.duantotnghiep.duantotnghiep.ui.details.DetailsActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import okhttp3.internal.Util;

public class DetailsOrderAdapter extends BaseAdapter<ItemLayoutShowOrderBinding, OrderDetailDTO> {
    @Override
    protected ItemLayoutShowOrderBinding getItemBinding(ViewGroup parent) {
        return ItemLayoutShowOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(OrderDetailDTO data, ItemLayoutShowOrderBinding binding) {
        Picasso.get().load(data.getImage()).fit().into(binding.img);
        binding.txtTitle.setText(data.getName() + "(" + data.getSize() + ")");
        binding.txtSales.setText(data.getSale() + "%");
        binding.number.setText("Số lượng: " + data.getQuantity() + "");
        binding.txtTotalAmount.setText("đ " + Utils.formatPrice(data.getIntoMoney()) + "");
    }


}
