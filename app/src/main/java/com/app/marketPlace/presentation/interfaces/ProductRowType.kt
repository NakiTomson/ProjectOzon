package com.app.marketPlace.presentation.interfaces

import android.widget.ImageView
import com.app.marketPlace.domain.models.ProductItem

interface ProductRowType :RowType {

    var setOnProductClickListener: ProductClickListener?

    var setOnHeartProductClickListener: ClickListener?

    var setOnBasketProductClickListener: ClickListener?

    fun interface ProductClickListener{
        fun clickProduct(product: ProductItem, view: ImageView)
    }

    fun interface ClickListener{
        fun onClick(product: ProductItem)
    }

}