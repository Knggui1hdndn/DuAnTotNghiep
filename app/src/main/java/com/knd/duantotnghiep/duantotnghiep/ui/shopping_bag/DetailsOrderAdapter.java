package com.knd.duantotnghiep.duantotnghiep.ui.shopping_bag;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemLayoutShoppingBagBinding;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

public class DetailsOrderAdapter extends BaseAdapter<ItemLayoutShoppingBagBinding, DetailOrderResponse> {
    private AdapterCallBack.OnClickViewDetailsOrder onClickViewDetailsOrder;

    public DetailsOrderAdapter() {
    }

    public DetailsOrderAdapter(AdapterCallBack.OnClickViewDetailsOrder onClickViewDetailsOrder) {
        this.onClickViewDetailsOrder = onClickViewDetailsOrder;
    }

    @Override
    protected ItemLayoutShoppingBagBinding getItemBinding(ViewGroup parent) {
        return ItemLayoutShoppingBagBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(DetailOrderResponse data, ItemLayoutShoppingBagBinding binding) {

        binding.checked.setChecked(data.getSelected());
        binding.txtTitle.setText(data.getProductResponse().getName() + "");
        binding.txtClassify.setText(data.getSize() + "-" + data.getImageQuantity().getImageProduct().getColor());
        binding.number.setText(data.getQuantity() + "");
        binding.txtTotalAmount.setText("đ " + Utils.formatPrice(Double.valueOf(data.getIntoMoney())) + "");
        binding.txtRemaining.setText(data.getImageQuantity().getQuantity() + "");
        boolean checkError = data.getQuantity() > data.getImageQuantity().getQuantity();
        if (checkError) {
            binding.txtError.setVisibility(View.VISIBLE);
            binding.txtError.setText("The quantity of products exceeds the warehouse");
        } else {
            binding.txtError.setVisibility(View.GONE);
        }
        Picasso.get().load(data.getImageQuantity().getImageProduct().getImage()).fit().into(binding.img);
        binding.minus.setOnClickListener(view -> {
            int quantity = data.getQuantity() - 1;
            if (quantity == 0) {
                onClickViewDetailsOrder.onCloseClick(data);
                return;
            }
            data.setQuantity(quantity);
            onClickViewDetailsOrder.onMinusClick(data);
        });
        binding.plus.setOnClickListener(view -> {
            data.setQuantity(data.getQuantity() + 1);
            onClickViewDetailsOrder.onPlusClick(data);
        });
        binding.checked.setOnCheckedChangeListener((compoundButton, b) -> {
            data.setSelected(b);
            onClickViewDetailsOrder.onCheckedClick(data);
        });
        binding.close.setOnClickListener(view -> {
            onClickViewDetailsOrder.onCloseClick(data);
        });
    }
}
