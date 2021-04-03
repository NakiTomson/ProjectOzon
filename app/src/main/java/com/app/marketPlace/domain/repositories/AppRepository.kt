package com.app.marketPlace.domain.repositories


import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.app.marketPlace.data.remote.models.*
import com.app.marketPlace.data.remote.services.*
import com.app.marketPlace.data.utils.Constants
import com.app.marketPlace.presentation.factory.Resource
import com.app.marketPlace.data.utils.Constants.attrCategoryPathId
import com.app.marketPlace.data.utils.Constants.attrSearch
import com.app.marketPlace.domain.mappers.*
import com.app.marketPlace.domain.models.LiveStreamItem
import com.app.marketPlace.domain.models.CombineProducts
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.presentation.paging.ProductPagingSource
import com.app.marketPlace.presentation.paging.ProductPagingSource.Companion.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(private val marketPlaceApi: BestBuyService) {

    suspend fun loadBannersTop(params: Params): Results.BannersResult {
        return try {
            Results.BannersResult(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(
                        Banner(imageUrl = "https://ic.wampi.ru/2021/03/27/oneAdsBanner.jpg"),
                        Banner(imageUrl = "https://ic.wampi.ru/2021/03/27/twoAdsBanner.jpg"),
                        Banner(imageUrl = "https://ic.wampi.ru/2021/03/27/threeAdsBanner.jpg")
                    ),
                    type = params.resourceType
                )
            )
        } catch (e: Exception) {
            Results.BannersResult(  Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun loadBannersCenter(params: Params): Results.BannersResult {
        return loadBannersTop(params)
    }


    fun loadBannersDown(params: Params):Results.BannersResult {
        return try {
            Results.BannersResult(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(Banner(imageUrl = "https://ic.wampi.ru/2021/03/27/exzample_banner.jpg")),
                    type = params.resourceType
                )
            )
        } catch (e: Exception) {
            Results.BannersResult(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun loadStories(storiesParams: Params): Results.StoriesResult {
        return try {
            Results.StoriesResult(
                Resource(status = Resource.Status.COMPLETED,
                data = marketPlaceApi.getStoriesAsync().await(),
                type = storiesParams.resourceType)
            )
        } catch (e: Exception) {
            Results.StoriesResult(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun loadLiveStreams(liveParams: Params):Results.LiveStreamsResult {
        return try {
            Results.LiveStreamsResult(
                Resource(status = Resource.Status.COMPLETED,
                data = marketPlaceApi.getLiveStreamsAsync().await(),
                type = liveParams.resourceType)
            )
        } catch (e: Exception) {
            Results.LiveStreamsResult( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun loadCategories(params: Params): Results.CategoriesProductResult {
        return try {
            val listGeneralCategory = marketPlaceApi
                .getCategoriesProductAsync(path = params.pathId,pageSize = params.pageSize, page = params.page, apiKey = params.apiKey)
                .await()
            Results.CategoriesProductResult(
                params.mapper.map(listGeneralCategory, params) as Resource<List<Categories>>
            )
        } catch (e: Exception) {
            Results.CategoriesProductResult(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun loadProducts(params: Params):Results.ProductsResult {
        return try {
            val listProducts = marketPlaceApi
                .getProductsAsync(params.attributes,params.pathId, params.pageSize, params.page, params.apiKey)
                .await()
            Results.ProductsResult(params.mapper.map(listProducts, params) as Resource<CombineProducts>)
        } catch (e: Exception) {
            Results.ProductsResult( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }



    fun loadProductsPager(params: Params,pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<Product>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ProductPagingSource(marketPlaceApi,params) }
        ).liveData
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}

sealed class Params(
    open var attributes: String = attrSearch,
    open var pathId: String = Constants.CoffeeMaker,
    open var pageSize: String = "3",
    open var apiKey: String = Constants.ApiToken,
    open var page: String = "1",
    open var topOffer: String = "",
    open var bottomOffer: String = "",
    open var requestName: String = "",
    open var typeProduct: Product.Type = Product.Type.OnlyImage,
    open var resourceType: Resource.Type = Resource.Type.UNDEFINED,
    open var spain: Int = 3,
    open var mapper: Mapper = MapperDefault()
){

    class CategoriesProductParams(
        override var pageSize: String = "3",
        override var apiKey: String = Constants.ApiToken,
        override var page: String = "1",
        override var resourceType: Resource.Type = Resource.Type.CATEGORIES,
        override var mapper: Mapper = MapperCategories(),
        override var pathId: String = ""
    ) : Params()


    class ProductsParams(
        override var attributes: String = attrCategoryPathId,
        override var pathId: String = Constants.CellPhone,
        override var pageSize: String = "3",
        override var apiKey: String = Constants.ApiToken,
        override var page: String = "1",
        override var topOffer: String = "",
        override var bottomOffer: String = "",
        override var requestName: String = "",
        override var typeProduct: Product.Type,
        override var resourceType: Resource.Type = Resource.Type.PRODUCT,
        override var mapper: Mapper = MapperProducts(),
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


sealed class Results(open val result: Resource<*>) {
    data class CategoriesProductResult(override val result: Resource<List<Categories>>):Results(result)
    data class BannersResult(override val result : Resource<MutableList<Banner>>):Results(result)
    data class StoriesResult(override val result : Resource<Stories>):Results(result)
    data class LiveStreamsResult(override val result : Resource<LiveStreamItem>):Results(result)
    data class ProductsResult(override val result : Resource<CombineProducts>):Results(result)
}