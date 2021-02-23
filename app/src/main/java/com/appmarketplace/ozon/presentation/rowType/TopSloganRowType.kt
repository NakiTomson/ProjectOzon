package com.appmarketplace.ozon.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

data class TopSloganRowType(val sloganOffer: String) :RowType{


    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_TOP_TYPE;
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val bunnerViewHolder: ViewHolderFactory.TopSloganOfferProduct = viewHolder as  ViewHolderFactory.TopSloganOfferProduct
        bunnerViewHolder.bind(sloganOffer)
    }
}