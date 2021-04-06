package com.app.marketPlace.presentation.activities.ui.fragments.home

import com.app.marketPlace.data.utils.Constants.ApiToken
import com.app.marketPlace.data.utils.Constants.Camera
import com.app.marketPlace.data.utils.Constants.CellPhone
import com.app.marketPlace.data.utils.Constants.CoffeeMaker
import com.app.marketPlace.data.utils.Constants.ComputerKeyboard
import com.app.marketPlace.data.utils.Constants.Headphones
import com.app.marketPlace.data.utils.Constants.Laptop
import com.app.marketPlace.data.utils.Constants.Monitor
import com.app.marketPlace.data.utils.Constants.Phone
import com.app.marketPlace.data.utils.Constants.TVS
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.repositories.AppRepository
import com.app.marketPlace.domain.repositories.Params.*
import com.app.marketPlace.domain.repositories.Results
import com.app.marketPlace.presentation.activities.checkingForErrors
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.factory.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel() {

    private val _resDataFlow =
        MutableSharedFlow<Resource<*>>()

    private val _completed: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    var netConnectionState = false

    init {
        viewModelScope.launch{
            delayUntilInternetResumeConnection()
            startLoading()
        }
    }

    private suspend fun delayUntilInternetResumeConnection() {
        while (!netConnectionState){
            delay(150)
        }
    }

    val resDataFlow: SharedFlow<Resource<*>> =
        _resDataFlow.shareIn(viewModelScope,started = SharingStarted.Lazily,replay = 20)

    val completed: StateFlow<Boolean?>  = _completed.asStateFlow()


    private suspend fun startLoading(){

        val bannerTop: Deferred<Results.BannersResult> = async { repository.loadBannersTop(BannerParams()) }

        val categories = async {
            repository.loadCategories(
                CategoriesProductParams(pageSize = "20", apiKey = ApiToken, page = "1")
            )
        }

        val stories:Deferred<Results.StoriesResult> = async {  repository.loadStories(StoriesParams())}
        val liveStreamItems:Deferred<Results.LiveStreamsResult> = async {  repository.loadLiveStreams(LiveParams())}

        val productsCoffeeMaker = async {
            repository.loadProducts(
                ProductsParams(pathId = CoffeeMaker, pageSize = "3", apiKey = ApiToken, page = "1",
                    typeProduct = Product.Type.OnlyImage
                )
            )
        }

        val regAction = async {
            Resource(Resource.Status.COMPLETED,"mock",type = Resource.Type.REGISTRATION)
        }

        val productsCellPhone = async {
            repository.loadProducts(
                ProductsParams(pathId = CellPhone, pageSize = "6", apiKey = ApiToken, page = "53",
                    typeProduct = Product.Type.ProductNonName,
                    topOffer = "Это выгодно! Успей купить!",
                    bottomOffer = "Смотреть все товары !"
                )
            )
        }

        val bannersCenter:Deferred<Results.BannersResult> = async {  repository.loadBannersCenter(BannerParams())}
        val productsLaptop = async {
            repository.loadProducts(
                ProductsParams(pathId = Laptop, pageSize = "3", apiKey = ApiToken, page = "233",
                    typeProduct = Product.Type.ProductNonName,
                    topOffer = "Рекомендуем"
                )
            )
        }
        val bannersDown:Deferred<Results.BannersResult> = async{ repository.loadBannersDown(BannerParams()) }
        val productsTvs = async {
            repository.loadProducts(
                ProductsParams(pathId = TVS, pageSize = "4", apiKey = ApiToken, page = "23",
                    typeProduct = Product.Type.ProductNonName,
                    topOffer = "Акции недели !",
                    bottomOffer = "Скидки до -80 % здесь!",
                    spain = 2
                )
            )
        }

        _resDataFlow.emitAll(
            flow {
                emit(checkingForErrors(bannerTop.await().result))
                emit(checkingForErrors(categories.await().result))
                emit(checkingForErrors(stories.await().result))
                emit(checkingForErrors(liveStreamItems.await().result))
                emit(checkingForErrors(productsCoffeeMaker.await().result))
                emit(checkingForErrors(regAction.await()))
                emit(checkingForErrors(productsCellPhone.await().result))
                emit(checkingForErrors(bannersCenter.await().result))
                emit(checkingForErrors(productsLaptop.await().result))
                emit(checkingForErrors(bannersDown.await().result))
                emit(checkingForErrors(productsTvs.await().result))
                _completed.value = true
            }
        )
    }

    fun loadAdditionalData(page: Int) {
        loadData(Dispatchers.IO){
            delayUntilInternetResumeConnection()

            val productsCamera = async {
                repository.loadProducts(
                    ProductsParams(pathId = Camera, pageSize = "8", apiKey = ApiToken, page = "3",
                        typeProduct = Product.Type.ProductWithName,
                        topOffer = "Вас может заинтересовать ",
                        spain = 2
                    )
                )
            }

            val productsHeadPhones = async {
                repository.loadProducts(
                    ProductsParams(
                        pathId = Headphones, pageSize = "12", apiKey = ApiToken, page = "1",
                        typeProduct = Product.Type.ProductNoNBasket,
                        topOffer = "Музыка для души ",
                        spain = 4
                    )
                )
            }

            val productsMonitors = async {
                repository.loadProducts(
                    ProductsParams(pathId = Monitor, pageSize = "6", apiKey = ApiToken, page = "1",
                        typeProduct = Product.Type.ProductHorizontal,
                        topOffer = "Лучшие мониторы 90 % скидка",
                        resourceType = Resource.Type.HORIZONTAL_PRODUCT,
                        spain = 1
                    )
                )
            }
            val productsKeyboard = async {
                repository.loadProducts(
                    ProductsParams(pathId = ComputerKeyboard, pageSize = "6", apiKey = ApiToken,
                        page = "1",
                        typeProduct = Product.Type.ProductWithName,
                        topOffer = "Удачная покупка"
                    )
                )
            }
            val productsPhones = async {
                repository.loadProducts(
                    ProductsParams(pathId = Phone, pageSize = "20", apiKey = ApiToken, page = "1",
                        typeProduct = Product.Type.ProductWithName,
                        topOffer = "Пора обновить телефон !",
                        spain = 3
                    )
                )
            }

            _resDataFlow.emitAll(
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

