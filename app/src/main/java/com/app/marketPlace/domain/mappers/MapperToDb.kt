package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.data.db.models.HintProductDb
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.presentation.activities.MainViewModel
import javax.inject.Inject

class MapperToDb @Inject constructor() {

    fun mapBasketUi(basket: Product):BasketProductDb{
        return BasketProductDb(
            name = basket.name,
            icon = basket.icon,
            isFavorite = basket.isFavorite,
            discount = basket.discount,
            isBestseller = basket.isBestseller,
            priceMinusDiscount = basket.priceMinusDiscount,
            price = basket.price,
            isCanGoToBasket = basket.isCanGoToBasket,
            shortDescription = basket.shortDescription,
            longDescription = basket.longDescription,
            images = basket.images,
            company = basket.company,
            color = basket.color,
            id = basket.skuId)
    }

    fun mapFavoriteUi(product: Product):FavoriteProductDb{
        return FavoriteProductDb(
            name = product.name,
            icon = product.icon,
            isFavorite = product.isFavorite,
            discount = product.discount,
            isBestseller = product.isBestseller,
            priceMinusDiscount = product.priceMinusDiscount,
            price = product.price,
            isCanGoToBasket = product.isCanGoToBasket,
            shortDescription = product.shortDescription,
            longDescription = product.longDescription,
            images = product.images,
            company = product.company,
            color = product.color,
            id = product.skuId)
    }

    fun mapHintUi(request: String): HintProductDb {
        return HintProductDb(nameRequest = request)
    }

    companion object{
        fun checkInFavorite(skuId:Int):Boolean{
            MainViewModel.listIdsFavorite.forEach {
                if (it == skuId){
                    return true
                }
            }
            return false
        }

        private fun checkInBasket(skuId:Int):Boolean{
            MainViewModel.listIdsBasket.forEach {
                if (it == skuId){
                    return true
                }
            }
            return false
        }

        fun reMapProduct(products: Product):Product {
            products.isFavorite = checkInFavorite(products.skuId)
            products.isBasket = checkInBasket(products.skuId)
            return products
        }
    }
}