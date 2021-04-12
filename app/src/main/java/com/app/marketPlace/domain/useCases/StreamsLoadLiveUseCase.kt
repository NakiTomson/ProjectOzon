package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.domain.models.Results
import com.app.marketPlace.presentation.factory.Resource
import javax.inject.Inject

class StreamsLoadLiveUseCase @Inject constructor(private val marketPlaceApi: BestBuyService) {

    suspend fun loadLiveStreams(liveParams: Params): Results.LiveStreamsResult {
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
}