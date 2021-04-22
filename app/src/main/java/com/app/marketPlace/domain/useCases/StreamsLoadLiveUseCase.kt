package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource
import com.app.marketPlace.presentation.factory.TypeResource
import javax.inject.Inject

class StreamsLoadLiveUseCase @Inject constructor(private val marketPlaceApi: BestBuyService) {

    suspend fun loadLiveStreams(liveParams: Params): TypeResource {
        return try {
            TypeResource.LiveStreams(
                Resource(status = Resource.Status.COMPLETED,
                    data = marketPlaceApi.getLiveStreamsAsync().await()
                )
            )
        } catch (e: Exception) {
            TypeResource.LiveStreams( Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}