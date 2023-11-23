package com.knd.duantotnghiep.duantotnghiep.ui.noti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityNotificationBinding;
import com.knd.duantotnghiep.duantotnghiep.models.Notification;
import com.knd.duantotnghiep.duantotnghiep.remote.NotificationApi;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class NotificationActivity extends BaseActivity<ActivityNotificationBinding> {

    @Override
    public ActivityNotificationBinding getViewBinding() {
        return ActivityNotificationBinding.inflate(getLayoutInflater());
    }

    private  ArrayList<Notification> notifications = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    @Inject
    public NotificationApi notificationApi;

    @Override
    protected void initData() {
        notificationAdapter = new NotificationAdapter();
        notificationAdapter.setOnClickItemListener(item -> {
            notificationApi.updateNotification(item.get_id());
            item.setSeen(true);
            notificationAdapter.notifyItemChanged(notifications.lastIndexOf(item));
        });
        binding.mRcy.setAdapter(notificationAdapter);
        setUpToolBar(binding.toolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));
    }

    @Override
    protected void initObserver() {
        notificationApi.getNotification().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    notificationAdapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });
    }
}