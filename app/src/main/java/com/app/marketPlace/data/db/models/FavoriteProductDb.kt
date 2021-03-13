package com.app.marketPlace.data.db.models

import androidx.room.*
import com.app.marketPlace.data.db.converters.ProductConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity(tableName = "products")
@TypeConverters(ProductConverter::class)
data class FavoriteProductDb(

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

