package com.appmarketplace.ozon.domain.repositories


import android.util.Log
import com.appmarketplace.ozon.data.db.Gonfigs.CELL_PHONES
import com.appmarketplace.ozon.data.db.Gonfigs.HOME_AUDIO
import com.appmarketplace.ozon.data.db.Gonfigs.LAPTOPS
import com.appmarketplace.ozon.data.db.Gonfigs.TVS
import com.appmarketplace.ozon.data.remote.models.ProductsModel
import com.appmarketplace.ozon.data.remote.services.ServerApi
import com.appmarketplace.ozon.domain.converters.GeneralCategoryConverter
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import com.appmarketplace.ozon.presentation.pojo.OnHistoryItem
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem
import java.io.IOException

class HomeRepositoryImpl(
    val marketPlaceApi: ServerApi.MarketPlaceService,
    val converter: GeneralCategoryConverter
) : BaseRepository<HomeRepositoryImpl.Params, HomeRepositoryImpl.Results>(), HomeRepository {



    suspend fun testLoading(keyWord:String): Results.ResultProduct {

        try {
            val products = marketPlaceApi.getSearchProductsKey1(keyword = keyWord,pageSize = "100",page = "1").await()
            return Results.ResultProduct(
                converter.fromListProductToUiListProducts(products = products.products, type = 2)
            )
        } catch (e: Exception) {
           return Results.ResultProduct(  Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun getBannerStart(): Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(
                        OnBoardingItem(onBoardingImageUrl = "https://www.dropbox.com/s/l5t0828ivc6eb79/oneAdsBanner.jpg?dl=1"),
                        OnBoardingItem(onBoardingImageUrl = "https://www.dropbox.com/s/scqgvaj8vk7omti/twoAdsBanner.jpg?dl=1"),
                        OnBoardingItem(onBoardingImageUrl = "https://www.dropbox.com/s/n0bb1qr1n4caci4/threeAdsBanner.jpg?dl=1")
                    )
                )
            )
        } catch (e: Exception) {
            Results.ResultBanner(  Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    override suspend fun loadDataCategoryProduct(params: Params): Results.ResultCategoryProduct {
        return try {
            val listGeneralCategory = marketPlaceApi.getCategoryProducts("20", "1").await()
            Results.ResultCategoryProduct(converter.fromListOnCategoryToListUICategory(listGeneralCategory))
        } catch (e: Exception) {
            Results.ResultCategoryProduct(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun getHistoryItems(): Results.ResultHistory {
        return try {
            Results.ResultHistory(Resource(status = Resource.Status.COMPLETED, data = marketPlaceApi.getHistorys().await()))
        } catch (e: Exception) {
            Results.ResultHistory(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun getLiveItems():Results.ResultLive {
        return try {
            Results.ResultLive(Resource(status = Resource.Status.COMPLETED, data = marketPlaceApi.getLives().await()))
        } catch (e: Exception) {
            Results.ResultLive( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun getFirstProducts():Results.ResultProduct {
        return try {
            Thread.sleep(5300)
            val listProduts = marketPlaceApi.getThreeProductsByCategoryKey1(HOME_AUDIO, "3", "1").await()
            Results.ResultProduct(
                converter.fromListProductToUiListProducts(products = listProduts.products, type = 0)
            )
        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }

    }



    suspend fun getSecondProducts():Results.ResultProduct {
        Thread.sleep(0)
        return try {
            val listProducts = marketPlaceApi.getThreeProductsByCategoryKey2(CELL_PHONES, "6", "54").await()
            Results.ResultProduct(
                converter.fromListProductToUiListProducts(
                    "Лучшие предложения!",
                    "Скидки до  80 % здесь!",
                    listProducts?.products,
                    type = 1
                )
            )
        } catch (e: Exception) {
            Results.ResultProduct(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun getBannerCenter(): Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(
                        OnBoardingItem(onBoardingImageUrl = "https://www.dropbox.com/s/l5t0828ivc6eb79/oneAdsBanner.jpg?dl=1"),
                        OnBoardingItem(onBoardingImageUrl = "https://www.dropbox.com/s/scqgvaj8vk7omti/twoAdsBanner.jpg?dl=1"),
                        OnBoardingItem(onBoardingImageUrl = "https://www.dropbox.com/s/n0bb1qr1n4caci4/threeAdsBanner.jpg?dl=1")
                    )
                )
            )
        } catch (e: Exception) {
            Results.ResultBanner(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun getThirdProducts():Results.ResultProduct {
        return try {
            Thread.sleep(10300)
            val listProducts = marketPlaceApi.getThreeProductsByCategoryKey1(LAPTOPS, "3", "233").await()
            Results.ResultProduct(
                converter.fromListProductToUiListProducts(
                    topStringOffer = "Крутые Скидки! 90%",
                    products = listProducts?.products,
                    type = 1
                )
            )
        } catch (e: Exception) {
            Results.ResultProduct(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun getBannerDown():Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(OnBoardingItem(onBoardingImageUrl = "https://www.dropbox.com/s/zkgiislgopkjjrh/exzample_banner.jpg?dl=1"))
                )
            )
        } catch (e: Exception) {
            Results.ResultBanner(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun getFourthProducts():Results.ResultProduct {
        return try {
            Thread.sleep(11000)
            val listProduts = marketPlaceApi.getThreeProductsByCategoryKey2(TVS, "4", "23").await()
            Results.ResultProduct( converter.fromListProductToUiListProducts(
                topStringOffer = "Товары с шок-кешбеком по Ozon.Card",
                bottonStringOffer = "Больше товаров тут",
                products = listProduts?.products,
                type = 1
            ))
        } catch (e: Exception) {
            Results.ResultProduct(  Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    class Params

    sealed class Results {
        data class ResultCategoryProduct(val result: Resource<MutableList<MutableList<OnBoardingItem>>>):Results()
        data class ResultBanner(val result : Resource<MutableList<OnBoardingItem>>):Results()
        data class ResultHistory(val result : Resource<OnHistoryItem>):Results()
        data class ResultLive(val result : Resource<OnLiveItem>):Results()
        data class ResultProduct(val result : Resource<MutableList<OnOfferProductsItem>>):Results()
    }
}