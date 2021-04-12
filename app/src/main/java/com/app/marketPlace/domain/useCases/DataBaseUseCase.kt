package com.app.marketPlace.domain.useCases

import androidx.annotation.WorkerThread
import com.app.marketPlace.data.db.dao.HintProductDao
import com.app.marketPlace.data.db.dao.InBasketDao
import com.app.marketPlace.data.db.dao.InFavoriteDao
import com.app.marketPlace.data.db.dao.UserDao
import com.app.marketPlace.data.db.models.BasketProductDb
import com.app.marketPlace.data.db.models.HintProductDb
import com.app.marketPlace.data.db.models.FavoriteProductDb
import com.app.marketPlace.data.db.models.UserDb
import com.app.marketPlace.domain.mappers.MapperToDb

import com.app.marketPlace.domain.models.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataBaseUseCase @Inject constructor(
    private val favoriteProductDao: InFavoriteDao,
    private val basketDao: InBasketDao,
    private val userDao: UserDao,
    private val hintDao: HintProductDao,
    private val mapperTo: MapperToDb
) {

    val productsInFavoriteIds: Flow<List<Int>?> = favoriteProductDao.getAllIds()
    val productsInBasketIds: Flow<List<Int>?> = basketDao.getAllIds()

    val baskets: Flow<List<BasketProductDb>?> = basketDao.getAllFlow()
    val favorites: Flow<List<FavoriteProductDb>?> = favoriteProductDao.getAllFlow()
    val hintsProduct: Flow<List<HintProductDb>>? = hintDao.getAllFlow()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertHint(request: String){
        hintDao.insert(mapperTo.mapHintUi(request))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteHint(request: String){
        hintDao.delete(mapperTo.mapHintUi(request))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertFavorite(product: Product) {
        favoriteProductDao.insert(mapperTo.mapFavoriteUi(product))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteFavorite(product: Product) {
        favoriteProductDao.delete(mapperTo.mapFavoriteUi(product))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteBasket(basket: Product){
        basketDao.delete(mapperTo.mapBasketUi(basket))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertBasket(basket: Product) {
        basketDao.insert(mapperTo.mapBasketUi(basket))
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user: UserDb){
        userDao.insert(user)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUser(login: String): UserDb {
        return userDao.getUser(login)
    }
}