package com.knd.duantotnghiep.duantotnghiep.ui.noti;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemNotificationBinding;
import com.knd.duantotnghiep.duantotnghiep.models.Notification;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

public class NotificationAdapter extends BaseAdapter<ItemNotificationBinding, Notification> {
    @Override
    protected ItemNotificationBinding getItemBinding(ViewGroup parent) {
        return ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @Override
    protected void bind(Notification data, ItemNotificationBinding binding) {
        Picasso.get().load(data.getUrl()).into(binding.img);
        binding.txtBody.setText(data.getBody());
        binding.txtTitle.setText(data.getTitle());
        binding.txtTime.setText(Utils.formatDateDetails(data.getCreateAt()));
        if (data.isSeen()) {
            binding.mConstraintLayout.setBackgroundColor(Color.WHITE);
        } else {
            binding.mConstraintLayout.setBackgroundColor(binding.mConstraintLayout.getContext().getColor(R.color.unClick));
        }
    }
}
