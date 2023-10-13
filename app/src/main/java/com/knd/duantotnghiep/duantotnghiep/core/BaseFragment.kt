package com.knd.duantotnghiep.duantotnghiep.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
 import android.view.View

import androidx.activity.result.ActivityResult

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.gms.auth.api.identity.Identity

import com.google.android.gms.common.api.ApiException

abstract class BaseFragment<VB : ViewBinding>(layout: Int) : Fragment(layout) {
    private var _binding: VB? = null
    private var requestCode = 0
    open val binding get() = _binding!!


    private fun resultSignInGoogle(result: ActivityResult) {
        try {
            val credential = Identity.getSignInClient(requireActivity())
                .getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken

            if (idToken != null) {
                fragmentResultCompletion(idToken, true)

            } else {
                fragmentResultCompletion("Error", false)
            }
        } catch (e: ApiException) {
            fragmentResultCompletion(e.localizedMessage, false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = getViewBinding(view)
        initData()
        initView()
        initObserver()
    }


    open var fragmentResultCompletion: (String?, Boolean) -> Unit = { param, s -> }


    fun launchFragment(id: Int) = try {
        findNavController().navigate(id)
    } catch (e: Exception) {
        Log.d("okokok", e.localizedMessage)
    }

    fun popBackStack() = findNavController().popBackStack()

    open fun initObserver() {}

    abstract fun getViewBinding(view: View): VB
    open fun initView() {}
    open fun initData() {}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}