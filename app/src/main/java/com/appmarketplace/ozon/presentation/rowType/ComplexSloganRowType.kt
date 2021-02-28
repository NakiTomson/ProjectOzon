package com.appmarketplace.ozon.presentation.rowType

import androidx.recyclerview.widget.RecyclerView
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import com.appmarketplace.ozon.presentation.factory.ViewHolderFactory

data class ComplexSloganRowType(val itemData: Item) :RowType{


    override fun getItemViewType(): Int {
        return RowType.PRODUCTS_SLOGAN_COMPLEX_TYPE;
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
        val bunnerViewHolder: ViewHolderFactory.ComplexSloganOfferProduct = viewHolder as  ViewHolderFactory.ComplexSloganOfferProduct
        bunnerViewHolder.bind(itemData)
    }

    sealed class Item(){
        class setBestseller(val company: String):Item()
        class setPrice(val actualPrice: String,val oldPrice:String):Item()
        class setSimpleOffer(val offer: String):Item()
    }
}