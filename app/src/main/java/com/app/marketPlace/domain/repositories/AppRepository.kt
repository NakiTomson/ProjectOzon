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




    suspend fun getBannerStart(params: Params): Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/l5t0828ivc6eb79/oneAdsBanner.jpg?dl=1"),
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/scqgvaj8vk7omti/twoAdsBanner.jpg?dl=1"),
                        Banner(onBoardingImageUrl = "https://www.dropbox.com/s/n0bb1qr1n4caci4/threeAdsBanner.jpg?dl=1")
                    ),
                    type = params.resourceType
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
                Mapper.MapperToUi.mapCategoriesFromServer(listGeneralCategory,params)
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
                Mapper.MapperToUi.mapProductsFromServer(listProducts, params)
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
                    params = params
                )
            )

        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }



    suspend fun getHistoryItems(storiesParams: Params): Results.ResultHistory {
        return try {
            Results.ResultHistory(Resource(status = Resource.Status.COMPLETED,
                data = marketPlaceApi.getHistoryAsync().await(),
                type = storiesParams.resourceType)
            )
        } catch (e: Exception) {
            Results.ResultHistory(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun getLiveItems(liveParams: Params):Results.ResultLive {
        return try {
            Results.ResultLive(Resource(status = Resource.Status.COMPLETED,
                data = marketPlaceApi.getLivesAsync().await(),
                type = liveParams.resourceType)
            )
        } catch (e: Exception) {
            Results.ResultLive( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }



    suspend fun getBannerCenter(params: Params): Results.ResultBanner {
        return getBannerStart(params)
    }

    fun getBannerDown(params: Params):Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(Banner(onBoardingImageUrl = "https://www.dropbox.com/s/zkgiislgopkjjrh/exzample_banner.jpg?dl=1")),
                    type = params.resourceType
                )
            )
        } catch (e: Exception) {
            Results.ResultBanner(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}


sealed class Params(
    open var pathId: String = ConstantsApp.COFFEE_MAKER,
    open var pageSize: String = "3",
    open var apiKey: String = ConstantsApp.APIKEY,
    open var page: String = "1",
    open var topOffer: String = "",
    open var bottomOffer: String = "",
    open var requestName: String = "",
    open var typeProduct: ProductItem.Type = ProductItem.Type.OnlyImage,
    open var resourceType: Resource.Type = Resource.Type.UNDEFINED,
    open var spain: Int = 3
){

    class CategoriesProductParams(
        override var pageSize: String = "3",
        override var apiKey: String = ConstantsApp.APIKEY,
        override var page: String = "1",
        override var resourceType: Resource.Type = Resource.Type.CATEGORIES
    ) : Params()


    class ProductsParams(
        override var pathId: String = ConstantsApp.COFFEE_MAKER,
        override var pageSize: String = "3",
        override var apiKey: String = ConstantsApp.APIKEY,
        override var page: String = "1",
        override var topOffer: String = "",
        override var bottomOffer: String = "",
        override var requestName: String = "",
        override var typeProduct: ProductItem.Type,
        override var resourceType: Resource.Type = Resource.Type.PRODUCT,
        override var spain: Int = 3
    ) : Params()

    class BannerParams(
        override var resourceType: Resource.Type = Resource.Type.BANNER
    ) : Params()
    class StoriesParams(
        override var resourceType: Resource.Type = Resource.Type.STORIES
    ) : Params()
    class LiveParams(
        override var resourceType: Resource.Type = Resource.Type.STREAMS
    ) : Params()
}


sealed class Results {
    data class ResultCategoryProduct(val result: Resource<MutableList<MutableList<Banner>>>):Results()
    data class ResultBanner(val result : Resource<MutableList<Banner>>):Results()
    data class ResultHistory(val result : Resource<Stories>):Results()
    data class ResultLive(val result : Resource<LiveStreamItem>):Results()
    data class ResultProduct(val result : Resource<CombineProductsItem>):Results()
}