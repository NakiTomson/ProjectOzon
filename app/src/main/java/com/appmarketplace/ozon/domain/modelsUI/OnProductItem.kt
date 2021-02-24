package com.appmarketplace.ozon.domain.modelsUI

import java.io.Serializable

data class OnProductItem (

    var type:Type  = Type.OnlyImage,

    val generalIconProduct: Int? = null,
    val generalIconProductSting: String? = null,
    val favoritelIconProduct: Boolean = false,
    val productDiscount:String? = null,
    val isBestseller :Boolean= false,
    val priceWithDiscount:String? = null,
    val priceOlD:String? = null,
    val goToBasket:Boolean = false,
    val nameOfProduct:String? = null,



    //For Details

    val startData:String? = null,

    val productNew:Boolean? = null,

    val activeForSale:Boolean? = null,

    val productTemplate:String? = null,

    val shortDescription:String? = null,

    val longDescription:String? = null,

    val images:List<String>? = null,

    val company:String? = null,

    val categoryPath:List<CategoryPath>? = null,
):Serializable{
    sealed class Type():Serializable {
        object OnlyImage : Type(),Serializable
        object ProductNonName : Type(),Serializable
        object ProductWithName : Type(),Serializable
    }
}

data class CategoryPath(
    val id:String? = null,
    val name:String? = null,
):Serializable{
}

