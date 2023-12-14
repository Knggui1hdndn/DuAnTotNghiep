package com.knd.duantotnghiep.duantotnghiep.ui.details;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseAdapter;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemEvaluateBinding;
import com.knd.duantotnghiep.duantotnghiep.models.EvaluateResponse;
import com.knd.duantotnghiep.duantotnghiep.models.TypeFeeling;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class EvaluateAdapter extends BaseAdapter<ItemEvaluateBinding, EvaluateResponse> {
    private AdapterCallBack.EvaluateAdapterCallback evaluateAdapterCallback;

    public EvaluateAdapter(AdapterCallBack.EvaluateAdapterCallback evaluateAdapterCallback) {
        this.evaluateAdapterCallback = evaluateAdapterCallback;
    }

    @Override
    protected ItemEvaluateBinding getItemBinding(ViewGroup parent) {
        return ItemEvaluateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(EvaluateResponse data, ItemEvaluateBinding binding) {
        User user = data.getUser();
        long countYes = data.getFeelings().stream().filter(feeling -> Objects.equals(feeling.getType(), TypeFeeling.LIKE.name())).count();
        long countNo = data.getFeelings().stream().filter(feeling -> Objects.equals(feeling.getType(), TypeFeeling.DISLIKE.name())).count();
        Picasso.get().load(user.getAvatar() + "").fit().into(binding.imgAvatar);
        binding.txtName.setText(user.getName());
        binding.txtDate.setText(Utils.formatDateDetails(data.getCreateAt()));
        binding.txtComment.setText(data.getComment() + "");
        binding.yes.setOnClickListener(view -> {
            evaluateAdapterCallback.onYesClick(data.get_id());
            if (data.getFeelings().stream().anyMatch(feeling -> user.get_id().equals(feeling.getIdUser())))
                binding.message.setText("Bạn và " + (countYes-1) +  " người thấy bài viết này hữu ích");
        });

        binding.no.setOnClickListener(view -> {
            evaluateAdapterCallback.onNoClick(data.get_id());
            if (data.getFeelings().stream().anyMatch(feeling -> (user.get_id().equals(feeling.getIdUser()))))
                binding.message.setText("Bạn và " + (countNo-1) +  " người thấy bài viết này hữu ích");

        });
        setAdapterImage(data.getUrl());
        setStar(data.getStar());
        if (data.getFeelings().stream().anyMatch(feeling -> user.get_id().equals(feeling.getIdUser()))) {

            if (countYes >= 1) {
                binding.message.setText("Bạn và " + (countYes-1) +  " người thấy bài viết này hữu ích");
            }

            if (countNo >= 1) {
                binding.message.setText("Bạn và " + (countNo-1) +  " người thấy bài viết này hữu ích");
            }

        } else {
            binding.message.setText("");
        }

    }

    private void setAdapterImage(ArrayList<String> url) {
        ImageEvaluateAdapter evaluateAdapter = new ImageEvaluateAdapter();
        evaluateAdapter.setData(url);
        binding.recyclerView2.setAdapter(evaluateAdapter);
    }

    private void setStar(Integer stars) {
        int star = stars;
        ArrayList<ImageView> arrays = new ArrayList<>(Arrays.asList(binding.start1, binding.start2, binding.start3, binding.start4, binding.start5));
        for (int i = 0; i < arrays.size(); i++) {
            if (i < star) {
                arrays.get(i).setImageResource(R.drawable.baseline_star_24);
            } else {
                arrays.get(i).setImageResource(R.drawable.baseline_star_border_24);
            }
        }
    }
}
