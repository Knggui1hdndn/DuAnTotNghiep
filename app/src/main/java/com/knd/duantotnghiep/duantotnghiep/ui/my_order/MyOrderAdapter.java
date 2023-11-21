package com.knd.duantotnghiep.duantotnghiep.ui.my_order;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemLayoutMyOderBinding;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.utils.MyOrderAdapterCallback;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import java.util.function.Consumer;

public class MyOrderAdapter extends BaseAdapter<ItemLayoutMyOderBinding, OrderResponse> {
    private MyOrderAdapterCallback .OnClickListener onClickListener;
    @Override
    protected ItemLayoutMyOderBinding getItemBinding(ViewGroup parent) {
        return ItemLayoutMyOderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    public MyOrderAdapter(MyOrderAdapterCallback.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void bind(OrderResponse data, ItemLayoutMyOderBinding binding) {
        binding.txtAddress.setText(data.getAddress());
        binding.txtName.setText(data.getName());
        binding.txtPhone.setText(data.getPhoneNumber());
        binding.txtDesrepstion.setText(data.getDescription());
        binding.txtPrice.setText(Utils.formatPrice(data.getTotalAmount()));
        binding.txtTimeOrder.setText(Utils.formatDateDetails(data.getCreateAt()));
        binding.btnStatus.setText(data.getStatus());
        binding.pay.setText(data.getPayments());
        if (data.getStatus().equals("Cancel")) {
            binding.btnStatus.setText("Recreate Order");

        }
        binding.btnStatus.setOnClickListener(view -> {
          if (data.getStatus().equals("Cancel") || data.getStatus().equals("Wait for confirmation")) {
              onClickListener.onRecreateOrder(data.get_id());
          }
        });
    }
}
