package com.appmarketplace.ozon.presentation.activityes

import android.util.Log
import androidx.lifecycle.*
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.presentation.OzonApp
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), CoroutineScope {


    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    val liveNavigation:MutableLiveData<Int> = MutableLiveData()


    fun navigateInHome(navigate: Int){
        if (liveNavigation.value != navigate){
            liveNavigation.value = navigate
        }
    }

    fun navigateInCategory(navigate: Int){
        liveNavigation.value = navigate
    }

    fun navigateInFavorite(navigate: Int){
        liveNavigation.value = navigate
    }

    fun navigateInBasket(navigate: Int){
        liveNavigation.value = navigate
    }

    fun navigateInAccount(navigate: Int){
        liveNavigation.value = navigate
    }

}