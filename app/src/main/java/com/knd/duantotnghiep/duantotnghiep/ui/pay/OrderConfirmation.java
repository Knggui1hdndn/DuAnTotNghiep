package com.knd.duantotnghiep.duantotnghiep.ui.pay;

import static com.knd.duantotnghiep.duantotnghiep.utils.Utils.checkInfoNull;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.LayoutConfimPurchaseBinding;
import com.knd.duantotnghiep.duantotnghiep.models.CombinedOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.OrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.Payments;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.ui.details.ShipmentDetails;
import com.knd.duantotnghiep.duantotnghiep.ui.shopping_bag.ShoppingBagViewModel;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderConfirmation extends BaseActivity<LayoutConfimPurchaseBinding> {
    @Inject
    UserPreferencesManager userPreferencesManager;
    private User user;
    private double totalCost;
    private double discount;
    private OrderRequest orderRequest;
    private ShoppingBagViewModel shoppingBagViewModel;
    private String idOrder;
    private DetailOrderRequest detailOrderRequest;
    private double totalPayment;
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            user = (User) intent.getSerializableExtra("user");
            assert user != null;
            setDeliveryInformation(user);
        }
    };

    @SuppressLint("SetTextI18n")
    private void setDeliveryInformation(User user) {
        binding.address.setText("Địa chỉ: " + checkInfoNull(user.getAddress()));
        binding.name.setText("Tên: " + checkInfoNull(user.getName()));
        binding.phoneNumber.setText("Số điện thoại: " + checkInfoNull(user.getPhoneNumber()));
    }

    @Override
    public LayoutConfimPurchaseBinding getViewBinding() {
        return LayoutConfimPurchaseBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        shoppingBagViewModel = new ViewModelProvider(this).get(ShoppingBagViewModel.class);
        user = userPreferencesManager.getCurrentUser();
        Log.d("ShoppingBagViewModel",user.toString());
        Intent intent = getIntent();
        totalCost = intent.getDoubleExtra("totalCost", 0.0);
        discount = intent.getDoubleExtra("discount", 0.0);
        totalPayment = intent.getDoubleExtra("totalPayment", 0.0);
        idOrder = intent.getStringExtra("order");
        try {
            detailOrderRequest = new Gson().fromJson(intent.getStringExtra("detailsOrder"), DetailOrderRequest.class);
        } catch (JsonSyntaxException e) {
            detailOrderRequest = null;
        }
    }

    @SuppressLint({"SetTextI18n", "UnspecifiedRegisterReceiverFlag", "UseCompatLoadingForDrawables"})
    @Override
    protected void initView() {
        setUpToolBar(binding.toolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));
        binding.edit.setOnClickListener(view -> {
            registerReceiver(broadcastReceiver, new IntentFilter("edit"));
            startActivity(new Intent(OrderConfirmation.this, ShipmentDetails.class));
        });

        binding.pay.setOnClickListener(view -> {
            orderRequest = new OrderRequest(user.getName(), user.getPhoneNumber(), user.getAddress(), binding.rdigr.getCheckedRadioButtonId() == R.id.transfer ? Payments.TRANSFER : Payments.CASH);
            if (user.getName().isEmpty() || user.getPhoneNumber().isEmpty() || user.getAddress().isEmpty()) {
                showMessage("Vui lòng nhập đầy đủ thông tin nhận hàng");
                return;
            }
            if (idOrder != null) {
                shoppingBagViewModel.purchase(idOrder, new CombinedOrderRequest(orderRequest, null));
            } else {
                shoppingBagViewModel.purchase(null, new CombinedOrderRequest(orderRequest, detailOrderRequest));
            }
        });
        setDeliveryInformation(user);
        binding.txtDiscount.setText("-đ " + Utils.formatPrice(discount));
        binding.totalCost.setText("đ " + Utils.formatPrice(totalCost));
        binding.totalPayment.setText("đ " + Utils.formatPrice(totalPayment));
    }

    @Override
    protected void initObserver() {
        shoppingBagViewModel.purchase.observe(this, voidNetworkResult -> {
            ApiCallBack.handleResult(voidNetworkResult, new ApiCallBack.HandleResult<String>() {
                @Override
                public void handleSuccess(String data) {
                    sendBroadcast(new Intent("confirm"));
                    if (binding.rdigr.getCheckedRadioButtonId() == R.id.cash) {
                        showMessage("Chờ xác nhận");
                        finish();
                        return;
                    }
                    Intent intent = new Intent(OrderConfirmation.this, PaymentConfirmationActivity.class);
                    intent.putExtra("order", data);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void handleError(String error) {
                    showMessage(error);
                }

                @Override
                public void handleLoading() {

                }
            });
        });
    }
}
