package com.appmarketplace.ozon.data.remote.modelsDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.appmarketplace.ozon.domain.modelsUI.CategoryPath
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity(tableName = "products")
@TypeConverters(Converters::class)
data class ProductDb(


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

//    val categoryPath: List<CategoryPath>? = null,

    @PrimaryKey
    val id: Int
) {

}



public class Converters {

    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType: Type? = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

//    @TypeConverter
//    fun fromTimestamp(value: String): List<CategoryPath?> {
//        val listType: Type? = object : TypeToken<List<String>>() {}.type
//        return Gson().fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: List<CategoryPath>?): String? {
//        if (date== null || date.isEmpty()) {
//            return (null);
//        }
//        val gson = Gson()
//        val type: Type = object : TypeToken<List<CategoryPath>>() {}.type
//        val json: String = gson.toJson(date, type)
//
//        return json
//    }


}
