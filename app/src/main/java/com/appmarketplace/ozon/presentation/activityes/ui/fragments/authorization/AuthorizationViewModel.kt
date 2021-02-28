package com.appmarketplace.ozon.presentation.activityes.ui.fragments.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB
import com.appmarketplace.ozon.domain.repositories.HomeRepository
import com.appmarketplace.ozon.presentation.OzonApp
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class AuthorizationViewModel : ViewModel(), CoroutineScope {


    init {
        OzonApp.appComponent.inject(authorizationViewModel = this)
    }

    @Inject
    lateinit var productDb: OzonAppDataBase

    val liveInsert:MutableLiveData<Boolean> = MutableLiveData()



    fun setUser(user: UserDB) {
        launch (Dispatchers.IO){
            productDb.userDao()?.insert(user)
            withContext(Dispatchers.Main){
                liveInsert.value = true
            }
        }
    }

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}