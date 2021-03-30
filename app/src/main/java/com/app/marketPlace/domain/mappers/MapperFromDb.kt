package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.domain.mappers.MapperToDb.Companion.checkInFavorite
import com.app.marketPlace.domain.models.ProductItem
import javax.inject.Inject

class MapperFromDb @Inject constructor() {

    fun mapListBasketDb(basketsList: List<BasketProductDb>?): List<ProductItem>? {

        return basketsList?.map {

            ProductItem(
                type = ProductItem.Type.ProductWithName,
                generalIconProductSting = it.iconProduct,
                favoriteIconProduct = checkInFavorite(it.id),
                productDiscount = it.productDiscount,
                isBestseller = it.isBestseller,
                priceWithDiscount = it.priceWithDiscount,
                priceOlD = it.priceOlD,
                goToBasket = it.goToBasket,
                nameOfProduct = it.nameOfProduct,
                shortDescription = it.shortDescription,
                longDescription = it.longDescription,
                images = it.images,
                company = it.company,
                color = it.color,
                skuId = it.id
            )
        }
    }


    fun mapListFavoriteDB(basketsList: List<FavoriteProductDb>?): List<ProductItem>? {

        return basketsList?.map {

            ProductItem(
                type = ProductItem.Type.ProductWithName,
                generalIconProductSting = it.iconProduct,
                favoriteIconProduct = checkInFavorite(it.id),
                productDiscount = it.productDiscount,
                isBestseller = it.isBestseller,
                priceWithDiscount = it.priceWithDiscount,
                priceOlD = it.priceOlD,
                goToBasket = it.goToBasket,
                nameOfProduct = it.nameOfProduct,
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