package com.appmarketplace.ozon.domain.modelsUI

import java.io.Serializable

data class OnProductItem (

    val skuId:Int  = 0,

    var type:Type  = Type.OnlyImage(),

    val generalIconProduct: Int? = null,
    val generalIconProductSting: String? = null,
    var favoritelIconProduct: Boolean = false,
    val productDiscount:String? = null,
    var isBestseller :Boolean= false,
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

    val color:String? = null,

    val categoryPath:List<CategoryPath>? = null,

    ):Serializable{

    sealed class Type constructor(open val type:Int = 0):Serializable {
        class OnlyImage(override val type: Int = 1) : Type(type), Serializable
        class ProductNonName(override val type: Int = 2) : Type(type),Serializable
        class ProductWithName(override val type: Int = 3) : Type(type),Serializable
        class GetType(): Type() {
            fun getType(type: Int?):Type{
                return when (type) {
                    1 -> OnlyImage()
                    2 -> ProductNonName()
                    3 -> ProductWithName()
                    else -> OnlyImage()
                }
            }
        }
    }
}

data class CategoryPath(
    val id:String? = null,
    val name:String? = null,
):Serializable{
}

