package com.app.marketPlace.presentation.activities.ui.fragments

import androidx.lifecycle.ViewModel
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.domain.mappers.MapperFromDb
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.presentation.MarketPlaceApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


abstract class BaseViewModel constructor():ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    fun <P> loadData(coroutineContexts: CoroutineContext = coroutineContext,doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        doCoroutineWork(doOnAsyncBlock, viewModelScope, coroutineContexts)
    }

    private inline fun <P> doCoroutineWork(
        crossinline doOnAsyncBlock: suspend CoroutineScope.() -> P,
        coroutineScope: CoroutineScope,
        context: CoroutineContext
    ) {
        coroutineScope.launch {
            withContext(context) {
                doOnAsyncBlock.invoke(this)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancelChildren()
    }
}
