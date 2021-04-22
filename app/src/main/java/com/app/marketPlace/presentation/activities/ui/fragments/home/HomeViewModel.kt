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
import com.app.marketPlace.domain.useCases.*
import com.app.marketPlace.domain.models.Params.*
import com.app.marketPlace.presentation.activities.checkingErrorsInType
import com.app.marketPlace.presentation.activities.ui.fragments.BaseViewModel
import com.app.marketPlace.presentation.factory.TypeResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsLoadUseCase: ProductsLoadUseCase,
    private val loadBannersUseCase: BannersLoadUseCase,
    private val categoriesLoadUseCase: CategoriesLoadUseCase,
    private val storiesLoadUseCase: StoriesLoadUseCase,
    private val streamsLoadLiveUseCase: StreamsLoadLiveUseCase,
) : BaseViewModel() {

    private val _resDataFlow =
        MutableSharedFlow<TypeResource>()

    private val _completed: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    var netConnectionState = false

    init {
        viewModelScope.launch {
            delayUntilInternetResumeConnection()
            startLoading()
        }
    }

    private suspend fun delayUntilInternetResumeConnection() {
        while (!netConnectionState) {
            delay(150)
        }
    }

    val resDataFlow: SharedFlow<TypeResource> =
        _resDataFlow.shareIn(viewModelScope, started = SharingStarted.Lazily, replay = 20)

    val completed: StateFlow<Boolean?> = _completed.asStateFlow()


    private suspend fun startLoading(){

        val bannerTop = async { loadBannersUseCase.loadBannersTop(BannerParams()) }

        val categories = async {
            categoriesLoadUseCase.loadCategories(
                CategoriesProductParams(pageSize = "20", apiKey = ApiToken, page = "1")
            )
        }

        val stories = async {  storiesLoadUseCase.loadStories(StoriesParams())}
        val liveStreamItems = async {  streamsLoadLiveUseCase.loadLiveStreams(LiveParams())}

        val productsCoffeeMaker = async {
            productsLoadUseCase.loadProducts(
                ProductsParams(pathId = CoffeeMaker, pageSize = "3", apiKey = ApiToken, page = "1",
                    typeProduct = Product.Type.OnlyImage
                )
            )
        }

        val regAction = async {
            TypeResource.Registration()
        }

        val productsCellPhone = async {
            productsLoadUseCase.loadProducts(
                ProductsParams(pathId = CellPhone, pageSize = "6", apiKey = ApiToken, page = "53",
                    typeProduct = Product.Type.ProductNonName,
                    topOffer = "Это выгодно! Успей купить!",
                    bottomOffer = "Смотреть все товары !"
                )
            )
        }

        val bannersCenter= async {  loadBannersUseCase.loadBannersCenter(BannerParams())}
        val productsLaptop = async {
            productsLoadUseCase.loadProducts(
                ProductsParams(pathId = Laptop, pageSize = "3", apiKey = ApiToken, page = "233",
                    typeProduct = Product.Type.ProductNonName,
                    topOffer = "Рекомендуем"
                )
            )
        }
        val bannersDown = async{ loadBannersUseCase.loadBannersDown(BannerParams()) }
        val productsTvs = async {
            productsLoadUseCase.loadProducts(
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
                emit(checkingErrorsInType(bannerTop.await()))
                emit(checkingErrorsInType(categories.await()))
                emit(checkingErrorsInType(stories.await()))
                emit(checkingErrorsInType(liveStreamItems.await()))
                emit(checkingErrorsInType(productsCoffeeMaker.await()))
                emit(checkingErrorsInType(regAction.await()))
                emit(checkingErrorsInType(productsCellPhone.await()))
                emit(checkingErrorsInType(bannersCenter.await()))
                emit(checkingErrorsInType(productsLaptop.await()))
                emit(checkingErrorsInType(bannersDown.await()))
                emit(checkingErrorsInType(productsTvs.await()))
                _completed.value = true
            }
        )
    }

    fun loadAdditionalData(page: Int) {
        loadData(Dispatchers.IO){
            delayUntilInternetResumeConnection()

            val productsCamera = async {
                productsLoadUseCase.loadProducts(
                    ProductsParams(pathId = Camera, pageSize = "8", apiKey = ApiToken, page = "3",
                        typeProduct = Product.Type.ProductWithName,
                        topOffer = "Вас может заинтересовать ",
                        spain = 2
                    )
                )
            }

            val productsHeadPhones = async {
                productsLoadUseCase.loadProducts(
                    ProductsParams(
                        pathId = Headphones, pageSize = "12", apiKey = ApiToken, page = "1",
                        typeProduct = Product.Type.ProductNoNBasket,
                        topOffer = "Музыка для души ",
                        spain = 4
                    )
                )
            }

            val productsMonitors:Deferred<TypeResource> = async {
                productsLoadUseCase.loadHorizontalProducts(
                    ProductsParams(pathId = Monitor, pageSize = "6", apiKey = ApiToken, page = "1",
                        typeProduct = Product.Type.ProductHorizontal,
                        topOffer = "Лучшие мониторы 90 % скидка",
                        spain = 1
                    )
                )
            }
            val productsKeyboard = async {
                productsLoadUseCase.loadProducts(
                    ProductsParams(pathId = ComputerKeyboard, pageSize = "6", apiKey = ApiToken,
                        page = "1",
                        typeProduct = Product.Type.ProductWithName,
                        topOffer = "Удачная покупка"
                    )
                )
            }
            val productsPhones = async {
                productsLoadUseCase.loadProducts(
                    ProductsParams(pathId = Phone, pageSize = "20", apiKey = ApiToken, page = "1",
                        typeProduct = Product.Type.ProductWithName,
                        topOffer = "Пора обновить телефон !",
                        spain = 3
                    )
                )
            }

            _resDataFlow.emitAll(
                flow {
                    emit(checkingErrorsInType(productsCamera.await()))
                    emit(checkingErrorsInType(productsHeadPhones.await()))
                    emit(checkingErrorsInType(productsMonitors.await()))
                    emit(checkingErrorsInType(productsKeyboard.await()))
                    emit(checkingErrorsInType(productsPhones.await()))
                }
            )
        }
    }
}

