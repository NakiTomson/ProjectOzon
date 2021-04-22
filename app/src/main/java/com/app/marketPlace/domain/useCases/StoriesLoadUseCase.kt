package com.app.marketPlace.domain.useCases

import com.app.marketPlace.data.remote.services.BestBuyService
import com.app.marketPlace.domain.models.Params
import com.app.marketPlace.presentation.factory.Resource
import com.app.marketPlace.presentation.factory.TypeResource
import javax.inject.Inject

class StoriesLoadUseCase @Inject constructor(private val marketPlaceApi: BestBuyService) {

    suspend fun loadStories(storiesParams: Params): TypeResource {
        return try {
            TypeResource.Story(
                Resource(
                    status = Resource.Status.COMPLETED,
                    data = marketPlaceApi.getStoriesAsync().await()
                )
            )
        } catch (e: Exception) {
            TypeResource.Story(Resource(status = Resource.Status.ERROR, data = null, exception = e))
        }
    }
}