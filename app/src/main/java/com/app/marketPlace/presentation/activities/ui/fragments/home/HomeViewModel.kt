package com.app.marketPlace.presentation.activities.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.marketPlace.data.utils.ConstantsApp.APIKEY
import com.app.marketPlace.data.utils.ConstantsApp.CAMERA
import com.app.marketPlace.data.utils.ConstantsApp.CELL_PHONES
import com.app.marketPlace.data.utils.ConstantsApp.COFFEE_MAKER
import com.app.marketPlace.data.utils.ConstantsApp.COMPUTER_KEYBOARDS
import com.app.marketPlace.data.utils.ConstantsApp.HEADPHONES
import com.app.marketPlace.data.utils.ConstantsApp.LAPTOPS
import com.app.marketPlace.data.utils.ConstantsApp.MONITORS
import com.app.marketPlace.data.utils.ConstantsApp.PHONES
import com.app.marketPlace.data.utils.ConstantsApp.TVS
import com.app.marketPlace.data.utils.ConstantsApp.onDownload
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params.BannerParams
import com.app.marketPlace.domain.repositories.Params.LiveParams
import com.app.marketPlace.domain.repositories.Params.StoriesParams
import com.app.marketPlace.domain.repositories.Params.CategoriesProductParams
import com.app.marketPlace.domain.repositories.Params.ProductsParams
import com.app.marketPlace.domain.repositories.Results
import com.app.marketPlace.presentation.MarketPlaceApp
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.rowType.Resource
import com.app.marketPlace.domain.models.*
import com.app.marketPlace.presentation.activities.checkingForErrors
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class HomeViewModel() : BaseViewModel() {

    private val _data =
        MutableSharedFlow<Resource<*>>()

    init {
        MarketPlaceApp.appComponent.inject(this)
        viewModelScope.launch {
            startLoading()
        }
    }

    @Inject
    lateinit var repository: AppRepository

    val completed: MutableLiveData<Boolean> = MutableLiveData()


    val data: SharedFlow<Resource<*>> =
        _data.shareIn(viewModelScope,started = SharingStarted.Lazily,replay = 20)


    private suspend fun startLoading(){

        val bannerTop: Deferred<Results.ResultBanner> = async { repository.getBannerStart(BannerParams()) }

        val categories = async {
            repository.loadCategories(CategoriesProductParams(
                pageSize = "20", apiKey = APIKEY, page = "1"
            ))
        }

        val stories:Deferred<Results.ResultHistory> = async {  repository.getHistoryItems(StoriesParams())}

        val liveStreamItems:Deferred<Results.ResultLive> = async {  repository.getLiveItems(LiveParams())}

        val productsOne = async {
            repository.loadProducts(
                ProductsParams(pathId = COFFEE_MAKER, pageSize = "3", apiKey = APIKEY, page = "1",
                    typeProduct = ProductItem.Type.OnlyImage
                )
            )
        }

        val productsTwo = async {
            repository.loadProducts(
                ProductsParams(pathId = CELL_PHONES, pageSize = "6", apiKey = APIKEY, page = "53",
                    typeProduct = ProductItem.Type.ProductNonName,
                    topOffer = "Это выгодно! Успей купить!",
                    bottomOffer = "Смотреть все товары !"
                )
            )
        }

        val bannersCenter:Deferred<Results.ResultBanner> = async {  repository.getBannerCenter(BannerParams())}
        val productsThree = async {
            repository.loadProducts(
                ProductsParams(pathId = LAPTOPS, pageSize = "3", apiKey = APIKEY, page = "233",
                    typeProduct = ProductItem.Type.ProductNonName,
                    topOffer = "Рекомендуем"
                )
            )
        }
        val bannersDown:Deferred<Results.ResultBanner> = async{ repository.getBannerDown(BannerParams()) }
        val productsFour = async {
            repository.loadProducts(
                ProductsParams(pathId = TVS, pageSize = "4", apiKey = APIKEY, page = "23",
                    typeProduct = ProductItem.Type.ProductNonName,
                    topOffer = "Акции недели !",
                    bottomOffer = "Скидки до -80 % здесь!",
                    spain = 2
                )
            )
        }


        _data.emitAll(
            flow {
                emit(checkingForErrors(bannerTop.await().result))
                emit(checkingForErrors(categories.await().result))
                emit(checkingForErrors(stories.await().result))
                emit(checkingForErrors(liveStreamItems.await().result))
                emit(checkingForErrors(productsOne.await().result))
                emit(checkingForErrors(productsTwo.await().result))
                emit(checkingForErrors(bannersCenter.await().result))
                emit(checkingForErrors(productsThree.await().result))
                emit(checkingForErrors(bannersDown.await().result))
                emit(checkingForErrors(productsFour.await().result))
                completed.postValue(true)
            }
        )
    }

    fun loadAdditionalData() {
        if (onDownload) return
        onDownload = true

        loadData(Dispatchers.IO){
            val productsCamera = async {
                repository.loadProducts(
                    ProductsParams(pathId = CAMERA, pageSize = "8", apiKey = APIKEY, page = "3",
                        typeProduct = ProductItem.Type.ProductWithName,
                        topOffer = "Вас может заинтересовать ",
                        spain = 2
                    )
                )
            }
            val productsHeadPhones = async {
                repository.loadProducts(
                    ProductsParams(pathId = HEADPHONES, pageSize = "12", apiKey = APIKEY, page = "1",
                        typeProduct = ProductItem.Type.ProductNoNBasket,
                        topOffer = "Музыка для души ",
                        spain = 4
                    )
                )
            }
            val productsMonitors = async {
                repository.loadProducts(
                    ProductsParams(pathId = MONITORS, pageSize = "6", apiKey = APIKEY, page = "1",
                        typeProduct = ProductItem.Type.ProductHorizontal,
                        topOffer = "Лучшие мониторы 90 % скидка",
                        resourceType = Resource.Type.HORIZONTAL_PRODUCT,
                        spain = 1
                    )
                )
            }
            val productsKeyboard = async {
                repository.loadProducts(
                    ProductsParams(pathId = COMPUTER_KEYBOARDS, pageSize = "6", apiKey = APIKEY,
                        page = "1",
                        typeProduct = ProductItem.Type.ProductWithName,
                        topOffer = "Удачная покупка"
                    )
                )
            }
            val productsPhones = async {
                repository.loadProducts(
                    ProductsParams(pathId = PHONES, pageSize = "20", apiKey = APIKEY, page = "1",
                        typeProduct = ProductItem.Type.ProductWithName,
                        topOffer = "Пора обновить телефон !",
                        spain = 3
                    )
                )
            }

            _data.emitAll(
                flow {
                    emit(checkingForErrors(productsCamera.await().result))
                    emit(checkingForErrors(productsHeadPhones.await().result))
                    emit(checkingForErrors(productsMonitors.await().result))
                    emit(checkingForErrors(productsKeyboard.await().result))
                    emit(checkingForErrors(productsPhones.await().result))
                }
            )
        }
    }
}
