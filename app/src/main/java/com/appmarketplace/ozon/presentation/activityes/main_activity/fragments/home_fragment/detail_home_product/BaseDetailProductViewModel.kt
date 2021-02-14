package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.detail_home_product

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext

abstract class BaseDetailProductViewModel:ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = Job()


    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)


    fun <P> loadData(coroutineContext: CoroutineContext,doOnAsyncBlock: suspend CoroutineScope.() -> P) {

        doCoroutineWork(doOnAsyncBlock, viewModelScope, coroutineContext)
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