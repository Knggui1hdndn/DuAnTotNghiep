package com.knd.duantotnghiep.duantotnghiep.ui.main;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemProductBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ImageQuantity;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductDetail;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends BaseAdapter<ItemProductBinding, ProductResponse> {
    @Override
    protected ItemProductBinding getItemBinding(ViewGroup parent) {
        return ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(ProductResponse productResponse, ItemProductBinding binding) {
        Log.d("sssssfaaaaaa",productResponse.getName()+"");
        if (productResponse != null) {
            try {
                double price = productResponse.getPrice();
                double sale = productResponse.getSale();
                double discountedPrice = price * (1 - (sale / 100));
                binding.tvPrice.setText("đ " + Utils.formatPrice(discountedPrice));
                binding.tvTitle.setText(productResponse.getName());
                binding.txtStar.setText(productResponse.getStar() + "");
                binding.sold.setText("Sold " + productResponse.getSold());

                if (productResponse.getProductDetails() != null && !productResponse.getProductDetails().isEmpty()) {
                    ProductDetail productDetail = productResponse.getProductDetails().get(0);

                    if (productDetail.getImageProducts() != null && !productDetail.getImageProducts().isEmpty()) {
                        ImageQuantity imageProduct = productDetail.getImageProducts().get(0);
                        Picasso.get().load(imageProduct.getImageProduct().getImage()).into(binding.image);
                    }
                }
            } catch (Exception e) {

            }
        }
    }
}

