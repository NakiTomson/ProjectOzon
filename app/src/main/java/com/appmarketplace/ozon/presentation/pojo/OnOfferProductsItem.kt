package com.appmarketplace.ozon.presentation.pojo

data class OnOfferProductsItem(
    val topStringOffer:String?= null,
    val list: List<OnProductItem>,
    val bottonStringOffer:String? = null,
    val requestName:String? = null
)