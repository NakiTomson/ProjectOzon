package com.appmarketplace.ozon.presentation.activityes

import android.util.Log
import androidx.lifecycle.*
import com.appmarketplace.ozon.data.remote.modelsDB.BasketProductDb
import com.appmarketplace.ozon.data.remote.modelsDB.HintProductDB
import com.appmarketplace.ozon.data.remote.modelsDB.UserDB
import com.appmarketplace.ozon.domain.modelsUI.OnProductItem
import com.appmarketplace.ozon.domain.repositories.DataBaseRepository
import com.appmarketplace.ozon.domain.repositories.ListProductRepository
import com.appmarketplace.ozon.presentation.activityes.ui.fragments.productsList.ProductsListViewModel
import com.appmarketplace.ozon.presentation.rowType.Resource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: DataBaseRepository) : ViewModel(), CoroutineScope {


    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    val allIdsInFavorite: LiveData<List<Int>?> = repository.productsInFavoriteIds.asLiveData()

    val allIdsInBaskets: LiveData<List<Int>?> = repository.productsInBasketIds.asLiveData()

    val userLive: MutableLiveData<UserDB> = MutableLiveData()

    val baskets: LiveData<List<BasketProductDb>?> = repository.baskets?.asLiveData()

    val favorite: LiveData<List<BasketProductDb>?> = repository.baskets?.asLiveData()

    val hintProducts: LiveData<List<HintProductDB>>? = repository.hintProducts?.asLiveData()


    fun insertOrDeleteHintsProduct(request: String){
        if (true){
            insertHint(request)
        }else{
            deleteHint(request)
        }
    }

    private fun insertHint(request: String){
        launch (Dispatchers.IO){
            repository.insertHint(request)
        }
    }

    private fun deleteHint(request: String){
        launch (Dispatchers.IO){
            repository.deleteHint(request)
        }
    }

    fun insertOrDeleteFavoriteProduct(productsItem: OnProductItem){
        if (productsItem.favoritelIconProduct){
            insertProduct(productsItem)
        }else{
            deleteProduct(productsItem)
        }
    }

    private fun insertProduct(product: OnProductItem){
        launch (Dispatchers.IO){
            repository.insertProductInFavorite(product)
        }
    }

    internal fun deleteProduct(basket: OnProductItem){
        launch (Dispatchers.IO){
            repository.deleteProductFromFavorite(basket)
        }
    }


    fun insertOrDeleteBasket(productsItem: OnProductItem){
        if (productsItem.productInBasket) {
            insertBasket(productsItem)
        } else {
            deleteBasket(productsItem)
        }
    }

    private fun insertBasket(basket: OnProductItem){
        launch (Dispatchers.IO){
            repository.insertBasket(basket)
        }
    }

    private fun deleteBasket(basket: OnProductItem){
        launch (Dispatchers.IO){
            repository.deleteBasket(basket)
        }
    }



    fun setUser(user: UserDB) {
        launch (Dispatchers.IO){
            repository.insertUser(user)
        }
    }

    fun getUser(login: String) {
        launch (Dispatchers.IO){
            userLive.postValue(repository.getUser(login))
        }
    }


    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}



class MainViewModelFactory(private val repository: DataBaseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository,) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class ProductsListViewModelFactory(private val repository: ListProductRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductsListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


fun <T> MutableLiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}


fun <T> gettingErrors(resource: Resource<T>): Boolean {
    return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
}

fun <T> errorhandling(name: String, resource: Resource<T>) {
    Log.v(name, "${resource.exception?.message}")
    Log.v(name, "${resource.exception}")
    Log.v(name, "${resource.status}")
}


