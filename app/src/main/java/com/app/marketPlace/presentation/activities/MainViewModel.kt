package com.app.marketPlace.presentation.activities

import android.util.Log
import androidx.lifecycle.*
import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.HintProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.data.db.models.UserDb
import com.app.marketPlace.domain.mappers.MapperFromDb
import com.app.marketPlace.domain.models.Product
import com.app.marketPlace.domain.repositories.DataBaseRepository

import com.app.marketPlace.presentation.factory.Resource
import com.app.marketPlace.presentation.utils.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    var repository:DataBaseRepository,
    var networkConnection:NetworkConnection,
    var mapperFromDb:MapperFromDb,
) : ViewModel(), CoroutineScope {

    companion object {
        var listIdsBasket: MutableList<Int> = mutableListOf()
        var listIdsFavorite: MutableList<Int> = mutableListOf()
    }

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    val allIdsInFavorite: LiveData<List<Int>?> = repository.productsInFavoriteIds.asLiveData()

    val allIdsInBaskets: LiveData<List<Int>?> = repository.productsInBasketIds.asLiveData()

    val userLive: MutableLiveData<UserDb> = MutableLiveData()

    val baskets: LiveData<List<BasketProductDb>?> = repository.baskets.asLiveData()

    val favorite: LiveData<List<FavoriteProductDb>?> = repository.favorites.asLiveData()

    val hintProducts: LiveData<List<HintProductDb>>? = repository.hintsProduct?.asLiveData()

    fun insertOrDeleteHintsProduct(request: String) {
        insertHint(request)
    }

    private fun insertHint(request: String) {
        launch(Dispatchers.IO) {
            repository.insertHint(request)
        }
    }

    private fun deleteHint(request: String) {
        launch(Dispatchers.IO) {
            repository.deleteHint(request)
        }
    }

    fun insertOrDeleteFavoriteProduct(productsItem: Product) {
        if (productsItem.isFavorite) {
            insertProduct(productsItem)
        } else {
            deleteProduct(productsItem)
        }
    }

    private fun insertProduct(product: Product) {
        launch(Dispatchers.IO) {
            repository.insertFavorite(product)
        }
    }

    internal fun deleteProduct(basket: Product) {
        launch(Dispatchers.IO) {
            repository.deleteFavorite(basket)
        }
    }


    fun insertOrDeleteBasket(productsItem: Product) {
        if (productsItem.isBasket) {
            insertBasket(productsItem)
        } else {
            deleteBasket(productsItem)
        }
    }

    private fun insertBasket(basket: Product) {
        launch(Dispatchers.IO) {
            repository.insertBasket(basket)
        }
    }

    private fun deleteBasket(basket: Product) {
        launch(Dispatchers.IO) {
            repository.deleteBasket(basket)
        }
    }


    fun setUser(user: UserDb) {
        launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    fun getUser(login: String) {
        launch(Dispatchers.IO) {
            userLive.postValue(repository.getUser(login))
        }
    }


    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

fun <T> gettingErrors(resource: Resource<T>): Boolean {
    return (resource.status == Resource.Status.COMPLETED && resource.data != null && resource.exception == null)
}


suspend fun<Type> checkingForErrors(data: Resource<Type>): Resource<Type> {
    return if (gettingErrors(data)){
        data
    }else{
        errorHandling(data.type.toString(),data)
        data.copy(data = null)
    }
}
fun <T> errorHandling(name: String, resource: Resource<T>) {
    if (resource.status == Resource.Status.LOADING) return
    Log.v(name, "${resource.exception?.message}")
    Log.v(name, "${resource.exception?.stackTrace}")
    Log.v(name, "${resource.exception}")
    Log.v(name, "${resource.status}")
}