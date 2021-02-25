package com.appmarketplace.ozon.domain.mappers

import com.appmarketplace.ozon.data.remote.modelsAPI.Product
import com.appmarketplace.ozon.data.remote.modelsAPI.ProductsModel
import com.appmarketplace.ozon.domain.exception.NotFoundRealizationException
import com.appmarketplace.ozon.domain.modelsUI.CategoryPath
import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.OnOfferProductsItem
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem.Type
import java.text.DecimalFormat

class MapProductsToListData<T,M>(
    val type: Type =Type.OnlyImage,
    val topOffer:String = "",
    val bottomOffer:String = "",
    val requestName:String = ""

) :MapperUI<T,M>{

    override fun map(list: M): Resource<T> {

        list as  ProductsModel

        val data = list.products?.let { getProducts(it) }?.let {
            OnOfferProductsItem(
                topStringOffer = topOffer,
                bottonStringOffer = bottomOffer,
                list = it,
                requestName = requestName
            )
        }

        return Resource(
            status = Resource.Status.COMPLETED,
            data = data as T,
            exception = null
        )
    }

    private fun getProducts(products: List<Product>): List<OnProductItem>{
        val list: MutableList<OnProductItem> = ArrayList()

        products.forEach {



            list.add(
                OnProductItem(
                    type = type,
                    generalIconProductSting = it.image,
                    favoritelIconProduct = true,
                    productDiscount = getDiscount(it.salePrice, it.regularPrice),
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
                    images = getlistImages(it),
                    company = it.manufacturer,
                    color = it.color,
                    categoryPath = it.categoryPath?.map { path->
                        CategoryPath(id = path.id, name = path.name)
                    }
                )
            )
        }

        return list
    }

    fun getlistImages(products: Product):List<String>{
        val imagerList:MutableList<String> = ArrayList()

        products.largeFrontImage?.let {
            imagerList.add(it.toString())
        }

        products.angleImage?.let {
            imagerList.add(it.toString())
        }

        products.backViewImage?.let {
            imagerList.add(it.toString())
        }

        products.leftViewImage?.let {
            imagerList.add(it.toString())
        }

        products.alternateViewsImage?.let {
            imagerList.add(it.toString())
        }

       return imagerList
    }

    fun getDiscount(salePrice: Double?, regular: Double?): String? {

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

}