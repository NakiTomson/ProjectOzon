package com.app.marketPlace.data.remote.modelsDB

import androidx.room.*


@Entity(tableName = "basket")
@TypeConverters(Converters::class)
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


    val color: String? = null,

    @PrimaryKey
    val id: Int
)


