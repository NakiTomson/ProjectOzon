package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.domain.mappers.MapperToDb.Companion.checkInFavorite
import com.app.marketPlace.domain.models.Product
import javax.inject.Inject

class MapperFromDb @Inject constructor() {

    fun mapBasketListDb(basketsList: List<BasketProductDb>?): List<Product>? {

        return basketsList?.map {

            Product(
                type = Product.Type.ProductWithName,
                icon = it.icon,
                isFavorite = checkInFavorite(it.id),
                discount = it.discount,
                isBestseller = it.isBestseller,
                priceMinusDiscount = it.priceMinusDiscount,
                price = it.price,
                isCanGoToBasket = it.isCanGoToBasket,
                name = it.name,
                shortDescription = it.shortDescription,
                longDescription = it.longDescription,
                images = it.images,
                company = it.company,
                color = it.color,
                skuId = it.id
            )
        }
    }


    fun mapFavoriteListDb(basketsList: List<FavoriteProductDb>?): List<Product>? {

        return basketsList?.map {

            Product(
                type = Product.Type.ProductWithName,
                icon = it.icon,
                isFavorite = checkInFavorite(it.id),
                discount = it.discount,
                isBestseller = it.isBestseller,
                priceMinusDiscount = it.priceMinusDiscount,
                price = it.price,
                isCanGoToBasket = it.isCanGoToBasket,
                name = it.name,
                shortDescription = it.shortDescription,
                longDescription = it.longDescription,
                images = it.images,
                company = it.company,
                color = it.color,
                skuId = it.id
            )
        }
    }
}