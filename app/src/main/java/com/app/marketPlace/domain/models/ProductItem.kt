package com.app.marketPlace.domain.models

import java.io.Serializable

data class ProductItem (

    val skuId:Int,

    var type:Type  = Type.OnlyImage,

    val generalIconProductSting: String? = null,
    var favoriteIconProduct: Boolean = false,
    var productInBasket: Boolean = false,
    val productDiscount:String? = null,
    var isBestseller :Boolean= false,
    val priceWithDiscount:String? = null,
    val priceOlD:String? = null,
    var goToBasket:Boolean = false,
    val nameOfProduct:String? = null,
    val inHints:Boolean = true,

    //For Details

    val startData:String? = null,

    val productNew:Boolean? = null,

    val activeForSale:Boolean? = null,

    val productTemplate:String? = null,

    val shortDescription:String? = null,

    val longDescription:String? = null,

    val images:MutableList<String>? = null,

    val company:String? = null,

    val color:String? = null,

    val categoryPath:List<CategoryPath>? = null,

    ):Serializable{

    sealed class Type :Serializable {
        object OnlyImage : Type(), Serializable
        object ProductNonName : Type(), Serializable
        object ProductWithName : Type(), Serializable
        object ProductNoNBasket : Type(), Serializable
        object ProductHorizontal : Type(), Serializable
    }
}

data class CategoryPath(
    val id:String? = null,
    val name:String? = null,
):Serializable