package com.appmarketplace.ozon.presentation.data

import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.adapters.ContainerProductsAdapter
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

data class ProductsRowType(val adapterProducts: ContainerProductsAdapter) :RowType {

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_ROW_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val productionViewHolder = viewHolder as ViewHolderFactory.ProductViewHolder
        productionViewHolder.bind(adapterProducts)
    }
}