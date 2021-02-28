package com.appmarketplace.ozon.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

data class BottomSloganRowType(val sloganOffer: String) :RowType{

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_BOTTOM_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val bunnerViewHolder: ViewHolderFactory.BottomSloganOfferProduct = viewHolder as  ViewHolderFactory.BottomSloganOfferProduct
        bunnerViewHolder.bind(sloganOffer)
    }
}