package com.appmarketplace.ozon.domain.converters

import android.util.Log
import com.appmarketplace.ozon.data.remote.models.Product
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.GeneralCategory
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem
import com.appmarketplace.ozon.presentation.pojo.OnProductItem
import java.text.DecimalFormat


class GeneralCategoryConverter{


    fun fromListOnCategoryToListUICategory(listGeneral: GeneralCategory?): Resource<MutableList<MutableList<OnBoardingItem>>> {

        val listCategoryImage = listOf<String>(
            "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=1",
            "https://www.dropbox.com/s/3oh2dtpcais3pmm/two.png?dl=1",
            "https://www.dropbox.com/s/s21jitjxbehsu6m/three.png?dl=1",
            "https://www.dropbox.com/s/rp58pn5csppxveg/foure.png?dl=1",
            "https://www.dropbox.com/s/dwsgfil13yaqfl6/five.png?dl=1",
            "https://www.dropbox.com/s/tp5dn122l4fxisz/six.png?dl=1",
            "https://www.dropbox.com/s/ral28ayfirfeq08/seven.png?dl=1",
            "https://www.dropbox.com/s/iweu5xow84uzw0q/eight.png?dl=1",
            "https://www.dropbox.com/s/da22ge77utrs7gk/nine.png?dl=1",
            "https://www.dropbox.com/s/al1j3eqjqwgieda/ten.png?dl=1",
            "https://www.dropbox.com/s/3z9sqdui2zztnws/one.png?dl=1",
            "https://www.dropbox.com/s/3oh2dtpcais3pmm/two.png?dl=1",
            "https://www.dropbox.com/s/s21jitjxbehsu6m/three.png?dl=1",
            "https://www.dropbox.com/s/rp58pn5csppxveg/foure.png?dl=1",
            "https://www.dropbox.com/s/dwsgfil13yaqfl6/five.png?dl=1",
            "https://www.dropbox.com/s/tp5dn122l4fxisz/six.png?dl=1",
            "https://www.dropbox.com/s/ral28ayfirfeq08/seven.png?dl=1",
            "https://www.dropbox.com/s/iweu5xow84uzw0q/eight.png?dl=1",
            "https://www.dropbox.com/s/da22ge77utrs7gk/nine.png?dl=1",
            "https://www.dropbox.com/s/al1j3eqjqwgieda/ten.png?dl=1"
        )


        for ((idex, i) in (0 until listGeneral?.categories!!.size).withIndex()){
            listGeneral!!.categories[i].image = listCategoryImage[idex]
        }


        val allData =
            mutableListOf(listGeneral?.categories?.take(10)!!.map {
                OnBoardingItem(
                    title = it.name,
                    onBoardingImageUrl = it.image
                )
            }.toMutableList(),
                listGeneral?.categories?.takeLast(10)!!.map {
                    OnBoardingItem(
                        title = it.name,
                        onBoardingImageUrl = it.image
                    )
                }.toMutableList()
            )

        val allDataCategoryList =
            Resource(status = Resource.Status.COMPLETED, data = allData, exception = null)


        return allDataCategoryList
    }



    fun fromListProductToUiListProducts(
        topStringOffer: String? = null,
        bottonStringOffer: String? = null,
        products: List<Product>?,
        type: Int
    ):Resource<MutableList<OnOfferProductsItem>>{

        return Resource(
            status = Resource.Status.COMPLETED,
            data =
            mutableListOf(
                OnOfferProductsItem(
                    topStringOffer = topStringOffer,
                    bottonStringOffer = bottonStringOffer,
                    list = facToryType(type, products)
                )
            )
        )

    }

    fun facToryType(type: Int, products: List<Product>?):List<OnProductItem>{
        return when (type) {
            0 -> {
                return getTypeOne(products)
            }
            1 -> {
                getTypeTwo(products)
            }
            else -> throw Exception("Non Type")
        }
    }

    fun getTypeOne(products: List<Product>?): List<OnProductItem> {

        val list: MutableList<OnProductItem> = ArrayList()

        products?.forEach {
            list.add(
                OnProductItem(
                    generalIconProductSting = it.image!!
                )
            )
        }

        return list
    }

    fun getTypeTwo(products: List<Product>?): List<OnProductItem> {

        val list: MutableList<OnProductItem> = ArrayList()

        products?.forEach {

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


    fun getDiscount(salePrice: Double?, regular: Double?): String? {
        try {
            return if (salePrice!! >= regular!!){
                null
            }else{
                val df = DecimalFormat("#.##")
                ("- ${df.format((1 - salePrice?.div(regular!!)).times(100))} %")
            }
        } catch (e: Exception) {
            throw Exception("Price Exception")
        }
    }
}