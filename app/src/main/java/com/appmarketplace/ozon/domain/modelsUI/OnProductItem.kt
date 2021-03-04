package com.appmarketplace.ozon.domain.modelsUI

import java.io.Serializable

data class OnProductItem (

    val skuId:Int,

    var type:Type  = Type.OnlyImage,

    val generalIconProduct: Int? = null,
    val generalIconProductSting: String? = null,
    var favoritelIconProduct: Boolean = false,
    var productInBasket: Boolean = false,
    val productDiscount:String? = null,
    var isBestseller :Boolean= false,
    val priceWithDiscount:String? = null,
    val priceOlD:String? = null,
    val goToBasket:Boolean = false,
    val nameOfProduct:String? = null,
    val inHints:Boolean = true,

    //For Details

    val startData:String? = null,

    val productNew:Boolean? = null,

    val activeForSale:Boolean? = null,

    val productTemplate:String? = null,

    val shortDescription:String? = null,

    val longDescription:String? = null,

    val images:List<String>? = null,

    val company:String? = null,

    val color:String? = null,

    val categoryPath:List<CategoryPath>? = null,

    ):Serializable{

    sealed class Type constructor():Serializable {
        object OnlyImage : Type(), Serializable
        object ProductNonName : Type(), Serializable
        object ProductWithName : Type(), Serializable
    }
}

data class CategoryPath(
    val id:String? = null,
    val name:String? = null,
):Serializable{
}

