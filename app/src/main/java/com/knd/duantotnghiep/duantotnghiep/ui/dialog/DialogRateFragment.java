package com.knd.duantotnghiep.duantotnghiep.ui.dialog;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseDialogFragment;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentDialogRateBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class DialogRateFragment extends BaseDialogFragment<FragmentDialogRateBinding> {
    private DialogRateCallback.onClickListener onClickListener;

    public DialogRateFragment() {
        super(R.layout.fragment_dialog_rate);
    }

    public DialogRateFragment(DialogRateCallback.onClickListener onClickListener) {
        this();
        this.onClickListener = onClickListener;
    }

    @Override
    public void initObserver() {
    }

    private int index = 5;

    @Override
    public void initView() {
        ArrayList<ImageView> imageViewArrayList = new ArrayList<>(Arrays.asList(
                binding.imageView1,
                binding.imageView2,
                binding.imageView3,
                binding.imageView4,
                binding.imageView5));

        imageViewArrayList.forEach(imageView -> {
            imageView.setOnClickListener(view -> {
                index = imageViewArrayList.indexOf(imageView) +1;
                for (int i = 0; i < imageViewArrayList.size(); i++) {
                    imageViewArrayList.get(i).setImageTintList(ColorStateList.valueOf(i < index ? Color.parseColor("#FF9800") : requireContext().getColor(R.color.unClick)));
                }
            });
        });

        binding.choose.setOnClickListener(view -> {
            onClickListener.onClickChoose(index);
            dismiss();
        });

        binding.imageView7.setOnClickListener(view -> {
            dismiss();
        });
    }

    @Override
    public FragmentDialogRateBinding getViewBinding(View view) {
        return FragmentDialogRateBinding.bind(view);
    }
}
