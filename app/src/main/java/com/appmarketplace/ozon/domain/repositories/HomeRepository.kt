package com.appmarketplace.ozon.domain.repositories


import com.appmarketplace.ozon.data.remote.services.*
import com.appmarketplace.ozon.domain.mappers.Mapper
import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.OnBoardingItem

class HomeRepository(val marketPlaceApi: ServerApi.MarketPlaceService,val mapper: Mapper) {


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



    suspend fun loadCategories(params: Params): Results.ResultCategoryProduct {
        return try {
            val listGeneralCategory = marketPlaceApi
                .getCategoryProducts(params.pageSize, params.page,params.apikey)
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
                .getProductsByCategory(params.pathId, params.pageSize, params.page, params.apikey)
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