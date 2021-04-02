package com.app.marketPlace.presentation.interfaces

import android.widget.ImageView
import com.app.marketPlace.domain.models.Product

interface ProductRowType :RowType {

    var setOnProductClickListener: ProductClickListener?

    var setOnHeartProductClickListener: ClickListener?

    var setOnBasketProductClickListener: ClickListener?

    fun interface ProductClickListener{
        fun clickProduct(product: Product, view: ImageView)
    }

    fun interface ClickListener{
        fun onClick(product: Product)
    }
}