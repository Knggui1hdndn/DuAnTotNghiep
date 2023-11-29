package com.knd.duantotnghiep.duantotnghiep.ui.search;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemSearchBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;


public class SearchAdapter extends BaseAdapter<ItemSearchBinding, ProductResponse> {

    @Override
    protected ItemSearchBinding getItemBinding(ViewGroup parent) {
        return ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @Override
    protected void bind(ProductResponse data, ItemSearchBinding binding) {
       try {
           if (data instanceof ProductResponse) {
               binding.txtName.setText(((ProductResponse) data).getName());
               binding.isSearch.setVisibility(View.GONE);

               Picasso.get().load(Utils.getImageProduct((ProductResponse) data)).into(binding.imgProduct);
           }
       }catch (Exception e) {}
    }
}
