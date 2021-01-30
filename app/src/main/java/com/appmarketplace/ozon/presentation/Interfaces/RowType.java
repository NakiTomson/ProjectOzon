package com.appmarketplace.ozon.presentation.Interfaces;

import androidx.recyclerview.widget.RecyclerView;


public interface RowType {

    int BANNER_ROW_TYPE =   0;
    int CATEGORY_ROW_TYPE = 1;
    int HISTORY_ROW_TYPE = 2;
    int LIVE_ROW_TYPE = 3;
    int REGISTRATION_ROW_TYPE = 4;
    int PRODUCTS_ROW_TYPE = 5;

    int getItemViewType();

    void onBindViewHolder(RecyclerView.ViewHolder viewHolder);
}
