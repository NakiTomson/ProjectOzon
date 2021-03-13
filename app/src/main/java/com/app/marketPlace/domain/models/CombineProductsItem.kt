package com.app.marketPlace.domain.models

data class CombineProductsItem(
    val topStringOffer:String?= null,
    val list: List<ProductItem>,
    val boltonStringOffer:String? = null,
    val requestName:String? = null
)