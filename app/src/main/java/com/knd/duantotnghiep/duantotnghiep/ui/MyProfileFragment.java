package com.knd.duantotnghiep.duantotnghiep.ui;


import static com.knd.duantotnghiep.duantotnghiep.utils.Utils.checkInfoNull;

import android.content.Intent;
import android.view.View;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityMyProfileBinding;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.ui.my_order.MyOderActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.my_order.MyOrderAdapter;
import com.knd.duantotnghiep.duantotnghiep.ui.profile.EditProfileActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.signIn.LoginActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.TokenManager;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyProfileFragment extends BaseFragment<ActivityMyProfileBinding> {


    @Inject
    public UserPreferencesManager userPreferencesManager;

    @Inject
    public TokenManager tokenManager;
    User user;

    public MyProfileFragment() {
        super(R.layout.activity_my_profile);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initView() {

        binding.textView13.setOnClickListener(view -> startActivity(
                new Intent(requireActivity(), MyOderActivity.class)
        ));

        binding.edit.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), EditProfileActivity.class));
        });
        binding.logout.setOnClickListener(view -> {
            userPreferencesManager.removeCurrentUser();
            tokenManager.removeToken();
            requireActivity().finish();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
        });
    }

    @Override
    public void onResume() {

        super.onResume();
        initData();
    }

    @Override
    public void initData() {
        user = userPreferencesManager.getCurrentUser();
        if (user != null) {
            Picasso.get().load(user.getAvatar()).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).into(binding.avatar);
            binding.txtName.setText(checkInfoNull(user.getName()));
            binding.txtAddress.setText(checkInfoNull(user.getAddress()));
            binding.txtEmail.setText(checkInfoNull(user.getEmail()));
        }
    }


    @Override
    public ActivityMyProfileBinding getViewBinding(View view) {
        return ActivityMyProfileBinding.bind(view);
    }
}