package com.knd.duantotnghiep.duantotnghiep.core

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack

abstract class BaseAdapter<VB : ViewBinding, T> constructor() :
    RecyclerView.Adapter<BaseAdapter<VB, T>.ViewHolder>() {

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    private var onClickItemListener: AdapterCallBack.OnClickItemListener<T>? = null

    fun setOnClickItemListener(onClickItemListener: AdapterCallBack.OnClickItemListener<T>) {
        this.onClickItemListener = onClickItemListener
    }

    protected lateinit var binding: VB
    protected val listData: MutableList<T> = mutableListOf()
    protected abstract fun getItemBinding(parent: ViewGroup): VB
    protected abstract fun bind(data: T, binding: VB)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<T>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = getItemBinding(parent)
         return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.root.setOnClickListener { onClickItemListener?.onClickItem(listData[position]) }
        bind(listData[position], holder.binding)
    }


}