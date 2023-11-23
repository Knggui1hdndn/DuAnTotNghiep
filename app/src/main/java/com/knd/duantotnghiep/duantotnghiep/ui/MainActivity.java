package com.knd.duantotnghiep.duantotnghiep.ui;

import static com.knd.duantotnghiep.duantotnghiep.utils.Utils.COUNT_SHOPPING_BAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.OptIn;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.knd.duantotnghiep.duantotnghiep.R;

import com.knd.duantotnghiep.duantotnghiep.broadcast.NetworkMonitor;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityMainBinding;
import com.knd.duantotnghiep.duantotnghiep.models.CountsOrderDetailsAndNoti;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.ui.noti.NotificationActivity;
import com.knd.duantotnghiep.duantotnghiep.ui.search.SearchActivity;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.CheckPermission;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private NavController navController;

    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    private MainViewModel mainViewModel;
    @Inject
    public NetworkMonitor networkMonitor;
    private final ActivityResultLauncher<String> activityResult = registerForActivityResult(new ActivityResultContracts.RequestPermission(), o -> {

    });

    @Override
    protected void initData() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!CheckPermission.isPermissionPostNotification(this)) {
                activityResult.launch(CheckPermission.POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    protected void initObserver() {

        networkMonitor.observe(this, aBoolean -> {
            if (aBoolean) {
                mainViewModel.getCountNotiAndOrderDetails();
            }
        });
        mainViewModel.getCountNotiAndOrderDetails.observe(this, listNetworkResult -> ApiCallBack.handleResult(listNetworkResult, new ApiCallBack.HandleResult<CountsOrderDetailsAndNoti>() {
            @Override
            public void handleSuccess(CountsOrderDetailsAndNoti data) {
                COUNT_SHOPPING_BAG = data.getCountOrderDetails();
                Utils.setUpBadge(MainActivity.this, binding.toolbar, data.getCountOrderDetails(), R.id.cart);
                Utils.setUpBadge(MainActivity.this, binding.toolbar, data.getCountNotification(), R.id.notification);
                binding.bottomNav.getOrCreateBadge(R.id.shoppingBagFragment).setText(data.getCountOrderDetails() + "");
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
            }

            @Override
            public void handleLoading() {

            }
        }));
    }

    @Override
    protected void initView() {
        setUpToolBar(binding.toolbar, false, null);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView2);
        navController = navHostFragment != null ? navHostFragment.getNavController() : null;
        assert navController != null;
        new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.bottomNav, navController);

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.myProfileActivity || navDestination.getId() == R.id.wishListFragment) {
                binding.toolbar.setVisibility(View.GONE);
            } else {
                binding.toolbar.setVisibility(View.VISIBLE);
            }
        });
        binding.txtSearch.setOnClickListener(view -> {
            startActivity(new Intent(this, SearchActivity.class));
            overridePendingTransition(R.anim.transtion_search_enter, R.anim.transtion_search);
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.notification){
            startActivity(new Intent(this, NotificationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_shop;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
