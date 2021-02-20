package com.appmarketplace.ozon.presentation.adapters;

import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl;
import com.appmarketplace.ozon.presentation.Interfaces.RowType;
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Qualifier;


public class MultipleTypesAdapter extends RecyclerView.Adapter {

    private final List<RowType> dataSet = new ArrayList<>(17);


    private volatile int countPosition = -1;

    public void setData(Collection<RowType> dataSets){
        dataSet.clear();
        dataSet.addAll(dataSets);
        notifyDataSetChanged();
    }

    public void setData(RowType dataSets){
        dataSet.add(dataSets);
        ++countPosition;
        notifyItemChanged(countPosition);
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
    public long getItemId(int position) {
        return dataSet.get(position).hashCode();
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
