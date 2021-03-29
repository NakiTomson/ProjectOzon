package com.app.marketPlace.domain.mappers

import com.app.marketPlace.data.remote.models.*
import com.app.marketPlace.domain.exception.NotFoundRealizationException
import com.app.marketPlace.domain.exception.NotMappedException
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.presentation.rowType.Resource
import java.text.DecimalFormat

class MapperProducts : IMapper {

    override fun <T> map(data: T?, params: Params): Resource<*> {
        if (data !is ProductsList?){
            return Resource(status = Resource.Status.COMPLETED, data = null ,
                exception = NotMappedException(data),
                type = params.resourceType)
        }

        if (data == null || data.products.isNullOrEmpty()){
            return Resource(status = Resource.Status.EMPTY, data = null ,
                exception = null,
                type = params.resourceType)
        }

        val item = CombineProductsItem(
            topOffer = params.topOffer,
            bottomOffer = params.bottomOffer,
            list = getUiProducts(data.products, params.typeProduct),
            requestName = params.requestName,
            spain = params.spain
        )

        return Resource(
            status = Resource.Status.COMPLETED,
            data = item,
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
}