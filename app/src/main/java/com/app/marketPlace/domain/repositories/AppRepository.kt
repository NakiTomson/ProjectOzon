package com.app.marketPlace.domain.repositories


import androidx.room.Index
import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.data.remote.services.*
import com.app.marketPlace.data.utils.ConstantsApp
import com.app.marketPlace.presentation.rowType.Resource
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.Categories
import com.app.marketPlace.data.utils.ConstantsApp.attrCategoryPathId
import com.app.marketPlace.data.utils.ConstantsApp.attrSearch
import com.app.marketPlace.domain.mappers.*
import com.app.marketPlace.domain.models.LiveStreamItem
import com.app.marketPlace.domain.models.CombineProductsItem
import com.app.marketPlace.domain.models.ProductItem
import java.util.jar.Attributes
import javax.inject.Inject

class AppRepository @Inject constructor(private val marketPlaceApi: MarketPlaceService) {

    suspend fun getBannerStart(params: Params): Results.ResultBanner {
        return try {
            Results.ResultBanner(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(
                        Banner(onBoardingImageUrl = "https://ic.wampi.ru/2021/03/27/oneAdsBanner.jpg"),
                        Banner(onBoardingImageUrl = "https://ic.wampi.ru/2021/03/27/twoAdsBanner.jpg"),
                        Banner(onBoardingImageUrl = "https://ic.wampi.ru/2021/03/27/threeAdsBanner.jpg")
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
                .getCategoryProductsAsync(path = params.pathId,pageSize = params.pageSize, page = params.page, apiKey = params.apiKey)
                .await()
            Results.ResultCategoryProduct(
                params.mapper.map(listGeneralCategory, params) as Resource<List<Categories>>
            )
        } catch (e: Exception) {
            Results.ResultCategoryProduct(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun loadProducts(params: Params):Results.ResultProduct {
        return try {
            val listProducts = marketPlaceApi
                .getProductsAsync(params.attributes,params.pathId, params.pageSize, params.page, params.apiKey)
                .await()
            Results.ResultProduct(params.mapper.map(listProducts, params) as Resource<CombineProductsItem>)
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
                    data = mutableListOf(Banner(onBoardingImageUrl = "https://ic.wampi.ru/2021/03/27/exzample_banner.jpg")),
                    type = params.resourceType
                )
            )
        } catch (e: Exception) {
            Results.ResultBanner(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}

sealed class Params(
    open var attributes: String = attrSearch,
    open var pathId: String = ConstantsApp.COFFEE_MAKER,
    open var pageSize: String = "3",
    open var apiKey: String = ConstantsApp.APIKEY,
    open var page: String = "1",
    open var topOffer: String = "",
    open var bottomOffer: String = "",
    open var requestName: String = "",
    open var typeProduct: ProductItem.Type = ProductItem.Type.OnlyImage,
    open var resourceType: Resource.Type = Resource.Type.UNDEFINED,
    open var spain: Int = 3,
    open var mapper: IMapper = MapperDef()
){

    class CategoriesProductParams(
        override var pageSize: String = "3",
        override var apiKey: String = ConstantsApp.APIKEY,
        override var page: String = "1",
        override var resourceType: Resource.Type = Resource.Type.CATEGORIES,
        override var mapper: IMapper = MapperCategory(),
        override var pathId: String = ""
    ) : Params()


    class ProductsParams(
        override var attributes: String = attrCategoryPathId,
        override var pathId: String = ConstantsApp.COFFEE_MAKER,
        override var pageSize: String = "3",
        override var apiKey: String = ConstantsApp.APIKEY,
        override var page: String = "1",
        override var topOffer: String = "",
        override var bottomOffer: String = "",
        override var requestName: String = "",
        override var typeProduct: ProductItem.Type,
        override var resourceType: Resource.Type = Resource.Type.PRODUCT,
        override var mapper: IMapper = MapperProducts(),
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


sealed class Results(open val result:Resource<*>) {
    data class ResultCategoryProduct(override val result: Resource<List<Categories>>):Results(result)
    data class ResultBanner(override val result : Resource<MutableList<Banner>>):Results(result)
    data class ResultHistory(override val result : Resource<Stories>):Results(result)
    data class ResultLive(override val result : Resource<LiveStreamItem>):Results(result)
    data class ResultProduct(override val result : Resource<CombineProductsItem>):Results(result)
}