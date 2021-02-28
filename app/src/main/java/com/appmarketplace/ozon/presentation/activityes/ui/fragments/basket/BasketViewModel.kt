package com.appmarketplace.ozon.presentation.activityes.ui.fragments.basket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appmarketplace.ozon.data.db.BasketProductDao
import com.appmarketplace.ozon.data.db.OzonAppDataBase
import com.appmarketplace.ozon.data.db.ProductDao
import com.appmarketplace.ozon.data.remote.modelsAPI.ProductsModel
import com.appmarketplace.ozon.data.remote.modelsDB.BasketProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.ProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB
import com.appmarketplace.ozon.data.remote.services.APIKEY4
import com.appmarketplace.ozon.data.utils.Gonfigs
import com.appmarketplace.ozon.data.utils.Gonfigs.listIds
import com.appmarketplace.ozon.data.utils.Gonfigs.listIdsInBusket
import com.appmarketplace.ozon.domain.mappers.MapProductsToListData
import com.appmarketplace.ozon.domain.modelsUI.CategoryPath
import com.appmarketplace.ozon.domain.modelsUI.OnOfferProductsItem
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import com.appmarketplace.ozon.domain.repositories.Params
import com.appmarketplace.ozon.presentation.OzonApp
import com.appmarketplace.ozon.presentation.rowType.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class BasketViewModel:ViewModel(), CoroutineScope {





    init {
        OzonApp.appComponent.inject(basketViewModel = this)
    }


    @Inject
    lateinit var productDao: ProductDao

    @Inject
    lateinit var basketDao: BasketProductDao

    @Inject
    @field : Named("bestbuy")
    lateinit var listProductRepository: ListProductRepository


    val productsResultList: MutableLiveData<Resource<OnOfferProductsItem>> = MutableLiveData()

    val productsInBusketLive: MutableLiveData<List<OnProductItem>> = MutableLiveData()

    private val job: Job = Job()

    fun getBasket(){
        launch (Dispatchers.IO){
            val baketsProduct = basketDao.getAll()
            Log.v("TAGAGA","re ${baketsProduct?.size}")
            withContext(Dispatchers.Main){

                productsInBusketLive.value = baketsProduct?.map {
                        OnProductItem(
                            type = OnProductItem.Type.ProductWithName(),
                            generalIconProductSting = it.iconProduct,
                            favoritelIconProduct = it.isFavorite,
                            productDiscount = it.productDiscount,
                            isBestseller = it.isBestseller,
                            priceWithDiscount = it.priceWithDiscount,
                            priceOlD = it.priceOlD,
                            goToBasket = it.goToBasket,
                            nameOfProduct = it.nameOfProduct,
                            shortDescription = it.shortDescription,
                            longDescription = it.longDescription,
                            images = it.images,
                            company = it.company,
                            color = it.color,
                            skuId = it.id
                        )
                    }

            }
        }
    }

    fun getListSimilarCategory(recomedDedPath: String){
        if(productsResultList.value?.data == null){

            launch {
                val products= async {
                    listProductRepository
                        .loadProducts(
                            Params.ProductsParam<OnOfferProductsItem, ProductsModel>(
                                mapper = MapProductsToListData(type = OnProductItem.Type.ProductWithName(),listIds = listIds,listIdsBakset = listIdsInBusket),
                                pathId = recomedDedPath,
                                pageSize = "20",
                                apikey = APIKEY4,
                                page = "1"
                            )
                        )
                }
                withContext(Dispatchers.Main){
                    productsResultList.value = products.await().result
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun insertOrDeleteFavoriteProduct(productsItem: OnProductItem) {
        launch(Dispatchers.IO) {
            if (productsItem.favoritelIconProduct){
                productDao?.insert(
                    ProductDb(
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
                        color = productsItem.color,
                        id = productsItem.skuId
                    )
                )
            }else{
                productDao.delete(
                    ProductDb(
                        id = productsItem.skuId
                    )
                )
            }
        }
    }

    fun deleteBasket(productsItem: OnProductItem) {
        launch (Dispatchers.IO){
            basketDao.delete(
                BasketProductDb(
                    id = productsItem.skuId
                )
            )
        }
    }

    fun insertOrDeleteBasket(productsItem: OnProductItem) {
        launch(Dispatchers.IO) {
            if (productsItem.productInBasket){
                basketDao?.insert(
                    BasketProductDb(
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
                        color = productsItem.color,
                        id = productsItem.skuId
                    )
                )
            }else{
                basketDao.delete(
                    BasketProductDb(
                        id = productsItem.skuId
                    )
                )
            }
        }
    }


}