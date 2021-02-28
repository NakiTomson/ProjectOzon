package com.appmarketplace.ozon.presentation.activityes.ui.fragments.makingOrder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB
import com.appmarketplace.ozon.presentation.OzonApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MakingOrderViewModel : ViewModel(), CoroutineScope {


    init {
        OzonApp.appComponent.inject(makingOrderViewModel = this)
    }


    @Inject
    lateinit var productDb: OzonAppDataBase

    val userLive: MutableLiveData<UserDB> = MutableLiveData()

    fun getUSer(login: String){
        launch(Dispatchers.IO){
            val user = productDb.userDao()?.getUser(login)
            userLive.postValue(user)
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