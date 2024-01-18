package com.knd.duantotnghiep.duantotnghiep.ui.dialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemChatBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ChatMessage;
import com.knd.duantotnghiep.duantotnghiep.ui.details.ImageEvaluateAdapter;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


public class AdapterChat extends BaseAdapter<ItemChatBinding, ChatMessage> {

    @Override
    protected ItemChatBinding getItemBinding(ViewGroup parent) {
        return ItemChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @Override
    protected void bind(ChatMessage data, ItemChatBinding binding) {
        boolean check = data.getToUser();
        binding.timeAdminSender.setVisibility(View.GONE);
        binding.timeUserSender.setVisibility(View.GONE);
        if (data.getMessage() != null && data.getMessage().trim().isEmpty()) {
            binding.messageReceiver.setVisibility(View.GONE);
            binding.messageSender.setVisibility(View.GONE);
        }
        if (!check) {
            binding.mLayoutSend.setOnClickListener(view -> {
                binding.timeUserSender.setVisibility(binding.timeUserSender.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            });
            setAdapterImage(data.getUrl(), binding.recyclerView2);
            binding.mLayoutReceiver.setVisibility(View.GONE);
            binding.mLayoutSend.setVisibility(View.VISIBLE);
            binding.messageSender.setText(data.getMessage());
            binding.timeUserSender.setText(Utils.formatDateDetails(data.getTimeSend()));
        } else {
            binding.mLayoutReceiver.setOnClickListener(view -> {
                binding.timeAdminSender.setVisibility(binding.timeAdminSender.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            });
            binding.messageReceiver.setText(data.getMessage());

            setAdapterImage(data.getUrl(), binding.recyclerView1);
            binding.mLayoutSend.setVisibility(View.GONE);
            binding.mLayoutReceiver.setVisibility(View.VISIBLE);
            binding.messageReceiver.setText(data.getMessage());
            binding.timeAdminSender.setText(Utils.formatDateDetails(data.getTimeSend()));
        }
    }

    private void setAdapterImage(List<String> url, RecyclerView recyclerView) {
        ImageEvaluateAdapter evaluateAdapter = new ImageEvaluateAdapter();
        recyclerView.setAdapter(evaluateAdapter);
        evaluateAdapter.setData(url);

    }

}
