package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.remote.models.Product
import com.app.marketPlace.data.remote.models.Products
import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.HintProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.CategoriesProduct
import com.app.marketPlace.data.utils.ConstantsApp.listImagesByCategory
import com.app.marketPlace.domain.exception.NotFoundRealizationException
import com.app.marketPlace.domain.models.*
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.activities.MainViewModel.Companion.listIdsBasket
import com.app.marketPlace.presentation.activities.MainViewModel.Companion.listIdsFavorite
import com.app.marketPlace.presentation.rowType.Resource
import java.text.DecimalFormat

object Mapper {

    public object MapperToUi{

        fun mapBasketDb(productDb: BasketProductDb, type: ProductItem.Type): ProductItem {

            return ProductItem(
                type = type,
                generalIconProductSting = productDb.iconProduct,
                favoriteIconProduct = productDb.isFavorite,
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

        fun mapProductsFromServer(
            products: Products,
            params: Params
        ):Resource<CombineProductsItem>{


            val data = CombineProductsItem(
                topOffer = params.topOffer,
                bottomOffer = params.bottomOffer,
                list = getUiProducts(products.products,params.typeProduct),
                requestName = params.requestName,
                spain = params.spain
            )

            return Resource(
                status = Resource.Status.COMPLETED,
                data = data,
                exception = null,
                type = params.resourceType
            )
        }


        private fun getUiProducts(products: List<Product>?, type: ProductItem.Type) : List<ProductItem>{

            val newProductsList: MutableList<ProductItem> = ArrayList()

            products?.forEach {

                newProductsList.add(
                    ProductItem(
                        type = type,
                        generalIconProductSting = it.image,
                        favoriteIconProduct = false,
                        productInBasket = false,
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

        fun checkInFavorite(skuId:Int):Boolean{

            listIdsFavorite.forEach {
                if (it == skuId){
                    return true
                }
            }
            return false
        }

        fun checkInBasket(skuId:Int):Boolean{
            listIdsBasket.forEach {
                if (it == skuId){
                    return true
                }
            }
            return false
        }

        private fun getListImages(products: Product):MutableList<String>{
            val imagesList:MutableList<String> = ArrayList()

            products.largeFrontImage?.let {
                imagesList.add(it.toString())
            }

            products.angleImage?.let {
                imagesList.add(it)
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
                    ("- ${df.format((1 - salePrice.div(regular)).times(100))} %")
                }
            } catch (e: Exception) {
                throw NotFoundRealizationException("Match Price Discount Exception")
            }
        }


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


        fun mapCategoriesFromServer(category: CategoriesProduct?, params: Params): Resource<MutableList<MutableList<Banner>>> {

            for ((index, i) in (0 until category?.categories!!.size).withIndex()) {
                category.categories[i].image = listImagesByCategory[index]
            }


            val data = if (category.categories.size <= 21) {
                mutableListOf(
                    category.categories
                        .take(10)
                        .map {
                            Banner(
                                title = it.name,
                                onBoardingImageUrl = it.image,
                                category = it.id
                            )
                        }.toMutableList(),

                    category.categories
                        .takeLast(10)
                        .map {
                            Banner(
                                title = it.name,
                                onBoardingImageUrl = it.image,
                                category = it.id
                            )
                        }.toMutableList()
                )
            } else {
                mutableListOf(
                    category.categories.map {
                        Banner(title = it.name, onBoardingImageUrl = it.image, category = it.id)
                    }.toMutableList()
                )
            }

            return Resource(status = Resource.Status.COMPLETED, data = data , exception = null,type = params.resourceType)
        }

    }


    object MapperToDb{

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

    }


    fun reMapProduct(products: ProductItem):ProductItem {
        products.favoriteIconProduct = MapperToUi.checkInFavorite(products.skuId)
        products.productInBasket = MapperToUi.checkInBasket(products.skuId)
        return products
    }

}
