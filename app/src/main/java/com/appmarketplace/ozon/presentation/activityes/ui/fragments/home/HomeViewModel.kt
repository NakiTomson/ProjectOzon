package com.appmarketplace.ozon.presentation.activityes.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.appmarketplace.ozon.data.remote.services.*
import com.appmarketplace.ozon.data.utils.Gonfigs.CELL_PHONES
import com.appmarketplace.ozon.data.utils.Gonfigs.HOME_AUDIO
import com.appmarketplace.ozon.data.utils.Gonfigs.LAPTOPS
import com.appmarketplace.ozon.data.utils.Gonfigs.TVS
import com.appmarketplace.ozon.domain.repositories.HomeRepository
import com.appmarketplace.ozon.domain.repositories.Params
import com.appmarketplace.ozon.domain.repositories.Results
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.BaseViewModel
import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.*
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel() : BaseViewModel() {


    init {
        OzonApp.appComponent.inject(listProductsHome = this)
    }


    @Inject @field : Named("bestbuy")
    lateinit var homeRepositoryImplBestBye: HomeRepository


    @Inject @field : Named("dropbox")
    lateinit var homeRepositoryImplDropBox: HomeRepository



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
        loadData(Dispatchers.IO) {
            when {
                bannerListStart.value?.data == null -> { loadingProductsFoure() }
            }
        }
    }

    suspend fun loadingBannerStart(){
        val bannerStart:Deferred<Results.ResultBanner> = async { homeRepositoryImplBestBye.getBannerStart()}
        withContext(Dispatchers.Main){
            bannerListStart.value = bannerStart.await().result
        }
    }


    suspend fun loadingCategoryProduct(){

        val categoryResult =  async {
            homeRepositoryImplBestBye
                .loadCategories(
                    Params.CategoriesProductParams(pageSize = "20", apikey = APIKEY1, page = "1")
                )
        }
        loadingBannerStart()
        withContext(Dispatchers.Main){
            categoryProductliveData.value = categoryResult.await().result
        }
    }

    suspend fun loadingHistoryData(){
        val historyItems:Deferred<Results.ResultHistory> = async {  homeRepositoryImplDropBox.getHistoryItems()}
        loadingCategoryProduct()
        withContext(Dispatchers.Main){
            historyItemsLiveData.value = historyItems.await().result
        }
    }

    suspend fun loadingLiveData(){
        val liveItems:Deferred<Results.ResultLive> = async {  homeRepositoryImplDropBox.getLiveItems()}
        loadingHistoryData()
        withContext(Dispatchers.Main){
            liveItemsLiveData.value = liveItems.await().result
        }
    }



    suspend fun getSimpleImageProducts(){

        val products = async {
            homeRepositoryImplBestBye
                .loadProducts(
                    Params.ProductsParams(
                        pathId = HOME_AUDIO,
                        pageSize = "3",
                        apikey = APIKEY2,
                        page = "1",
                        typeProduct = OnProductItem.Type.OnlyImage
                    )
                )
        }
        loadingLiveData()
        withContext(Dispatchers.Main){
            listPoductsLiveData.value = products.await().result
        }
    }

    suspend fun getProductsPhone(){
        val products = async {
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = CELL_PHONES,
                    pageSize = "6",
                    apikey = APIKEY3,
                    page = "53",
                    typeProduct = OnProductItem.Type.ProductNonName
                )
            )
        }
        getSimpleImageProducts()
        withContext(Dispatchers.Main) {
            listPoductsLiveData2.value = products.await().result
        }
    }


    suspend fun loadingBannerCenter(){
        val bannerCenter:Deferred<Results.ResultBanner> = async {  homeRepositoryImplBestBye.getBannerCenter()}
        getProductsPhone()
        withContext(Dispatchers.Main){
            bannerListCenter.value = bannerCenter.await().result
        }
    }


    suspend fun getProductsLaptops(){
        val products3 = async {
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = LAPTOPS,
                    pageSize = "3",
                    apikey = APIKEY4,
                    page = "233",
                    typeProduct = OnProductItem.Type.ProductNonName
                )
            )
        }
        loadingBannerCenter()
        withContext(Dispatchers.Main) {
            listPoductsLiveData3.value = products3.await().result
        }
    }

    suspend fun loadingBannerDown(){
        val bannerDown:Deferred<Results.ResultBanner> = async{ homeRepositoryImplBestBye.getBannerDown() }
        getProductsLaptops()
        withContext(Dispatchers.Main){
            bannerListDown.value = bannerDown.await().result
        }
    }

    suspend fun loadingProductsFoure(){
        val products4 = async {

            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = TVS,
                    pageSize = "4",
                    apikey = APIKEY5,
                    page = "23",
                    typeProduct = OnProductItem.Type.ProductNonName
                )
            )
        }
        loadingBannerDown()
        withContext(Dispatchers.Main) {
            listPoductsLiveData4.value = products4.await().result
        }
    }



}
