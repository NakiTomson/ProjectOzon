package com.app.marketPlace.domain.models

data class CombineProducts(
    val topOffer:String?= null,
    val list: List<Product>,
    val bottomOffer:String? = null,
    val requestName:String? = null,
    val spain:Int = 3
)