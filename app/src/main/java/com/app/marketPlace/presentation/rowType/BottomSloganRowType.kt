package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory

data class BottomSloganRowType(val sloganOffer: String) :RowType{

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_BOTTOM_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val bannerViewHolder: ViewHolderFactory.BottomSloganOfferProduct = viewHolder as  ViewHolderFactory.BottomSloganOfferProduct
        bannerViewHolder.bind(sloganOffer)
    }
}