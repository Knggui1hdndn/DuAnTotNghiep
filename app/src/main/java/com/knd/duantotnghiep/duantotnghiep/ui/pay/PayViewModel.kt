package com.knd.duantotnghiep.duantotnghiep.ui.pay

import androidx.lifecycle.ViewModel
import com.knd.duantotnghiep.duantotnghiep.models.Order
import com.knd.duantotnghiep.duantotnghiep.respository.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PayViewModel(private val userResponse: UserResponse) : ViewModel() {
    val onGenerateQr = userResponse.generateQr!!
    fun getOnGenerateQr(order: Order) = userResponse.generateQR(order)
}