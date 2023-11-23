package com.knd.duantotnghiep.duantotnghiep.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.knd.duantotnghiep.duantotnghiep.models.CategoryResponse;

import java.util.ArrayList;

public class ClothesViewPager extends FragmentStateAdapter {
     private CategoryResponse categories;

    public ClothesViewPager(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, CategoryResponse categories) {
        super(fragmentManager, lifecycle);
        this.categories = categories;

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ItemClothes fragment=new ItemClothes();
        Bundle bundle = new Bundle(2);
        bundle.putString("idCategory", categories.get(position).get_id());
        fragment.setArguments(bundle);
        return fragment;
     }


    @Override
    public int getItemCount() {
        return categories.size();
    }
}
