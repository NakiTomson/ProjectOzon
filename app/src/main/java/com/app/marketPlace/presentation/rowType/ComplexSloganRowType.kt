package com.app.marketPlace.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.app.marketPlace.presentation.interfaces.RowType
import com.app.marketPlace.presentation.factory.ViewHolderFactory

data class ComplexSloganRowType(val itemData: Item) :RowType{


    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_COMPLEX_TYPE
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val bannerViewHolder: ViewHolderFactory.ComplexSloganOfferProduct = viewHolder as  ViewHolderFactory.ComplexSloganOfferProduct
        bannerViewHolder.bind(itemData)
    }

    sealed class Item {
        class SetBestseller(val company: String):Item()
        class SetPrice(val actualPrice: String, val oldPrice:String):Item()
        class SetSimpleOffer(val offer: String):Item()
    }
}