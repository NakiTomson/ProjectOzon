package com.app.marketPlace.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.marketPlace.data.db.converters.ProductConverter


@Entity(tableName = "product")
@TypeConverters(ProductConverter::class)
data class FavoriteProductDb(

    val name: String? = null,
    val icon: String? = null,
    val isFavorite: Boolean = false,
    val discount: String? = null,
    val isBestseller: Boolean = false,
    val priceMinusDiscount: String? = null,
    val price: String? = null,
    val isCanGoToBasket: Boolean = false,
    val shortDescription: String? = null,
    val longDescription: String? = null,
    val images: MutableList<String>? = null,
    val company: String? = null,
    val color: String? = null,

    @PrimaryKey
    val id: Int

)

