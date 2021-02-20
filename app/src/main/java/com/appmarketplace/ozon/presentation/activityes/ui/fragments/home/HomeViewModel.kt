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
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import okhttp3.internal.waitMillis
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


    val bannerListStart:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val categoryProductliveData:MutableLiveData<Resource<MutableList<MutableList<OnBoardingItem>>>> = MutableLiveData()

    val historyItemsLiveData:MutableLiveData<Resource<OnHistoryItem>> = MutableLiveData()

    val liveItemsLiveData:MutableLiveData<Resource<OnLiveItem>> = MutableLiveData()

    val listPoductsLiveData:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val listPoductsLiveData2:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val bannerListCenter:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val listPoductsLiveData3:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val bannerListDown:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val listPoductsLiveData4:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()


    fun startLoading() {
        launch(Dispatchers.IO) {
            when {
                bannerListStart.value?.data == null -> {
                    loadingProductsFoure()
                }
            }
        }
    }

    suspend fun loadingBannerStart(){
        val bannerStart:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async { homeRepositoryImplBestBye.getBannerStart()}
        withContext(Dispatchers.Main){
            bannerListStart.value = bannerStart.await().result
        }
    }

    suspend fun loadingCategoryProduct(){
        val categoryResult:Deferred<HomeRepositoryImpl.Results.ResultCategoryProduct> =  async { homeRepositoryImplBestBye.loadDataCategoryProduct(HomeRepositoryImpl.Params())}
        loadingBannerStart()
        withContext(Dispatchers.Main){
            categoryProductliveData.value = categoryResult.await().result
        }
    }

    suspend fun loadingHistoryData(){
        val historyItems:Deferred<HomeRepositoryImpl.Results.ResultHistory> = async {  homeRepositoryImplDropBox.getHistoryItems()}
        loadingCategoryProduct()
        withContext(Dispatchers.Main){
            historyItemsLiveData.value = historyItems.await().result
        }
    }

    suspend fun loadingLiveData(){
        val liveItems:Deferred<HomeRepositoryImpl.Results.ResultLive> = async {  homeRepositoryImplDropBox.getLiveItems()}
        loadingHistoryData()
        withContext(Dispatchers.Main){
            liveItemsLiveData.value = liveItems.await().result
        }
    }

    suspend fun loadingProductsOne(){
        val products:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async {  homeRepositoryImplBestBye.getFirstProducts()}
        loadingLiveData()
        withContext(Dispatchers.Main){
            listPoductsLiveData.value = products.await().result
        }
    }


    suspend fun loadingProductsTwo(){
        val products2:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async {  homeRepositoryImplBestBye.getSecondProducts() }
        loadingProductsOne()
        withContext(Dispatchers.Main){
            listPoductsLiveData2.value = products2.await().result
        }
    }


    suspend fun loadingBannerCenter(){
        val bannerCenter:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async {  homeRepositoryImplBestBye.getBannerCenter()}
        loadingProductsTwo()
        withContext(Dispatchers.Main){
            bannerListCenter.value = bannerCenter.await().result
        }
    }


    suspend fun loadingProductsThree(){
        val products3:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async{ homeRepositoryImplBestBye.getThirdProducts() }
        loadingBannerCenter()
        withContext(Dispatchers.Main){
            listPoductsLiveData3.value = products3.await().result
        }
    }

    suspend fun loadingBannerDown(){
        val bannerDown:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async{ homeRepositoryImplBestBye.getBannerDown() }
        loadingProductsThree()
        withContext(Dispatchers.Main){
            bannerListDown.value = bannerDown.await().result
        }
    }

    suspend fun loadingProductsFoure(){
        val products4:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async { homeRepositoryImplBestBye.getFourthProducts() }
        loadingBannerDown()
        withContext(Dispatchers.Main){
            listPoductsLiveData4.value = products4.await().result
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
        productFirst: Resource<OnOfferProductsItem>?,
        productSecond: Resource<OnOfferProductsItem>?,
        centerBanner: Resource<MutableList<OnBoardingItem>>?,
        productThird: Resource<OnOfferProductsItem>? = null,
        downBanner: Resource<MutableList<OnBoardingItem>>? = null,
        productFourth: Resource<OnOfferProductsItem>? = null,
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
