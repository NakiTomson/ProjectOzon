package com.app.marketPlace.domain.models

import com.app.marketPlace.data.remote.models.Categories
import java.io.Serializable

data class Product(

    val skuId: Int,
    val name: String? = null,
    val icon: String? = null,
    var type: Type = Type.OnlyImage,
    val discount: String? = null,
    val priceMinusDiscount: String? = null,
    val price: String? = null,
    var isCanGoToBasket: Boolean = false,
    var isBestseller: Boolean = false,
    val isHint: Boolean = true,
    var isFavorite: Boolean = false,
    var isBasket: Boolean = false,


    val startData: String? = null,
    val productNew: Boolean? = null,
    val activeForSale: Boolean? = null,
    val productTemplate: String? = null,
    val shortDescription: String? = null,
    val longDescription: String? = null,
    val images: MutableList<String>? = null,
    val company: String? = null,
    val color: String? = null,
    val categoryPath: List<Categories>? = null,

) : Serializable {
    sealed class Type : Serializable {
        object OnlyImage : Type(), Serializable
        object ProductNonName : Type(), Serializable
        object ProductWithName : Type(), Serializable
        object ProductNoNBasket : Type(), Serializable
        object ProductHorizontal : Type(), Serializable
    }
}
