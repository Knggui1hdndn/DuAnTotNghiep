package com.knd.duantotnghiep.duantotnghiep.ui.pay;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.lifecycle.ViewModelProvider;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityPaymentConfirmationBinding;
import com.knd.duantotnghiep.duantotnghiep.models.OrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.PayQR;
import com.knd.duantotnghiep.duantotnghiep.ui.shopping_bag.ShoppingBagViewModel;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import dagger.hilt.android.AndroidEntryPoint;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
@AndroidEntryPoint
public class PaymentConfirmationActivity extends BaseActivity<ActivityPaymentConfirmationBinding> {
    private PayViewModel payViewModel;
    private CountDownTimer countDownTimer;

    @Override
    public ActivityPaymentConfirmationBinding getViewBinding() {
        return ActivityPaymentConfirmationBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void initView() {
        payViewModel = new ViewModelProvider(this).get(PayViewModel.class);
        visibilityView(false, true);
        setUpToolBar(binding.mToolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));
        binding.txtTime.setOnClickListener(v -> {
            if (binding.textView.getText().equals("Order was canceled due to non-payment. Click to refresh")) {
                recreate();
            }
        });
        setupCopyOnClick(binding.txtAccountNumber);
        setupCopyOnClick(binding.txtAccountName);
        setupCopyOnClick(binding.txtAmount);
        setupCopyOnClick(binding.txtContent);
    }

    private void setupCopyOnClick(TextView textView) {
        textView.setOnClickListener(v -> {
            Utils.copyTextToCLipBoard(this, textView.getText().toString());
        });
    }

    private void visibilityView(boolean b, boolean isVisiAnim) {
        binding.txtTime.setVisibility(b ? View.VISIBLE : View.GONE);
        binding.imgQRCode.setVisibility(b ? View.VISIBLE : View.GONE);
        binding.lottie.setVisibility(isVisiAnim ? View.VISIBLE : View.GONE);
        binding.lottie.playAnimation();
    }

    @Override
    public void initData() {
        payViewModel = new ViewModelProvider(this).get(PayViewModel.class);
        String idOrder = getIntent().getStringExtra("order");
        payViewModel.getOnGenerateQr(idOrder);
    }

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm : ss");

    private CountDownTimer countDownExp(long time) {
        return new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.txtTime.setText(simpleDateFormat.format(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                visibilityView(false, false);
                binding.txtContent.setText("");
                binding.txtTime.setText("Đơn hàng bị hủy do không thanh toán. Nhấp để làm mới.");
            }
        };
    }

    @Override
    public void initObserver() {

        payViewModel.getOnGenerateQr.observe(this, it -> ApiCallBack.handleResult(it, new ApiCallBack.HandleResult<>() {
            @SuppressLint("NewApi")
            @Override
            public void handleSuccess(PayQR data) {
                 Picasso.get().load(data.getUrl()).fit().into(binding.imgQRCode);
                countDownTimer = countDownExp(data.getExpiration() - data.getTimeCurrent());
                countDownTimer.start();
                if (data.getExpiration() > data.getTimeCurrent()) {
                    setInfoPayments(data);
                    visibilityView(true, false);
                }
                binding.txtTime.setVisibility(View.VISIBLE);
            }

            @Override
            public void handleError(String error) {
                if (error != null) {
                    showMessage(error);
                    visibilityView(false, false);
                }
            }

            @Override
            public void handleLoading() {
                visibilityView(false, true);
            }
        }));
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) countDownTimer.cancel();
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    private void setInfoPayments(PayQR data) {
        binding.txtAccountName.setText("NGUYEN DUY KHANG");
        binding.txtAccountNumber.setText("0867896418");
        binding.txtAmount.setText(Utils.formatPrice(data.getTotalAmount()));
        binding.txtContent.setText(data.getNote());
    }
}
