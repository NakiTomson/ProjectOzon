package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.data.db.models.HintProductDb
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.presentation.activities.MainViewModel
import javax.inject.Inject

class MapperToDb @Inject constructor() {

    fun mapBasketUi(basket: ProductItem):BasketProductDb{
        return BasketProductDb(
            nameOfProduct = basket.nameOfProduct,
            iconProduct = basket.generalIconProductSting,
            isFavorite = basket.favoriteIconProduct,
            productDiscount = basket.productDiscount,
            isBestseller = basket.isBestseller,
            priceWithDiscount = basket.priceWithDiscount,
            priceOlD = basket.priceOlD,
            goToBasket = basket.goToBasket,
            shortDescription = basket.shortDescription,
            longDescription = basket.longDescription,
            images = basket.images,
            company = basket.company,
            color = basket.color,
            id = basket.skuId)
    }

    fun mapFavoriteUi(product: ProductItem):FavoriteProductDb{
        return FavoriteProductDb(
            nameOfProduct = product.nameOfProduct,
            iconProduct = product.generalIconProductSting,
            isFavorite = product.favoriteIconProduct,
            productDiscount = product.productDiscount,
            isBestseller = product.isBestseller,
            priceWithDiscount = product.priceWithDiscount,
            priceOlD = product.priceOlD,
            goToBasket = product.goToBasket,
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

        fun reMapProduct(products: ProductItem):ProductItem {
            products.favoriteIconProduct = checkInFavorite(products.skuId)
            products.productInBasket = checkInBasket(products.skuId)
            return products
        }
    }
}