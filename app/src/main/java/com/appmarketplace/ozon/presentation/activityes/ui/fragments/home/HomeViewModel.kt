package com.appmarketplace.ozon.presentation.activityes.ui.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appmarketplace.ozon.domain.repositories.HomeRepositoryImpl
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.BaseViewModel
import com.appmarketplace.ozon.presentation.data.Resource
import com.appmarketplace.ozon.presentation.pojo.OnBoardingItem
import com.appmarketplace.ozon.presentation.pojo.OnHistoryItem
import com.appmarketplace.ozon.presentation.pojo.OnLiveItem
import com.appmarketplace.ozon.presentation.pojo.OnOfferProductsItem
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel : BaseViewModel() {


    init {
        OzonApp.appComponent.inject(listProductsHome = this)
    }


    @Inject @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepositoryImpl


    @Inject @field : Named("dropbox")
    lateinit var homeRepositoryImplDropBox: HomeRepositoryImpl


    final val bannerListStart:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    final val categoryProductliveData:MutableLiveData<Resource<MutableList<MutableList<OnBoardingItem>>>> = MutableLiveData()

    final val historyItemsLiveData:MutableLiveData<Resource<OnHistoryItem>> = MutableLiveData()

    final val liveItemsLiveData:MutableLiveData<Resource<OnLiveItem>> = MutableLiveData()

    final val listPoductsLiveData:MutableLiveData<Resource<MutableList<OnOfferProductsItem>>> = MutableLiveData()

    final val listPoductsLiveData2:MutableLiveData<Resource<MutableList<OnOfferProductsItem>>> = MutableLiveData()

    final val bannerListCenter:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    final val listPoductsLiveData3:MutableLiveData<Resource<MutableList<OnOfferProductsItem>>> = MutableLiveData()

    final val bannerListDown:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    final val listPoductsLiveData4:MutableLiveData<Resource<MutableList<OnOfferProductsItem>>> = MutableLiveData()



    fun startLoading(){

        when  {
            bannerListStart.value?.data == null ->  {
                Log.v("LOADING","No Have Data")
                startLoadingData()}
            else -> {
                Log.v("LOADING","Have Data")
            }
        }

    }

    fun startLoadingData(){

        loadData(Dispatchers.IO) {

            val bannerStart:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async { homeRepositoryImplBestBye.getBannerStart()}

            val categoryResult:Deferred<HomeRepositoryImpl.Results.ResultCategoryProduct> =  async { homeRepositoryImplBestBye.loadDataCategoryProduct(HomeRepositoryImpl.Params())}

            val historyItems:Deferred<HomeRepositoryImpl.Results.ResultHistory> = async {  homeRepositoryImplDropBox.getHistoryItems()}

            val liveItems:Deferred<HomeRepositoryImpl.Results.ResultLive> = async {  homeRepositoryImplDropBox.getLiveItems()}

            val products:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async {  homeRepositoryImplBestBye.getFirstProducts()}

            val products2:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async {  homeRepositoryImplBestBye.getSecondProducts() }

            val bannerCenter:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async {  homeRepositoryImplBestBye.getBannerCenter()}

            val products3:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async{ homeRepositoryImplBestBye.getThirdProducts() }

            val bannerDown:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async{ homeRepositoryImplBestBye.getBannerDown() }

            val products4:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async { homeRepositoryImplBestBye.getFourthProducts() }


            withContext(Dispatchers.Main){
                setupData(
                    bannerStart.await().result,
                    (categoryResult.await().result),
                    (historyItems.await()).result,
                    liveItems.await().result,
                    products.await().result,
                    products2.await().result,
                    bannerCenter.await().result,
                    products3.await().result,
                    bannerDown.await().result,
                    products4.await().result,
                )

            }
        }
    }

    private fun setupData(
        startBannerItems: Resource<MutableList<OnBoardingItem>>?,
        categoryItems: Resource<MutableList<MutableList<OnBoardingItem>>>?,
        historyItems: Resource<OnHistoryItem>?,
        liveItems: Resource<OnLiveItem>?,
        productFirst: Resource<MutableList<OnOfferProductsItem>>?,
        productSecond: Resource<MutableList<OnOfferProductsItem>>?,
        centerBanner: Resource<MutableList<OnBoardingItem>>?,
        productThird: Resource<MutableList<OnOfferProductsItem>>? = null,
        downBanner: Resource<MutableList<OnBoardingItem>>? = null,
        productFourth: Resource<MutableList<OnOfferProductsItem>>? = null,
    ) {
        bannerListStart.value = startBannerItems
        categoryProductliveData.value = categoryItems
        historyItemsLiveData.value = historyItems
        liveItemsLiveData.value = liveItems
        listPoductsLiveData.value = productFirst
        listPoductsLiveData2.value = productSecond
        bannerListCenter.value = centerBanner
        listPoductsLiveData3.value = productThird
        bannerListDown.value = downBanner
        listPoductsLiveData4.value = productFourth
    }


}
