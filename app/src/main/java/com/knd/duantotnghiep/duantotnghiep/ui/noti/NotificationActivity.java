package com.knd.duantotnghiep.duantotnghiep.ui.noti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.View;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.core.Pagination;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityNotificationBinding;
import com.knd.duantotnghiep.duantotnghiep.models.Notification;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.remote.NotificationApi;
import com.knd.duantotnghiep.duantotnghiep.respository.NotificationRepository;
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

    private ArrayList<Notification> notifications = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    @Inject
    public NotificationRepository notificationRepository;
    private Pagination notiResponsePagination;

    @Override
    protected void initData() {
        notificationAdapter = new NotificationAdapter();
        binding.mRcy.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        notificationAdapter.setOnClickItemListener(item -> {

        });
        binding.mRcy.setAdapter(notificationAdapter);
        setUpToolBar(binding.toolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));
        notificationRepository.getNotifications(0);
        notiResponsePagination = new Pagination<>(this,
                binding.mProgress,
                notificationAdapter,
                notificationRepository.getNotifications, size -> {
            notificationRepository.getNotifications(size);
        }).attach();
        binding.mRcy.addOnScrollListener(notiResponsePagination);
    }

    @Override
    protected void initObserver() {

    }
}