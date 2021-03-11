package com.app.marketPlace.presentation.adapters;

import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.marketPlace.presentation.interfaces.RowType;
import com.app.marketPlace.presentation.factory.ViewHolderFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MultipleTypesAdapter extends RecyclerView.Adapter {

    private final List<RowType> dataSet = new ArrayList<>(17);


    private volatile int countPosition = -1;


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
    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
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