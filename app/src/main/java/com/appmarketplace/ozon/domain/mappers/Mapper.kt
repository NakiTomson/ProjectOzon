package com.appmarketplace.ozon.domain.mappers

import com.appmarketplace.ozon.data.remote.modelsAPI.Product
import com.appmarketplace.ozon.data.remote.modelsAPI.ProductsModel
import com.appmarketplace.ozon.data.remote.modelsDB.BasketProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.utils.Gonfigs.listImagesByCategory
import com.appmarketplace.ozon.domain.exception.NotFoundRealizationException
import com.appmarketplace.ozon.domain.modelsUI.*
import com.appmarketplace.ozon.presentation.activityes.MainActivity.Companion.listIdsBasket
import com.appmarketplace.ozon.presentation.activityes.MainActivity.Companion.listIdsFavorite
import com.appmarketplace.ozon.presentation.rowType.Resource
import java.text.DecimalFormat

class Mapper {


    fun mapProductDbToUi(productDb: BasketProductDb, type: OnProductItem.Type): OnProductItem {

        return  OnProductItem(
            type = type,
            generalIconProductSting = productDb.iconProduct,
            favoritelIconProduct = productDb.isFavorite,
            productDiscount = productDb.productDiscount,
            isBestseller = productDb.isBestseller,
            priceWithDiscount = productDb.priceWithDiscount,
            priceOlD = productDb.priceOlD,
            goToBasket = productDb.goToBasket,
            nameOfProduct = productDb.nameOfProduct,
            shortDescription = productDb.shortDescription,
            longDescription = productDb.longDescription,
            images = productDb.images,
            company = productDb.company,
            color = productDb.color,
            skuId = productDb.id
        )
    }

    fun mapBasketUiToDb(basket: OnProductItem):BasketProductDb{
        return BasketProductDb(
            nameOfProduct = basket.nameOfProduct,
            iconProduct = basket.generalIconProductSting,
            isFavorite = basket.favoritelIconProduct,
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

    fun mapProductUiToDb(product: OnProductItem):ProductDb{
        return ProductDb(
            nameOfProduct = product.nameOfProduct,
            iconProduct = product.generalIconProductSting,
            isFavorite = product.favoritelIconProduct,
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

    fun mapHintUiToDb(request: String): HintProductDB? {
        return HintProductDB(nameRequset = request)
    }

    fun mapProductFromApiToUiProduct(
        products: ProductsModel,
        topOffer:String = "",
        bottomOffer:String = "",
        requestName:String = "",
        type: OnProductItem.Type
    ):Resource<OnOfferProductsItem>{


        val data = OnOfferProductsItem(
            topStringOffer = topOffer,
            boltonStringOffer = bottomOffer,
            list = getUiProducts(products.products,type),
            requestName = requestName
        )

        return Resource(
            status = Resource.Status.COMPLETED,
            data = data,
            exception = null
        )
    }

    private fun getUiProducts(products: List<Product>?, type: OnProductItem.Type, ): List<OnProductItem>{

        val newProductsList: MutableList<OnProductItem> = ArrayList()

        products?.forEach {

            newProductsList.add(
                OnProductItem(
                    type = type,
                    generalIconProductSting = it.image,
                    favoritelIconProduct = checkInFavorite(it.sku ?:0 ),
                    productInBasket = checkInBasket(it.sku ?:0 ),
                    productDiscount = checkDiscountOnProduct(it.salePrice, it.regularPrice),
                    priceWithDiscount = it.salePrice.toString() + " $",
                    priceOlD = it.regularPrice.toString() + " $",
                    goToBasket = true,
                    nameOfProduct = it.name,
                    startData = it.startDate,
                    productNew = it.new,
                    activeForSale = it.active,
                    productTemplate = it.productTemplate,
                    shortDescription = it.shortDescription,
                    longDescription = it.longDescription,
                    images = getListImages(it),
                    company = it.manufacturer,
                    color = it.color,
                    skuId = it.sku!!,
                    categoryPath = it.categoryPath?.map { path->
                        CategoryPath(id = path.id, name = path.name)
                    }
                )
            )
        }

        return newProductsList
    }

    private fun checkInFavorite(skuId:Int):Boolean{

        listIdsBasket.forEach {
            if (it == skuId){
                return true
            }
        }
        return false
    }

    private fun checkInBasket(skuId:Int):Boolean{
        listIdsFavorite.forEach {
            if (it == skuId){
                return true
            }
        }
        return false
    }

    private fun getListImages(products: Product):List<String>{
        val imagesList:MutableList<String> = ArrayList()

        products.largeFrontImage?.let {
            imagesList.add(it.toString())
        }

        products.angleImage?.let {
            imagesList.add(it.toString())
        }

        products.backViewImage?.let {
            imagesList.add(it.toString())
        }

        products.leftViewImage?.let {
            imagesList.add(it.toString())
        }

        products.alternateViewsImage?.let {
            imagesList.add(it.toString())
        }

        return imagesList
    }

    private fun checkDiscountOnProduct(salePrice: Double?, regular: Double?): String? {

        try {
            return if (salePrice!! >= regular!!){
                null
            }else{
                val df = DecimalFormat("#.##")
                ("- ${df.format((1 - salePrice?.div(regular!!)).times(100))} %")
            }
        } catch (e: Exception) {
            throw NotFoundRealizationException("Match Price Discount Exception")
        }

    }


    fun mapCategoriesFromApiToUiCategories(category: GeneralCategory?): Resource<MutableList<MutableList<OnBoardingItem>>> {

        for ((index, i) in (0 until category?.categories!!.size).withIndex()) {
            category.categories[i].image = listImagesByCategory[index]
        }


        val data = if (category.categories.size <= 21) {
            mutableListOf(
                category.categories
                    .take(10)
                    .map {
                        OnBoardingItem(
                            title = it.name,
                            onBoardingImageUrl = it.image,
                            category = it.id
                        )
                    }.toMutableList(),

                category.categories
                    .takeLast(10)
                    .map {
                        OnBoardingItem(
                            title = it.name,
                            onBoardingImageUrl = it.image,
                            category = it.id
                        )
                    }.toMutableList()
            )
        } else {
            mutableListOf(
                category.categories.map {
                    OnBoardingItem(title = it.name, onBoardingImageUrl = it.image, category = it.id)
                }.toMutableList()
            )
        }

        return Resource(status = Resource.Status.COMPLETED, data = data , exception = null)
    }

    fun mapDbBasketToUi(basketsList: List<BasketProductDb>?): List<OnProductItem>? {

        return basketsList?.map {

            OnProductItem(
                type = OnProductItem.Type.ProductWithName,
                generalIconProductSting = it.iconProduct,
                favoritelIconProduct = it.isFavorite,
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
