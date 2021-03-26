package com.app.marketPlace.presentation.activities.ui.fragments

import androidx.lifecycle.ViewModel
import com.app.marketPlace.data.remote.models.Banner
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel:ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    fun <P> loadData(coroutineContexts: CoroutineContext,doOnAsyncBlock: suspend CoroutineScope.() -> P) {
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
