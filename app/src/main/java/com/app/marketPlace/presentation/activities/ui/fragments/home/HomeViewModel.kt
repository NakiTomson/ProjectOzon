package com.app.marketPlace.presentation.activities.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.app.marketPlace.data.remote.services.*
import com.app.marketPlace.data.utils.Configs.CELL_PHONES
import com.app.marketPlace.data.utils.Configs.HOME_AUDIO
import com.app.marketPlace.data.utils.Configs.LAPTOPS
import com.app.marketPlace.data.utils.Configs.TVS
import com.app.marketPlace.domain.repositories.HomeRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.domain.repositories.Results
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.rowType.Resource
import com.app.marketPlace.domain.modelsUI.*
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel : BaseViewModel() {


    init {
        MarketPlaceApp.appComponent.inject(listProductsHome = this)
    }


    @Inject @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepository


    @Inject @field : Named("dropbox")
    lateinit var homeRepositoryImplDropBox: HomeRepository



    val bannerListStart:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val categoryProductLiveData:MutableLiveData<Resource<MutableList<MutableList<OnBoardingItem>>>> = MutableLiveData()

    val historyItemsLiveData:MutableLiveData<Resource<OnHistoryItem>> = MutableLiveData()

    val liveItemsLiveData:MutableLiveData<Resource<OnLiveItem>> = MutableLiveData()

    val listProductsLiveData:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val listProductsLiveData2:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val bannerListCenter:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val listProductsLiveData3:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val bannerListDown:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val listProductsLiveData4:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()



    fun startLoading() {
        loadData(Dispatchers.IO) {
            when (bannerListStart.value?.data) {
                null -> { loadingProductsFore() }
            }
        }
    }

    private suspend fun loadingBannerStart(){
        val bannerStart:Deferred<Results.ResultBanner> = async { homeRepositoryImplBestBye.getBannerStart()}
        withContext(Dispatchers.Main){
            bannerListStart.value = bannerStart.await().result
        }
    }


    private suspend fun loadingCategoryProduct(){

        val categoryResult =  async {
            homeRepositoryImplBestBye
                .loadCategories(
                    Params.CategoriesProductParams(pageSize = "20", apiKey = APIKEY1, page = "1")
                )
        }
        loadingBannerStart()
        withContext(Dispatchers.Main){
            categoryProductLiveData.value = categoryResult.await().result
        }
    }

    private suspend fun loadingHistoryData(){
        val historyItems:Deferred<Results.ResultHistory> = async {  homeRepositoryImplDropBox.getHistoryItems()}
        loadingCategoryProduct()
        withContext(Dispatchers.Main){
            historyItemsLiveData.value = historyItems.await().result
        }
    }

    private suspend fun loadingLiveData(){
        val liveItems:Deferred<Results.ResultLive> = async {  homeRepositoryImplDropBox.getLiveItems()}
        loadingHistoryData()
        withContext(Dispatchers.Main){
            liveItemsLiveData.value = liveItems.await().result
        }
    }



    private suspend fun getSimpleImageProducts(){

        val products = async {
            homeRepositoryImplBestBye
                .loadProducts(
                    Params.ProductsParams(
                        pathId = HOME_AUDIO,
                        pageSize = "3",
                        apiKey = APIKEY2,
                        page = "1",
                        typeProduct = OnProductItem.Type.OnlyImage
                    )
                )
        }
        loadingLiveData()
        withContext(Dispatchers.Main){
            listProductsLiveData.value = products.await().result
        }
    }

    private suspend fun getProductsPhone(){
        val products = async {
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = CELL_PHONES,
                    pageSize = "6",
                    apiKey = APIKEY3,
                    page = "53",
                    typeProduct = OnProductItem.Type.ProductNonName
                )
            )
        }
        getSimpleImageProducts()
        withContext(Dispatchers.Main) {
            listProductsLiveData2.value = products.await().result
        }
    }


    private suspend fun loadingBannerCenter(){
        val bannerCenter:Deferred<Results.ResultBanner> = async {  homeRepositoryImplBestBye.getBannerCenter()}
        getProductsPhone()
        withContext(Dispatchers.Main){
            bannerListCenter.value = bannerCenter.await().result
        }
    }


    private suspend fun getProductsLaptops(){
        val products3 = async {
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = LAPTOPS,
                    pageSize = "3",
                    apiKey = APIKEY4,
                    page = "233",
                    typeProduct = OnProductItem.Type.ProductNonName
                )
            )
        }
        loadingBannerCenter()
        withContext(Dispatchers.Main) {
            listProductsLiveData3.value = products3.await().result
        }
    }

    private suspend fun loadingBannerDown(){
        val bannerDown:Deferred<Results.ResultBanner> = async{ homeRepositoryImplBestBye.getBannerDown() }
        getProductsLaptops()
        withContext(Dispatchers.Main){
            bannerListDown.value = bannerDown.await().result
        }
    }

    private suspend fun loadingProductsFore(){
        val products4 = async {

            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = TVS,
                    pageSize = "4",
                    apiKey = APIKEY5,
                    page = "23",
                    typeProduct = OnProductItem.Type.ProductNonName
                )
            )
        }
        loadingBannerDown()
        withContext(Dispatchers.Main) {
            listProductsLiveData4.value = products4.await().result
        }
    }



}
