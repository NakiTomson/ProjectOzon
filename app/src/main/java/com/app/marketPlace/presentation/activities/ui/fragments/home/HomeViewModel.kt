package com.app.marketPlace.presentation.activities.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.app.marketPlace.data.remote.modelsAPI.HistoryModels
import com.app.marketPlace.data.remote.services.*
import com.app.marketPlace.data.utils.Configs.CAMERA
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
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
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


    val completed:MutableLiveData<Boolean> = MutableLiveData()

    val bannerListTop:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val categoryProducts:MutableLiveData<Resource<MutableList<MutableList<OnBoardingItem>>>> = MutableLiveData()

    val storiesItems:MutableLiveData<Resource<HistoryModels>> = MutableLiveData()

    val liveStreams:MutableLiveData<Resource<OnLiveItem>> = MutableLiveData()

    val firstPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val secondPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val bannerListCenter:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val thirdPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val bannerListDown:MutableLiveData<Resource<MutableList<OnBoardingItem>>> = MutableLiveData()

    val fourthPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val fifthPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val sixthPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val seventhPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val eighthPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val ninthPartProducts:MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()



    fun startLoading() {
        loadData(Dispatchers.IO) {
            when (bannerListTop.value?.data) {
                null -> {
                    loadProductsFourth()
                }
            }
        }
    }

    private suspend fun loadBannerTop(){
        val banners:Deferred<Results.ResultBanner> = async { homeRepositoryImplBestBye.getBannerStart()}
        withContext(Dispatchers.Main){
            val result = banners.await().result
            when(gettingErrors(result)){
                true -> bannerListTop.value = result
                else -> errorHandling("ERROR BOARDING TOP",result)
            }
        }
    }


    private suspend fun loadProductCategories(){
        val categories = async {
            homeRepositoryImplBestBye.loadCategories(
                Params.CategoriesProductParams(pageSize = "20", apiKey = APIKEY1, page = "1")
            )
        }
        loadBannerTop()
        withContext(Dispatchers.Main){
            val result = categories.await().result
            when(gettingErrors(result)){
                true -> categoryProducts.value = result
                else -> errorHandling("ERROR CATEGORY PRODUCTS HOME",result)
            }
        }
    }

    private suspend fun loadStories(){
        val stories:Deferred<Results.ResultHistory> = async {  homeRepositoryImplDropBox.getHistoryItems()}
        loadProductCategories()
        withContext(Dispatchers.Main){
            val result = stories.await().result
            when(gettingErrors(result)){
                true -> storiesItems.value = result
                else -> errorHandling("ERROR STORIES",result)
            }
        }
    }

    private suspend fun loadLiveStream(){
        val liveStreamItems:Deferred<Results.ResultLive> = async {  homeRepositoryImplDropBox.getLiveItems()}
        loadStories()
        withContext(Dispatchers.Main){
            val result = liveStreamItems.await().result
            when(gettingErrors(result)){
                true -> liveStreams.value = result
                else -> errorHandling("ERROR LIVE",result)
            }
        }
    }


    private suspend fun loadProductsFirst(){
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
        loadLiveStream()
        withContext(Dispatchers.Main){
            val result = products.await().result
            when(gettingErrors(result)){
                true -> firstPartProducts.value = result
                else -> errorHandling("ERROR PRODUCT 1",result)
            }
        }
    }

    private suspend fun loadProductsSecond(){
        val products = async {
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = CELL_PHONES,
                    pageSize = "6",
                    apiKey = APIKEY3,
                    page = "53",
                    typeProduct = OnProductItem.Type.ProductNonName,
                    topOffer = "Это выгодно! Успей купить!",
                    bottomOffer = "Смотреть все товары !"
                )
            )
        }
        loadProductsFirst()
        withContext(Dispatchers.Main) {
            val result = products.await().result
            when(gettingErrors(result)){
                true -> secondPartProducts.value = result
                else -> errorHandling("ERROR PRODUCT 2",result)
            }
        }
    }


    private suspend fun loadBannerCenter(){
        val banners:Deferred<Results.ResultBanner> = async {  homeRepositoryImplBestBye.getBannerCenter()}
        loadProductsSecond()
        withContext(Dispatchers.Main){
            val result = banners.await().result
            when(gettingErrors(result)){
                true -> bannerListCenter.value = result
                else -> errorHandling("ERROR BANNER CENTER",result)
            }
        }
    }


    private suspend fun loadProductsThird(){
        val products = async {
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = LAPTOPS,
                    pageSize = "3",
                    apiKey = APIKEY4,
                    page = "233",
                    typeProduct = OnProductItem.Type.ProductNonName,
                    topOffer = "Рекомендуем ",
                )
            )
        }
        loadBannerCenter()
        withContext(Dispatchers.Main) {
            val result = products.await().result
            when(gettingErrors(result)){
                true -> thirdPartProducts.value = result
                else -> errorHandling("ERROR PRODUCT 3",result)
            }
        }
    }

    private suspend fun loadBannerDown(){
        val banners:Deferred<Results.ResultBanner> = async{ homeRepositoryImplBestBye.getBannerDown() }
        loadProductsThird()
        withContext(Dispatchers.Main){
            val result = banners.await().result
            when(gettingErrors(result)){
                true -> bannerListDown.value = result
                else -> errorHandling("ERROR BANNER DOWN",result)
            }
        }
    }

    private suspend fun loadProductsFourth(){
        val products = async {
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParams(
                    pathId = TVS,
                    pageSize = "4",
                    apiKey = APIKEY5,
                    page = "23",
                    typeProduct = OnProductItem.Type.ProductNonName,
                    topOffer = "Акции недели !",
                    bottomOffer = "Скидки до -80 % здесь!"
                )
            )
        }
        loadBannerDown()
        withContext(Dispatchers.Main) {
            val result = products.await().result
            when(gettingErrors(result)){
                true -> fourthPartProducts.value = result
                else -> errorHandling("ERROR PRODUCT 4",result)
            }
        }
    }


    internal fun loadProductsFifth(){
        if (fifthPartProducts.value != null) return
        GlobalScope.launch(Dispatchers.IO) {
            val products = async {
                homeRepositoryImplBestBye.loadProducts(
                    Params.ProductsParams(
                        pathId = CAMERA,
                        pageSize = "10",
                        apiKey = APIKEY1,
                        page = "1",
                        typeProduct = OnProductItem.Type.ProductWithName,
                        topOffer = "Вас может заинтересовать "
                    )
                )
            }
            withContext(Dispatchers.Main) {
                val result = products.await().result
                when(gettingErrors(result)){
                    true -> fifthPartProducts.value = result
                    else -> errorHandling("ERROR PRODUCT 5",result)
                }
                completed.value = true
            }
        }
    }

}
