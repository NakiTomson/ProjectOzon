package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource
import com.app.marketPlace.presentation.factory.TypeResource
import javax.inject.Inject



class BannersLoadUseCase @Inject constructor() {

    fun loadBannersTop(params: Params): TypeResource.Banners {
        // Ctrl + Alt + V
        return try {
            TypeResource.Banners(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(
                        Banner(imageUrl = "https://ic.wampi.ru/2021/03/27/oneAdsBanner.jpg"),
                        Banner(imageUrl = "https://ic.wampi.ru/2021/03/27/twoAdsBanner.jpg"),
                        Banner(imageUrl = "https://ic.wampi.ru/2021/03/27/threeAdsBanner.jpg")
                    )
                )
            )
        } catch (e: Exception) {
            TypeResource.Banners(  Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }

    suspend fun loadBannersCenter(params: Params): TypeResource.Banners {
        return loadBannersTop(params)
    }


    fun loadBannersDown(params: Params): TypeResource.Banners {
        return try {
            TypeResource.Banners(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = mutableListOf(Banner(imageUrl = "https://ic.wampi.ru/2021/03/27/exzample_banner.jpg"))
                )
            )
        } catch (e: Exception) {
            TypeResource.Banners(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}