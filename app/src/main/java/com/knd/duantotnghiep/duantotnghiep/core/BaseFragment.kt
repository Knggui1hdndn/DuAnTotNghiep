package com.knd.duantotnghiep.phonetrackerlocation.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VB : ViewBinding>(layout: Int) : Fragment(layout) {
    private var _binding: VB? = null
    private var requestCode = 0
    open val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = getViewBinding(view)
        initData()
        initView()
        initObserver()
    }

    open fun initObserver() {}

    abstract fun getViewBinding(view: View): VB
    open fun initView() {}
    open fun initData() {}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}