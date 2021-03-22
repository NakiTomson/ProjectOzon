package com.app.marketPlace.domain.models

data class CombineProductsItem(
    val topOffer:String?= null,
    val list: List<ProductItem>,
    val bottomOffer:String? = null,
    val requestName:String? = null,
    val spain:Int = 3
)