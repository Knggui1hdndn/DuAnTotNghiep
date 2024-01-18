package com.knd.duantotnghiep.duantotnghiep.ui.favourite;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemWishListBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ImageQuantity;
import com.knd.duantotnghiep.duantotnghiep.models.ProductDetail;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class FavouriteAdapter extends BaseAdapter<ItemWishListBinding, ProductResponse> {
    @Override
    protected ItemWishListBinding getItemBinding(ViewGroup parent) {
        return ItemWishListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(ProductResponse productResponse, ItemWishListBinding binding) {
        if (productResponse != null) {
            if (!productResponse.getFavourite()){
                binding.favorite.setImageResource(R.drawable.heart_unclick);
            }else {
                binding.favorite.setImageResource(R.drawable.baseline_favorite_24);
            }
            double price = productResponse.getPrice();
            double sale = productResponse.getSale();
            double discountedPrice = price * (1 - (sale / 100));
            binding.tvPrice.setText("đ " + Utils.formatPrice(discountedPrice));
            binding.tvTitle.setText(productResponse.getName());
            DecimalFormat simpleFormatter=new DecimalFormat("##.#");
            binding.txtStar.setText(simpleFormatter.format(productResponse.getStar()) + "");
            binding.sold.setText("Đã bán " + productResponse.getSold());
            if (productResponse.getProductDetails() != null && !productResponse.getProductDetails().isEmpty()) {
                ProductDetail productDetail = productResponse.getProductDetails().get(0);
                if (productDetail.getImageProducts() != null && !productDetail.getImageProducts().isEmpty()) {
                    ImageQuantity imageProduct = productDetail.getImageProducts().get(0);
                    Picasso.get().load(imageProduct.getImageProduct().getImage()).into(binding.image);
                }
            }
        }
    }

}
