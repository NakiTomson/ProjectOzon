package com.appmarketplace.ozon.presentation.activityes.ui.fragments.detail

import androidx.lifecycle.MutableLiveData
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.BaseViewModel

import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem


class DetailsProductViewModel() : BaseViewModel() {


    val categoryProductliveData:MutableLiveData<Resource<List<List<OnBoardingItem>>>> = MutableLiveData()


    fun getListCategoryProducts() {


    }

}