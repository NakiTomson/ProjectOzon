package com.app.marketPlace.domain.repositories


import com.app.marketPlace.data.remote.services.*
import com.app.marketPlace.domain.mappers.Mapper
import com.app.marketPlace.presentation.rowType.Resource
import com.app.marketPlace.domain.modelsUI.OnBoardingItem

class HomeRepository(private val marketPlaceApi: ServerApi.MarketPlaceService, val mapper: Mapper) {


    fun getBannerStart(): Results.ResultBanner {
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



    suspend fun loadCategories(params: Params): Results.ResultCategoryProduct {
        return try {
            val listGeneralCategory = marketPlaceApi
                .getCategoryProductsAsync(params.pageSize, params.page,params.apiKey)
                .await()

            Results.ResultCategoryProduct(
                mapper.mapCategoriesFromApiToUiCategories(listGeneralCategory)
            )
        } catch (e: Exception) {
            Results.ResultCategoryProduct(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }


    suspend fun loadProducts(params: Params):Results.ResultProduct {
        return try {

            val listProducts = marketPlaceApi
                .getProductsByCategoryAsync(params.pathId, params.pageSize, params.page, params.apiKey)
                .await()

            Results.ResultProduct(
                mapper.mapProductFromApiToUiProduct(
                    listProducts,
                    type = params.typeProduct
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

    fun getBannerDown():Results.ResultBanner {
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