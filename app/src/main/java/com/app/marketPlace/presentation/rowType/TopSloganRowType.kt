package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory

data class TopSloganRowType(val sloganOffer: String) :RowType{


    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_TOP_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val bannerViewHolder: ViewHolderFactory.TopSloganOfferProduct = viewHolder as  ViewHolderFactory.TopSloganOfferProduct
        bannerViewHolder.bind(sloganOffer)
    }
}