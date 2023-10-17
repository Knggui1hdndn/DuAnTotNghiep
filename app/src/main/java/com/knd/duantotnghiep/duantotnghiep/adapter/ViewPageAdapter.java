package com.knd.duantotnghiep.duantotnghiep.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.knd.duantotnghiep.duantotnghiep.ui.Fragments.AllClothesFragment;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = AllClothesFragment.newInstance();
                break;
            case 1:
                fragment = AllClothesFragment.newInstance();
                break;
            case 2:
                fragment = AllClothesFragment.newInstance();
                break;
            case 3:
                fragment = AllClothesFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
