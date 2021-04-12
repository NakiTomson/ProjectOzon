package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.domain.models.Results
import com.app.marketPlace.presentation.factory.Resource
import javax.inject.Inject

class BannersLoadUseCase @Inject constructor() {

    fun loadBannersTop(params: Params): Results.BannersResult {
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


    fun loadBannersDown(params: Params): Results.BannersResult {
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
}