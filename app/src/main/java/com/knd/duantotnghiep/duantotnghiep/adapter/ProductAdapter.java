package com.knd.duantotnghiep.duantotnghiep.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHodels> {
    private ArrayList<Product> list;

    public ProductAdapter(ArrayList<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodels onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ViewHodels(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodels holder, int position) {
        Product product = list.get(holder.getAdapterPosition());
        if(product == null){
            return;
        }
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());
        holder.tvCat.setText(product.getIdCata());
        Picasso.get().load(product.getImage()).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class ViewHodels extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvCat;
        private ImageView imgProduct;

        public ViewHodels(@NonNull View itemView) {

            super(itemView);
            tvName = itemView.findViewById(R.id.tv_title);
            tvCat = itemView.findViewById(R.id.category);
            tvPrice = itemView.findViewById(R.id.tv_price);
            imgProduct = itemView.findViewById(R.id.image);
        }
    }
}

