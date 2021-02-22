package com.appmarketplace.ozon.domain.mappers

import com.appmarketplace.ozon.data.remote.models.Product
import com.appmarketplace.ozon.data.remote.models.ProductsModel
import com.appmarketplace.ozon.domain.exception.NotFoundRealizationException
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem
import com.appmarketplace.ozon.presentation.pojo.OnProductItem
import java.text.DecimalFormat

class MapProductsToListData<T,M>(
    val type: Int = 0,
    val topOffer:String = "",
    val bottomOffer:String = "",
    val requestName:String = ""

) :MapperUI<T,M>{

    override fun map(list: M): Resource<T> {

        list as  ProductsModel

        val data =  OnOfferProductsItem(
            topStringOffer = topOffer,
            bottonStringOffer = bottomOffer,
            list = typeFactory(type, list.products!!),
            requestName = requestName
        )

        return Resource(
            status = Resource.Status.COMPLETED,
            data = data as T,
            exception = null
        )
    }

    private fun typeFactory(type: Int, products: List<Product>): List<OnProductItem> {
        return when (type) {
            0 -> productsWithOnlyImages(products)
            1 -> productsBase(products)
            2 -> productsBasePlusName(products)
            else -> throw NotFoundRealizationException("Non Type in factory")
        }
    }

    private fun productsWithOnlyImages(products: List<Product>): List<OnProductItem> {
        val list: MutableList<OnProductItem> = ArrayList()
        products.forEach {
            list.add(OnProductItem(generalIconProductSting = it.image))
        }
        return list
    }

    private fun productsBase(products: List<Product>): List<OnProductItem> {
        val list: MutableList<OnProductItem> = ArrayList()

        products.forEach {
            list.add(
                OnProductItem(
                    generalIconProductSting = it.image,
                    favoritelIconProduct = true,
                    productDiscount = getDiscount(it.salePrice, it.regularPrice),
                    priceWithDiscount = it.salePrice.toString() + " $",
                    priceOlD = it.regularPrice.toString() + " $",
                    goToBasket = true
                )
            )
        }
        return list
    }

    private fun productsBasePlusName(products: List<Product>): List<OnProductItem> {
        val list: MutableList<OnProductItem> = ArrayList()

        products.forEach {

            list.add(
                OnProductItem(
                    generalIconProductSting = it.image,
                    favoritelIconProduct = true,
                    productDiscount = getDiscount(it.salePrice, it.regularPrice),
                    priceWithDiscount = it.salePrice.toString() + " $",
                    priceOlD = it.regularPrice.toString() + " $",
                    goToBasket = true,
                    nameOfProduct = it.name
                )
            )
        }

        return list
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