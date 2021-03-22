package com.app.marketPlace.data.db.models

import androidx.room.*
import com.app.marketPlace.data.db.converters.ProductConverter


@Entity(tableName = "basket")
@TypeConverters(ProductConverter::class)
data class BasketProductDb(

    var type: Int? = null,

    val nameOfProduct: String? = null,
    val iconProduct: String? = null,
    val isFavorite: Boolean = false,
    val productDiscount: String? = null,
    val isBestseller: Boolean = false,
    val priceWithDiscount: String? = null,
    val priceOlD: String? = null,
    val goToBasket: Boolean = false,


    val shortDescription: String? = null,

    val longDescription: String? = null,

    val images: MutableList<String>? = null,

    val company: String? = null,


    var color: String? = null,

    @PrimaryKey
    val id: Int
)


