package com.appmarketplace.ozon.presentation.activityes.main_activity.fragments.home_fragment.detail_home_product

import androidx.lifecycle.MutableLiveData
import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl

import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailProductHomeViewModel() : BaseDetailProductViewModel() {


    val categoryProductliveData:MutableLiveData<Resource<List<List<OnBoardingItem>>>> = MutableLiveData()


    fun getListCategoryProducts() {


    }

}