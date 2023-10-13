package com.knd.duantotnghiep.duantotnghiep.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knd.duantotnghiep.duantotnghiep.Dao.SearchDao;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.databinding.ItemSearchBinding;
import com.knd.duantotnghiep.duantotnghiep.models.SearchLocal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    @Inject
    public SearchAdapter() {
        super();
    }

    @Inject
    public  SearchDao searchDao;
    private ArrayList<SearchLocal> list1 = new ArrayList<>();
    private ArrayList<SearchLocal> listCopy = new ArrayList<>();

    public void setData(List<SearchLocal> list) {
        list1.clear();
        list1.addAll(list);
        if (listCopy.size() == 0) listCopy = list1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding itemSearchBinding = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchViewHolder(itemSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        // Bind data to the ViewHolder
        holder.bind(list1.get(position));
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                list1 = listCopy;
                if (charSequence.length() > 0) {
                    String query = charSequence.toString().toLowerCase();
                    ArrayList<SearchLocal> list3 = new ArrayList<>();

                    for (SearchLocal searchLocal : list1) {
                        if (searchLocal.getName().toLowerCase().contains(query)) {
                            list3.add(searchLocal);
                        }
                    }
                    filterResults.values = list3;
                    filterResults.count = list3.size();
                } else {
                    list1.clear();
                    list1.addAll(listCopy);
                    filterResults.values = list1;
                    filterResults.count = list1.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list1 = (ArrayList<SearchLocal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private final ItemSearchBinding itemSearchBinding;

        public SearchViewHolder(@NonNull ItemSearchBinding itemSearchBinding) {
            super(itemSearchBinding.getRoot());
            this.itemSearchBinding = itemSearchBinding;
        }


        public void bind(SearchLocal searchLocal) {
            Picasso.get().load(searchLocal.getImage()).centerCrop().error(R.drawable.img_2).into(itemSearchBinding.imageView2);
            itemSearchBinding.txtName.setText(searchLocal.getName());
            itemSearchBinding.cardView.setOnClickListener(view -> {
                searchDao.insertSearch(searchLocal);
                listCopy.add(searchLocal);
            });
        }
    }
}
