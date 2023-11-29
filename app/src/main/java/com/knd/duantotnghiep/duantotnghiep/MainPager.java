package com.knd.duantotnghiep.duantotnghiep;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.knd.duantotnghiep.duantotnghiep.ui.MyProfileFragment;
import com.knd.duantotnghiep.duantotnghiep.ui.favourite.WishListFragment;
import com.knd.duantotnghiep.duantotnghiep.ui.home.HomeFragment;
import com.knd.duantotnghiep.duantotnghiep.ui.main.ClothesFragment;
import com.knd.duantotnghiep.duantotnghiep.ui.shopping_bag.ShoppingBagFragment;

public class MainPager extends FragmentStateAdapter {
    public MainPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 1 -> new ClothesFragment();
            case 2 -> new ShoppingBagFragment();
            case 3 -> new WishListFragment();
            case 4 -> new MyProfileFragment();
            default -> new HomeFragment();
        };
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
