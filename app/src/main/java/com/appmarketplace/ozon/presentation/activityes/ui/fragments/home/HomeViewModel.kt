package com.appmarketplace.ozon.presentation.activityes.ui.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.db.ProductDao
import com.appmarketplace.ozon.data.remote.modelsAPI.ProductsModel
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.services.*
import com.appmarketplace.ozon.data.utils.Gonfigs.CELL_PHONES
import com.appmarketplace.ozon.data.utils.Gonfigs.HOME_AUDIO
import com.appmarketplace.ozon.data.utils.Gonfigs.LAPTOPS
import com.appmarketplace.ozon.data.utils.Gonfigs.TVS
import com.appmarketplace.ozon.domain.mappers.MapListCategoryToListData
import com.appmarketplace.ozon.domain.mappers.MapProductsToListData
import com.appmarketplace.ozon.domain.repositories.HomeRepository
import com.appmarketplace.ozon.domain.repositories.Params
import com.appmarketplace.ozon.domain.repositories.Results
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.BaseViewModel
import com.appmarketplace.ozon.presentation.rowType.Resource
import com.appmarketplace.ozon.domain.modelsUI.*
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem.Type
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

    @Inject
    lateinit var productDao: ProductDao


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
                bannerListStart.value?.data == null -> {
                    loadingProductsFoure()
                }
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

        val categoryResult:Deferred<Results.ResultCategoryProduct<MutableList<MutableList<OnBoardingItem>>>> =  async {
            homeRepositoryImplBestBye
                .loadCategories(
                    Params.CategoriesProductParam<MutableList<MutableList<OnBoardingItem>>, GeneralCategory>(
                        mapper = MapListCategoryToListData(),
                        pageSize = "20",
                        apikey = APIKEY1,
                        page = "1"
                    )
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



    suspend fun getThreeSimpleImageProducts(){

        val products:Deferred<Results.ResultProduct<OnOfferProductsItem>> = async {
            homeRepositoryImplBestBye
                .loadProducts(
                    Params.ProductsParam<OnOfferProductsItem, ProductsModel>(
                        mapper = MapProductsToListData(type = Type.OnlyImage()),
                        pathId = HOME_AUDIO,
                        pageSize = "3",
                        apikey = APIKEY2,
                        page = "1"
                    )
                )
        }
        loadingLiveData()
        withContext(Dispatchers.Main){
            listPoductsLiveData.value = products.await().result
        }
    }

    suspend fun getProductsPhone(){
        val products:Deferred<Results.ResultProduct<OnOfferProductsItem>> = async {
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParam<OnOfferProductsItem,ProductsModel>(
                    mapper = MapProductsToListData(
                        type = Type.ProductNonName(),
                        topOffer = "Лучшие предложения!",
                        bottomOffer = "Скидки до  80 % здесь!",
                    ),
                    pathId = CELL_PHONES,
                    pageSize = "6",
                    apikey = APIKEY3,
                    page = "53"
                )
            )
        }
        getThreeSimpleImageProducts()
        withContext(Dispatchers.Main){
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
        val products3:Deferred<Results.ResultProduct<OnOfferProductsItem>> = async{
            homeRepositoryImplBestBye.loadProducts(
                Params.ProductsParam<OnOfferProductsItem,ProductsModel>(
                    mapper = MapProductsToListData(
                        type = Type.ProductNonName(),
                        topOffer = "Крутые скидки! 90%"
                    ),
                    pathId = LAPTOPS,
                    pageSize = "3",
                    apikey = APIKEY4,
                    page = "233"
                )
            )
        }

        loadingBannerCenter()
        withContext(Dispatchers.Main){
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
        val products4:Deferred<Results.ResultProduct<OnOfferProductsItem>> = async {
            homeRepositoryImplBestBye.loadProducts(Params.ProductsParam<OnOfferProductsItem,ProductsModel>(
                    mapper = MapProductsToListData(
                        type = Type.ProductNonName(),
                        topOffer = "Товары с шок-кешбеком по Ozon.Card",
                        bottomOffer = "Больше товаров тут"
                    ),
                    pathId = TVS,
                    pageSize = "4",
                    apikey = APIKEY5,
                    page = "23"
                )
            )
        }
        loadingBannerDown()
        withContext(Dispatchers.Main){
            listPoductsLiveData4.value = products4.await().result
        }
    }

    fun insertFavoriteProduct(productsItem: OnProductItem) {
        launch(Dispatchers.IO) {
            OzonApp.db.productsDao()?.insert(ProductDb(
                type = productsItem.type.type,
                nameOfProduct = productsItem.nameOfProduct,
                iconProduct = productsItem.generalIconProductSting,
                isFavorite = productsItem.favoritelIconProduct,
                productDiscount = productsItem.productDiscount,
                isBestseller = productsItem.isBestseller,
                priceWithDiscount = productsItem.priceWithDiscount,
                priceOlD = productsItem.priceOlD,
                goToBasket = productsItem.goToBasket,
                shortDescription = productsItem.shortDescription,
                longDescription = productsItem.longDescription,
                images = productsItem.images,
                company = productsItem.company,
                color = productsItem.color
            ))


            val data =OzonApp.db.productsDao().getAll()

            Log.v("TGYHUJIKO","re $data")
        }
    }


//    fun startLoadingData(){
//
//        loadData(Dispatchers.IO) {
//
//            val bannerStart:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async { homeRepositoryImplBestBye.getBannerStart()}
//
//            val categoryResult:Deferred<HomeRepositoryImpl.Results.ResultCategoryProduct> =  async { homeRepositoryImplBestBye.loadDataCategoryProduct(HomeRepositoryImpl.Params())}
//
//            val historyItems:Deferred<HomeRepositoryImpl.Results.ResultHistory> = async {  homeRepositoryImplDropBox.getHistoryItems()}
//
//            val liveItems:Deferred<HomeRepositoryImpl.Results.ResultLive> = async {  homeRepositoryImplDropBox.getLiveItems()}
//
//            val products:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async {  homeRepositoryImplBestBye.getFirstProducts()}
//
//            val products2:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async {  homeRepositoryImplBestBye.getSecondProducts() }
//
//            val bannerCenter:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async {  homeRepositoryImplBestBye.getBannerCenter()}
//
//            val products3:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async{ homeRepositoryImplBestBye.getThirdProducts() }
//
//            val bannerDown:Deferred<HomeRepositoryImpl.Results.ResultBanner> = async{ homeRepositoryImplBestBye.getBannerDown() }
//
//            val products4:Deferred<HomeRepositoryImpl.Results.ResultProduct> = async { homeRepositoryImplBestBye.getFourthProducts() }
//
//
//            withContext(Dispatchers.Main){
//                setupData(
//                    bannerStart.await().result,
//                    (categoryResult.await().result),
//                    (historyItems.await()).result,
//                    liveItems.await().result,
//                    products.await().result,
//                    products2.await().result,
//                    bannerCenter.await().result,
//                    products3.await().result,
//                    bannerDown.await().result,
//                    products4.await().result,
//                )
//
//            }
//        }
//    }

//    private fun setupData(
//        startBannerItems: Resource<MutableList<OnBoardingItem>>?,
//        categoryItems: Resource<MutableList<MutableList<OnBoardingItem>>>?,
//        historyItems: Resource<OnHistoryItem>?,
//        liveItems: Resource<OnLiveItem>?,
//        productFirst: Resource<OnOfferProductsItem>?,
//        productSecond: Resource<OnOfferProductsItem>?,
//        centerBanner: Resource<MutableList<OnBoardingItem>>?,
//        productThird: Resource<OnOfferProductsItem>? = null,
//        downBanner: Resource<MutableList<OnBoardingItem>>? = null,
//        productFourth: Resource<OnOfferProductsItem>? = null,
//    ) {
//        bannerListStart.value = startBannerItems
//        categoryProductliveData.value = categoryItems
//        historyItemsLiveData.value = historyItems
//        liveItemsLiveData.value = liveItems
//        listPoductsLiveData.value = productFirst
//        listPoductsLiveData2.value = productSecond
//        bannerListCenter.value = centerBanner
//        listPoductsLiveData3.value = productThird
//        bannerListDown.value = downBanner
//        listPoductsLiveData4.value = productFourth
//    }


}
