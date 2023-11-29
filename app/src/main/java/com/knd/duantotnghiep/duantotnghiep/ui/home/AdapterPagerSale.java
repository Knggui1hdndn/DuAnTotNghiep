package com.knd.duantotnghiep.duantotnghiep.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemProductSaleBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPagerSale extends BaseAdapter<ItemProductSaleBinding, ProductResponse> {


    @Override
    protected ItemProductSaleBinding getItemBinding(ViewGroup parent) {
        return ItemProductSaleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @Override
    protected void bind(ProductResponse data, ItemProductSaleBinding binding) {
        try {
            Picasso.get().load(Utils.getImageProduct(data)).fit().into(binding.imgProduct);
            double price = data.getPrice();
            double sale = data.getSale();
            double discountedPrice = price * (1 - (sale / 100));
            String formattedPrice = Utils.formatPrice(price);
            String formattedDiscountedPrice = Utils.formatPrice(discountedPrice);
            binding.txtPrice.setText(formattedPrice);
            binding.txtPriceBought.setText(formattedDiscountedPrice);
            binding.txtSales.setText(data.getSale() + " %");
        } catch (Exception e) {
        }

    }
}
