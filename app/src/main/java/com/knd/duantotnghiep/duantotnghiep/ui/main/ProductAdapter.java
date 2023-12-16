package com.knd.duantotnghiep.duantotnghiep.ui.main;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemProductBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ImageQuantity;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductDetail;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter<ItemProductBinding, ProductResponse> {
    @Override
    protected ItemProductBinding getItemBinding(ViewGroup parent) {
        return ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(ProductResponse productResponse, ItemProductBinding binding) {
         try {
            double price = productResponse.getPrice();
            double sale = productResponse.getSale();
            double discountedPrice = price * (1 - (sale / 100));
             DecimalFormat simpleFormatter=new DecimalFormat("##.#");
            binding.tvPrice.setText("đ " + Utils.formatPrice(discountedPrice));
            binding.tvTitle.setText(productResponse.getName());
            binding.txtStar.setText(simpleFormatter.format(productResponse.getStar()) + "");
            binding.sold.setText("Đã bán " + productResponse.getSold() +" sản phẩm");
            ArrayList<ProductDetail> productDetail = productResponse.getProductDetails() ;
            if (!productDetail.isEmpty()){
                ArrayList<ImageQuantity> imageProduct = productDetail.get(0).getImageProducts() ;
                if (!imageProduct.isEmpty()){
                    ImageQuantity imageQuantity = imageProduct.get(0);
                    Picasso.get().load(imageQuantity.getImageProduct().getImage()).into(binding.image);
                }else {
                    binding.image.setImageResource(R.drawable.baseline_image_24);
                 }
            }else {
                binding.image.setImageResource(R.drawable.baseline_image_24);
            }
        } catch (Exception e) {
            Log.d("skdsdkdskndnsd", e.getLocalizedMessage());
        }
    }
}

