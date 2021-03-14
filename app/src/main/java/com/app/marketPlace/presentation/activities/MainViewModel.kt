package com.app.marketPlace.presentation.activities

import android.util.Log
import androidx.lifecycle.*
import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.HintProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.data.db.models.UserDb
import com.app.marketPlace.domain.models.ProductItem
import com.app.marketPlace.domain.repositories.DataBaseRepository
import com.app.marketPlace.presentation.rowType.Resource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: DataBaseRepository) : ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job



    val allIdsInFavorite: LiveData<List<Int>?> = repository.productsInFavoriteIds.asLiveData()

    val allIdsInBaskets: LiveData<List<Int>?> = repository.productsInBasketIds.asLiveData()

    val userLive: MutableLiveData<UserDb> = MutableLiveData()

    val baskets: LiveData<List<BasketProductDb>?> = repository.baskets.asLiveData()

    val favorite: LiveData<List<FavoriteProductDb>?> = repository.favorite.asLiveData()

    val hintProducts: LiveData<List<HintProductDb>>? = repository.hintProducts?.asLiveData()


    fun insertOrDeleteHintsProduct(request: String){
        insertHint(request)
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

    fun insertOrDeleteFavoriteProduct(productsItem: ProductItem){
        if (productsItem.favoriteIconProduct){
            insertProduct(productsItem)
        }else{
            deleteProduct(productsItem)
        }
    }

    private fun insertProduct(product: ProductItem){
        launch (Dispatchers.IO){
            repository.insertProductInFavorite(product)
        }
    }

    internal fun deleteProduct(basket: ProductItem){
        launch (Dispatchers.IO){
            repository.deleteProductFromFavorite(basket)
        }
    }


    fun insertOrDeleteBasket(productsItem: ProductItem){
        if (productsItem.productInBasket) {
            insertBasket(productsItem)
        } else {
            deleteBasket(productsItem)
        }
    }

    private fun insertBasket(basket: ProductItem){
        launch (Dispatchers.IO){
            repository.insertBasket(basket)
        }
    }

    private fun deleteBasket(basket: ProductItem){
        launch (Dispatchers.IO){
            repository.deleteBasket(basket)
        }
    }



    fun setUser(user: UserDb) {
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
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//class ModelFactory(private var vieModel: ViewModel) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(vieModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return vieModel as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

fun <T> gettingErrors(resource: Resource<T>): Boolean {
    return !(resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data == null || resource.exception != null)
}

fun <T> errorHandling(name: String, resource: Resource<T>) {
    Log.v(name, "${resource.exception?.message}")
    Log.v(name, "${resource.exception}")
    Log.v(name, "${resource.status}")
}


