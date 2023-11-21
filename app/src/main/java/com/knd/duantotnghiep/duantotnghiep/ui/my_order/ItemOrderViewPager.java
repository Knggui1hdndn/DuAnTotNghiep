package com.knd.duantotnghiep.duantotnghiep.ui.my_order;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemOrderViewPager extends FragmentStateAdapter {

    private final ArrayList<String> strings = new ArrayList<>(Arrays.asList("Wait for confirmation","Confirmed", "Delivering", "Delivered", "Cancel", "Returns"));

    public ItemOrderViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ItemOrderFragment.getInstance(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
