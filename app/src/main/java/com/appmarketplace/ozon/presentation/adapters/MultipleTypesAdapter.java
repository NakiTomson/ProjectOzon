package com.appmarketplace.ozon.presentation.adapters;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.appmarketplace.ozon.presentation.Interfaces.RowType;
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MultipleTypesAdapter extends RecyclerView.Adapter {

    private List<RowType> dataSet;

    public MultipleTypesAdapter(List<RowType> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public int getItemViewType(int position) {

        return dataSet.get(position).getItemViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.create(parent, viewType);
    }


    @Override
    public void onBindViewHolder(@NotNull final RecyclerView.ViewHolder holder, int position) {
        dataSet.get(position).onBindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
