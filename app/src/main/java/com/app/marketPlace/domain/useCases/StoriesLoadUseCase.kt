package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.domain.models.Results
import com.app.marketPlace.presentation.factory.Resource
import javax.inject.Inject

class StoriesLoadUseCase @Inject constructor(private val marketPlaceApi: BestBuyService) {

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
}