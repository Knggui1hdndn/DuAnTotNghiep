package com.knd.duantotnghiep.duantotnghiep.core;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.gson.Gson;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class BaseAdapter<VB extends ViewBinding, T extends Object> extends RecyclerView.Adapter<BaseAdapter<VB, T>.ViewHolder> {


    private AdapterCallBack.OnClickItemListener<T> onClickItemListener;

    public void setOnClickItemListener(AdapterCallBack.OnClickItemListener<T> onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    protected VB binding;
    protected final List<T> listData = new ArrayList<>();

    protected abstract VB getItemBinding(ViewGroup parent);

    protected abstract void bind(T data, VB binding);


    public void setData(List<T> data) {
        listData.clear();
        listData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = getItemBinding(parent);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.getRoot().setOnClickListener(view -> {
            if (onClickItemListener != null) {
                onClickItemListener.onClickItem(listData.get(position));
            }
        });
        bind(listData.get(position), holder.binding);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final VB binding;

        public ViewHolder(VB binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
