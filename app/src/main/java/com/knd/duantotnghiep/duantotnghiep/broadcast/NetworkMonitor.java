package com.knd.duantotnghiep.duantotnghiep.broadcast;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dagger.hilt.android.qualifiers.ApplicationContext;

import javax.inject.Inject;

public class NetworkMonitor extends LiveData<Boolean> {
    private final ConnectivityManager connectivityManager;

    @Inject
    public NetworkMonitor(@ApplicationContext Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            postValue(true);
        }

        @Override
        public void onLost(@NonNull Network network) {
            postValue(false);
        }
    };

    @Override
    protected void onActive() {
        super.onActive();
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
