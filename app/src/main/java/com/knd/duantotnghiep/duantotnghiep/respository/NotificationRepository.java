package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.Notification;
import com.knd.duantotnghiep.duantotnghiep.remote.NotificationApi;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.util.List;

import javax.inject.Inject;

public class NotificationRepository {
    private NotificationApi notificationApi;

    private final MutableLiveData<NetworkResult<List<Notification>>> _getNotifications = new MutableLiveData<>();
    public LiveData<NetworkResult<List<Notification>>> getNotifications = _getNotifications;

    @Inject
    public NotificationRepository(NotificationApi notificationApi) {
        this.notificationApi = notificationApi;
    }

    public void getNotifications(int skip) {
         NetworkResult.handleCallApi(notificationApi.getNotification(skip),_getNotifications,null);
    }
}
