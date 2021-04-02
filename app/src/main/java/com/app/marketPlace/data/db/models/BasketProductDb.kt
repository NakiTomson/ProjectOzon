package com.app.marketPlace.data.db.models

import androidx.room.*
import com.app.marketPlace.data.db.converters.ProductConverter


@Entity(tableName = "basket")
@TypeConverters(ProductConverter::class)
data class BasketProductDb(
    var type: Int? = null,
    val name: String? = null,
    val icon: String? = null,
    val isFavorite: Boolean = false,
    val discount: String? = null,  // Discount price
    val isBestseller: Boolean = false,
    val priceMinusDiscount: String? = null, // Result price Minus Discount
    val price: String? = null, // Def price
    val isCanGoToBasket: Boolean = false,
    val shortDescription: String? = null,
    val longDescription: String? = null,
    val images: MutableList<String>? = null,
    val company: String? = null,
    var color: String? = null,

    @PrimaryKey
    val id: Int
)


