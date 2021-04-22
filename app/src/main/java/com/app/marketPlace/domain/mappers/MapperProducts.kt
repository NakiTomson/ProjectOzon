package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.remote.models.*
import com.app.marketPlace.domain.exception.NotFoundRealizationException
import com.app.marketPlace.domain.exception.NotFoundMappedException
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource
import java.text.DecimalFormat

class MapperProducts : Mapper {

    override fun <T> map(data: T?, params: Params): Resource<*> {
        if (data !is ProductsList?){
            return Resource(status = Resource.Status.COMPLETED, data = null ,
                exception = NotFoundMappedException(data))
        }

        if (data == null || data.products.isNullOrEmpty()){
            return Resource(status = Resource.Status.EMPTY, data = null ,
                exception = null)
        }

        val item = CombineProducts(
            topOffer = params.topOffer,
            bottomOffer = params.bottomOffer,
            list = getUiProducts(data.products, params.typeProduct),
            requestName = params.requestName,
            spain = params.spain
        )

        return Resource(
            status = Resource.Status.COMPLETED,
            data = item,
            exception = null
        )
    }


    fun mapProduct(data: List<ProductOfServer>?, params: Params): Resource<CombineProducts> {

        if (data.isNullOrEmpty()){
            return Resource(
                status = Resource.Status.COMPLETED,
                data = null,
                exception = null
            )
        }

        val item = CombineProducts(
            topOffer = params.topOffer,
            bottomOffer = params.bottomOffer,
            list = getUiProducts(data, params.typeProduct),
            requestName = params.requestName,
            spain = params.spain
        )

        return Resource(
            status = Resource.Status.COMPLETED,
            data = item,
            exception = null
        )
    }


    private fun getUiProducts(products: List<ProductOfServer>?, type: Product.Type) : List<Product>{

        val newProductsList: MutableList<Product> = ArrayList()

        products?.forEach {

            newProductsList.add(

                Product(
                    type = type,
                    icon = it.image,
                    isFavorite = false,
                    isBasket = false,
                    discount = checkDiscountOnProduct(it.salePrice, it.regularPrice),
                    priceMinusDiscount = it.salePrice.toString() + " $",
                    price = it.regularPrice.toString() + " $",
                    isCanGoToBasket = true,
                    name = it.name,
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
                        Categories(id = path.id, name = path.name)
                    }
                )
            )
        }

        return newProductsList
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

    private fun getListImages(products: ProductOfServer):MutableList<String>{
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
}