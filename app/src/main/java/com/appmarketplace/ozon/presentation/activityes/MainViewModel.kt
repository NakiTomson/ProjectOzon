package com.appmarketplace.ozon.presentation.activityes

import androidx.lifecycle.*

class MainViewModel : ViewModel() {


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