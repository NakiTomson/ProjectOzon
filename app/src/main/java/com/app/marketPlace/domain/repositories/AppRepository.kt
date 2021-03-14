package com.app.marketPlace.domain.repositories


import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.data.remote.services.*
import com.app.marketPlace.data.utils.ConstantsApp
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.presentation.rowType.Resource
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.domain.models.LiveStreamItem
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.models.ProductItem

class AppRepository(private val marketPlaceApi: MarketPlaceService) {


    fun getBannerStart(): Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/l5t0828ivc6eb79/oneAdsBanner.jpg?dl=1"),
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/scqgvaj8vk7omti/twoAdsBanner.jpg?dl=1"),
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/n0bb1qr1n4caci4/threeAdsBanner.jpg?dl=1")
                    )
                )
            )
        } catch (e: Exception) {
            Results.ResultBanner(  Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }



    suspend fun loadCategories(params: Params): Results.ResultCategoryProduct {
        return try {

            val listGeneralCategory = marketPlaceApi
                .getCategoryProductsAsync(params.pageSize, params.page,params.apiKey)
                .await()

            Results.ResultCategoryProduct(
                Mapper.MapperToUi.mapCategoriesFromServer(listGeneralCategory)
            )
        } catch (e: Exception) {
            Results.ResultCategoryProduct(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun loadSearchProducts(params: Params): Results.ResultProduct {
        return try {

            val listProducts = marketPlaceApi
                .getProductsBySearchAsync(params.pathId, params.pageSize, params.page, params.apiKey).await()

            Results.ResultProduct(
                Mapper.MapperToUi.mapProductsFromServer(listProducts, type = params.typeProduct,requestName = params.requestName)
            )

        } catch (e: Exception) {
            Results.ResultProduct(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun loadProducts(params: Params):Results.ResultProduct {
        return try {

            val listProducts = marketPlaceApi
                .getProductsByCategoryAsync(params.pathId, params.pageSize, params.page, params.apiKey)
                .await()

            Results.ResultProduct(
                Mapper.MapperToUi.mapProductsFromServer(
                    listProducts,
                    type = params.typeProduct,
                    topOffer = params.topOffer,
                    bottomOffer = params.bottomOffer,
                    requestName = params.requestName
                )
            )

        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }



    suspend fun getHistoryItems(): Results.ResultHistory {
        return try {
            Results.ResultHistory(Resource(status = Resource.Status.COMPLETED, data = marketPlaceApi.getHistoryAsync().await()))
        } catch (e: Exception) {
            Results.ResultHistory(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun getLiveItems():Results.ResultLive {
        return try {
            Results.ResultLive(Resource(status = Resource.Status.COMPLETED, data = marketPlaceApi.getLivesAsync().await()))
        } catch (e: Exception) {
            Results.ResultLive( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }



    fun getBannerCenter(): Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/l5t0828ivc6eb79/oneAdsBanner.jpg?dl=1"),
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/scqgvaj8vk7omti/twoAdsBanner.jpg?dl=1"),
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/n0bb1qr1n4caci4/threeAdsBanner.jpg?dl=1")
                    )
                )
            )
        } catch (e: Exception) {
            Results.ResultBanner(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    fun getBannerDown():Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(Banner(onBoardingImageUrl = "https://www.dropbox.com/s/zkgiislgopkjjrh/exzample_banner.jpg?dl=1"))
                )
            )
        } catch (e: Exception) {
            Results.ResultBanner(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}


sealed class Params(
    open var pathId: String = ConstantsApp.HOME_AUDIO,
    open var pageSize: String = "3",
    open var apiKey: String = ConstantsApp.APIKEY2,
    open var page: String = "1",
    open var topOffer: String = "",
    open var bottomOffer: String = "",
    open var requestName: String = "",
    open var typeProduct: ProductItem.Type = ProductItem.Type.OnlyImage
){

    class CategoriesProductParams(
        override var pageSize: String = "3",
        override var apiKey: String = ConstantsApp.APIKEY1,
        override var page: String = "1"
    ) : Params()


    class ProductsParams(
        override var pathId: String = ConstantsApp.HOME_AUDIO,
        override var pageSize: String = "3",
        override var apiKey: String = ConstantsApp.APIKEY2,
        override var page: String = "1",
        override var topOffer: String = "",
        override var bottomOffer: String = "",
        override var requestName: String = "",
        override var typeProduct: ProductItem.Type,
    ) : Params()
}


sealed class Results {
    data class ResultCategoryProduct(val result: Resource<MutableList<MutableList<Banner>>>):Results()
    data class ResultBanner(val result : Resource<MutableList<Banner>>):Results()
    data class ResultHistory(val result : Resource<Stories>):Results()
    data class ResultLive(val result : Resource<LiveStreamItem>):Results()
    data class ResultProduct(val result : Resource<CombineProductsItem>):Results()
}