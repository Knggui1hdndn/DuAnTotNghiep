package com.knd.duantotnghiep.duantotnghiep.ui.pay

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import androidx.activity.viewModels
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityPaymentConfirmationBinding
import com.knd.duantotnghiep.duantotnghiep.models.Order
import com.knd.duantotnghiep.duantotnghiep.models.PayQR
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@AndroidEntryPoint
public  class PaymentConfirmationActivity : BaseActivity<ActivityPaymentConfirmationBinding>() {
    private val payViewModel by viewModels<PayViewModel>()
    override fun getViewBinding(): ActivityPaymentConfirmationBinding {
        return ActivityPaymentConfirmationBinding.inflate(layoutInflater)
    }

    override fun initView() {
        visibilityView(false, true)
        binding.txtTime.setOnClickListener {
            if (binding.textView.text == "Order was canceled due to non-payment. Click to refresh") {
                recreate()
            }
        }
    }

    private fun visibilityView(b: Boolean, isVisiAnim: Boolean = false) {
        with(binding) {
            txtTime.visibility = if (!b) View.GONE else View.VISIBLE
            imgQRCode.visibility = if (!b) View.GONE else View.VISIBLE
            lottie.visibility = if (!isVisiAnim) View.GONE else View.VISIBLE;lottie.playAnimation()

        }
    }

    override fun initData() {
        val order = intent.getSerializableExtra("order") as Order
        payViewModel.getOnGenerateQr(order)
    }

    private val simpleDateFormat = SimpleDateFormat("mm : ss")
    private fun countDownExp(time: Long) = object : CountDownTimer(time, 1000) {
        override fun onTick(p0: Long) {
            binding.txtTime.text = simpleDateFormat.format(p0.toInt())
        }

        override fun onFinish() {
            visibilityView(false,false)
            binding.txtTime.text = "Order was canceled due to non-payment. Click to refresh"
        }

    }

    override fun initObserver() {
        with(payViewModel) {
            onGenerateQr.observe(this@PaymentConfirmationActivity) {
                ApiCallBack.handleResult(it, object : ApiCallBack.HandleResult<PayQR> {
                    override fun handleSuccess(data: PayQR?) {
                        if (data != null) {
                            Picasso.get().load(data.url).fit().into(binding.imgQRCode)
                            countDownExp(data.expiration - System.currentTimeMillis())
                            visibilityView(false, false)
                        }
                    }

                    override fun handleError(error: String?) {
                        if (error != null) {
                            showMessage(error)
                            visibilityView(false, false)
                        }
                    }

                    override fun handleLoading() {
                        visibilityView(false, true)
                    }

                })

            }
        }
    }
}