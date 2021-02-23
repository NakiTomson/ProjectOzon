package com.appmarketplace.ozon.domain.modelsUI

data class OnProductItem(

    val generalIconProduct: Int? = null,
    val generalIconProductSting: String? = null,
    val favoritelIconProduct: Boolean = false,
    val productDiscount:String? = null,
    val isBestseller :Boolean= false,
    val priceWithDiscount:String? = null,
    val priceOlD:String? = null,
    val goToBasket:Boolean = false,
    val nameOfProduct:String? = null

)