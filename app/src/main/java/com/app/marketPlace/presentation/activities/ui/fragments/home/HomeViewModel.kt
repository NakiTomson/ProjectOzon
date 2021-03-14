package com.app.marketPlace.presentation.activities.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.app.marketPlace.data.remote.models.Banner
import com.app.marketPlace.data.remote.models.Stories
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY1
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY2
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY3
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY4
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY5
import com.app.marketPlace.data.utils.ConstantsApp.CAMERA
import com.app.marketPlace.data.utils.ConstantsApp.CELL_PHONES
import com.app.marketPlace.data.utils.ConstantsApp.COMPUTER_KEYBOARDS
import com.app.marketPlace.data.utils.ConstantsApp.HEADPHONES
import com.app.marketPlace.data.utils.ConstantsApp.HOME_AUDIO
import com.app.marketPlace.data.utils.ConstantsApp.LAPTOPS
import com.app.marketPlace.data.utils.ConstantsApp.MONITORS
import com.app.marketPlace.data.utils.ConstantsApp.PHONES
import com.app.marketPlace.data.utils.ConstantsApp.TVS
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params
import com.app.marketPlace.domain.repositories.Results
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.rowType.Resource
import com.app.marketPlace.domain.models.*
import com.app.marketPlace.presentation.activities.errorHandling
import com.app.marketPlace.presentation.activities.gettingErrors
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeViewModel() : BaseViewModel() {

    init {
        MarketPlaceApp.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: AppRepository

    val completed:MutableLiveData<Boolean> = MutableLiveData()

    val bannerListTop:MutableLiveData<Resource<MutableList<Banner>>> = MutableLiveData()

    val categoryProducts:MutableLiveData<Resource<MutableList<MutableList<Banner>>>> = MutableLiveData()

    val storiesItems:MutableLiveData<Resource<Stories>> = MutableLiveData()

    val liveStreams:MutableLiveData<Resource<LiveStreamItem>> = MutableLiveData()

    val firstPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val secondPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val bannerListCenter:MutableLiveData<Resource<MutableList<Banner>>> = MutableLiveData()

    val thirdPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val bannerListDown:MutableLiveData<Resource<MutableList<Banner>>> = MutableLiveData()

    val fourthPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val fifthPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val sixthPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val seventhPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val eighthPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()

    val ninthPartProducts:MutableLiveData<Resource<CombineProductsItem>> = MutableLiveData()


    fun startLoading() {
        loadData(Dispatchers.IO) {
            loadProductsFourth()
        }
    }

    private suspend fun loadBannerTop(){
        if (bannerListTop.value != null) return
        val banners:Deferred<Results.ResultBanner> = async { repository.getBannerStart()}
        withContext(Dispatchers.Main){
            val result = banners.await().result
            when(gettingErrors(result)){
                true -> bannerListTop.value = result
                else -> errorHandling("ERROR BOARDING TOP",result)
            }
        }
    }


    private suspend fun loadProductCategories(){
        if (categoryProducts.value != null) return
        val categories = async {
            repository.loadCategories(
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
        if (storiesItems.value != null) return
        val stories:Deferred<Results.ResultHistory> = async {  repository.getHistoryItems()}
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
        if (liveStreams.value != null) return
        val liveStreamItems:Deferred<Results.ResultLive> = async {  repository.getLiveItems()}
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
        if (firstPartProducts.value != null) return
        val products = async {
            repository
                .loadProducts(
                    Params.ProductsParams(
                        pathId = HOME_AUDIO,
                        pageSize = "3",
                        apiKey = APIKEY2,
                        page = "1",
                        typeProduct = ProductItem.Type.OnlyImage
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
        if (secondPartProducts.value != null) return
        val products = async {
            repository.loadProducts(
                Params.ProductsParams(
                    pathId = CELL_PHONES,
                    pageSize = "6",
                    apiKey = APIKEY3,
                    page = "53",
                    typeProduct = ProductItem.Type.ProductNonName,
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
        if (bannerListCenter.value != null) return
        val banners:Deferred<Results.ResultBanner> = async {  repository.getBannerCenter()}
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
        if (thirdPartProducts.value != null) return
        val products = async {
            repository.loadProducts(
                Params.ProductsParams(
                    pathId = LAPTOPS,
                    pageSize = "3",
                    apiKey = APIKEY4,
                    page = "233",
                    typeProduct = ProductItem.Type.ProductNonName,
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
        if (bannerListDown.value != null) return
        val banners:Deferred<Results.ResultBanner> = async{ repository.getBannerDown() }
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
        if (fourthPartProducts.value != null) return
        val products = async {
            repository.loadProducts(
                Params.ProductsParams(
                    pathId = TVS,
                    pageSize = "4",
                    apiKey = APIKEY5,
                    page = "23",
                    typeProduct = ProductItem.Type.ProductNonName,
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
            completed.value = true
        }
    }



    fun loadAdditionalData() {
        loadData(Dispatchers.IO) {
            loadProductsNinth()
        }
    }


    private suspend fun loadProductsFifth(){
        if (fifthPartProducts.value != null) return
        val products = async {
            repository.loadProducts(
                Params.ProductsParams(
                    pathId = CAMERA,
                    pageSize = "8",
                    apiKey = APIKEY1,
                    page = "3",
                    typeProduct = ProductItem.Type.ProductWithName,
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
        }
    }



    private suspend fun loadProductsSixth(){
        if (sixthPartProducts.value != null) return

        val products = async {
            repository.loadProducts(
                Params.ProductsParams(
                    pathId = HEADPHONES,
                    pageSize = "12",
                    apiKey = APIKEY2,
                    page = "1",
                    typeProduct = ProductItem.Type.ProductNoNBasket,
                    topOffer = "Музыка для души "
                )
            )
        }
        loadProductsFifth()
        withContext(Dispatchers.Main) {
            val result = products.await().result
            when (gettingErrors(result)) {
                true -> sixthPartProducts.value = result
                else -> errorHandling("ERROR PRODUCT 6", result)
            }
        }
    }



    private suspend fun loadProductsSeventh(){
        if (seventhPartProducts.value != null) return

        val products = async {
            repository.loadProducts(
                Params.ProductsParams(
                    pathId = MONITORS,
                    pageSize = "6",
                    apiKey = APIKEY3,
                    page = "1",
                    typeProduct = ProductItem.Type.ProductHorizontal,
                    topOffer = "Лучшие мониторы 90 % скидка"
                )
            )
        }
        loadProductsSixth()
        withContext(Dispatchers.Main) {
            val result = products.await().result
            when(gettingErrors(result)){
                true -> seventhPartProducts.value = result
                else -> errorHandling("ERROR PRODUCT 7",result)
            }
        }
    }



    private suspend fun loadProductsEighth(){
        if (eighthPartProducts.value != null) return

        val products = async {
            repository.loadProducts(
                Params.ProductsParams(
                    pathId = COMPUTER_KEYBOARDS,
                    pageSize = "6",
                    apiKey = APIKEY4,
                    page = "1",
                    typeProduct = ProductItem.Type.ProductWithName,
                    topOffer = "Удачная покупка"
                )
            )
        }
        loadProductsSeventh()
        withContext(Dispatchers.Main) {
            val result = products.await().result
            when(gettingErrors(result)){
                true -> eighthPartProducts.value = result
                else -> errorHandling("ERROR PRODUCT 8",result)
            }
        }
    }



    private suspend fun loadProductsNinth(){
        if (ninthPartProducts.value != null) return
        val products = async {
            repository.loadProducts(
                Params.ProductsParams(
                    pathId = PHONES,
                    pageSize = "20",
                    apiKey = APIKEY5,
                    page = "1",
                    typeProduct = ProductItem.Type.ProductWithName,
                    topOffer = "Пора обновить телефон !"
                )
            )
        }
        loadProductsEighth()
        withContext(Dispatchers.Main) {
            val result = products.await().result
            when(gettingErrors(result)){
                true -> ninthPartProducts.value = result
                else -> errorHandling("ERROR PRODUCT 9",result)
            }
        }
    }
}
