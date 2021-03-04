package com.appmarketplace.ozon.data.remote.modelsDB

import androidx.room.*
import com.appmarketplace.ozon.domain.modelsUI.CategoryPath
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


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

    val images: List<String>? = null,

    val company: String? = null,


    val color: String? = null,

    @PrimaryKey
    val id: Int
) {

}


