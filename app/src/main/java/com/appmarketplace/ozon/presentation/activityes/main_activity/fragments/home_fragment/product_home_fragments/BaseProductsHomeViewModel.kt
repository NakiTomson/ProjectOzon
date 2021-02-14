package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.product_home_fragments

import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.presentation.Interfaces.RowType
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseProductsHomeViewModel:ViewModel(), CoroutineScope {

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