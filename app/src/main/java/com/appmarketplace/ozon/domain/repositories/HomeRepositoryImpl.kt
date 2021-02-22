package com.appmarketplace.ozon.domain.repositories


import com.appmarketplace.ozon.data.utils.Gonfigs.CELL_PHONES
import com.appmarketplace.ozon.data.utils.Gonfigs.HOME_AUDIO
import com.appmarketplace.ozon.data.utils.Gonfigs.LAPTOPS
import com.appmarketplace.ozon.data.utils.Gonfigs.TVS
import com.appmarketplace.ozon.data.remote.services.*
import com.appmarketplace.ozon.domain.converters.GeneralCategoryConverter
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import com.appmarketplace.ozon.presentation.pojo.OnHistoryItem
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem

class HomeRepositoryImpl(
    val marketPlaceApi: ServerApi.MarketPlaceService,
    val converter: GeneralCategoryConverter
) : BaseRepository<HomeRepositoryImpl.Params, HomeRepositoryImpl.Results>(), HomeRepository {




    suspend fun getSearch(keyWord:String): Results.ResultProduct {
        return try {
            val products = marketPlaceApi.getProductsBySearch(keyword = keyWord,pageSize = "100",page = "1").await()
            Results.ResultProduct(
                converter.fromListProductToUiListProducts(products = products.products, type = 2)
            )
        } catch (e: Exception) {
            Results.ResultProduct(  Resource(status = Resource.Status.ERROR, data = null, exception = e))
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


    suspend fun getFirstProducts(pathId: String = HOME_AUDIO,pageSize :String = "3",apikey:String = APIKEY2,type:Int = 0):Results.ResultProduct {
        return try {
            val listProduts = marketPlaceApi.getProductsByCategory(pathId, pageSize, "1", apikey).await()
            Results.ResultProduct(converter.fromListProductToUiListProducts(products = listProduts.products, type = type))
        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }



    suspend fun getSecondProducts():Results.ResultProduct {
        return try {
            val listProducts =
                marketPlaceApi.getProductsByCategory(CELL_PHONES, "6", "54",
                APIKEY3).await()

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
            val listProducts = marketPlaceApi.getProductsByCategory(LAPTOPS, "3", "233",
                APIKEY4).await()
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
            val listProduts = marketPlaceApi.getProductsByCategory(TVS, "4", "23", APIKEY5).await()
            Results.ResultProduct( converter.fromListProductToUiListProducts(
                topStringOffer = "Товары с шок-кешбеком по Ozon.Card",
                bottonStringOffer = "Больше товаров тут",
                products = listProduts.products,
                type = 1
            ))
        } catch (e: Exception) {
            Results.ResultProduct(  Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    class Params{

    }

    sealed class Results {
        data class ResultCategoryProduct(val result: Resource<MutableList<MutableList<OnBoardingItem>>>):Results()
        data class ResultBanner(val result : Resource<MutableList<OnBoardingItem>>):Results()
        data class ResultHistory(val result : Resource<OnHistoryItem>):Results()
        data class ResultLive(val result : Resource<OnLiveItem>):Results()
        data class ResultProduct(val result : Resource<OnOfferProductsItem>):Results()
    }
}