package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory

data class TopSloganRowType(val sloganOffer: String,val randomId: Double = Math.random() *421) :RowType{

    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_TOP_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val holder: ViewHolderFactory.TopSloganOfferProduct = viewHolder as  ViewHolderFactory.TopSloganOfferProduct
        holder.bind(sloganOffer)
    }
}