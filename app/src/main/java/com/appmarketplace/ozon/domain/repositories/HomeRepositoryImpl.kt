package com.appmarketplace.ozon.domain.repositories


import com.appmarketplace.ozon.data.utils.Gonfigs.HOME_AUDIO
import com.appmarketplace.ozon.data.remote.services.*
import com.appmarketplace.ozon.domain.mappers.MapperUI
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem

class HomeRepositoryImpl(val marketPlaceApi: ServerApi.MarketPlaceService)
    : AppRepository<HomeRepositoryImpl, HomeRepositoryImpl> {


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



    override suspend fun<T,M> loadCategories(params: Params.CategoriesProductParam<T,M>): Results.ResultCategoryProduct<T> {
        return try {
            val listGeneralCategory = marketPlaceApi
                .getCategoryProducts(params.pageSize, params.page,params.apikey)
                .await()
            Results.ResultCategoryProduct( params.mapper.map(listGeneralCategory as M))
        } catch (e: Exception) {
            Results.ResultCategoryProduct(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    override suspend fun<T,M> loadProducts(params: Params.ProductsParam<T,M>):Results.ResultProduct<T> {
        return try {

            val listProducts = marketPlaceApi
                .getProductsByCategory(params.pathId, params.pageSize, params.page, params.apikey)
                .await()

            Results.ResultProduct(params.mapper.map(listProducts as M))

        } catch (e: Exception) {
            Results.ResultProduct( Resource(status = Resource.Status.ERROR, data = null, exception = e))
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


}