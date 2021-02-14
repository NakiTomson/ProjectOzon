package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.search_list_home_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.presentation.pojo.GeneralCategory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine


open class SearchListProductHomeViewModel : ViewModel(),CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    suspend fun techSprint() {

    }


}

